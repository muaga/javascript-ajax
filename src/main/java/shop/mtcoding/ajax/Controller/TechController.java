package shop.mtcoding.ajax.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.ajax.dto.TechResponse;
import shop.mtcoding.ajax.model.Category;
import shop.mtcoding.ajax.model.CategoryRepository;
import shop.mtcoding.ajax.model.Tech;
import shop.mtcoding.ajax.model.TechRepository;

@Controller
public class TechController {

    @Autowired
    private TechRepository techRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // 프론트엔드에게 잘 전달하기 위해 하는 select 1 - DTO / stream
    @GetMapping("/v1/test/tech")
    public @ResponseBody TechResponse.MainDTO techV1() {
        List<Category> categoryList = categoryRepository.findAll();
        List<Tech> techList = techRepository.findByCategoryId(1);
        System.out.println("=================================");
        TechResponse.MainDTO mainDTO = new TechResponse.MainDTO(categoryList, techList);
        return mainDTO; // messageconverter 발동 - json 변환(getter발동)

        // Lazy전략으로 변경시 JSON 변환(getter) 때문에 LazyLoading이 걸린다.
        // JSON 변환시 tech 속 category를 가져와서 문자열로 만들어야 하기 때문이다.ㄴ
        // 그래서 DTO를 만들어야 한다.
        // DTO를 쓰는 이유 : 객체를 다르게 변경해서 LazyLoading 즉, 연관관계가 없는 버전인 DTO를 만들어야 한다.
        // 그래서 AJAX를 만드는데 DTO는 필수다.
    }

    // 프론트엔드에게 잘 전달하기 위해 하는 select 2 - @JsonIgnore
    @GetMapping("/v2/test/tech")
    public @ResponseBody List<Tech> techV2() {
        List<Tech> techList = techRepository.findByCategoryId(1);
        return techList; // messageconverter 발동 - json 변환(getter발동)
    }

    // AJAX -> js에 보낼 데이터를 다 쪼개서 만든다.
    // 1. Controller에서 빈 껍데기 디자인을 만들어 준다.(데이터 없음)
    @GetMapping("/tech")
    public String tech() {
        return "main";
    }

    // AJAX는 사용하는 형태에 따라 category입장, tech입장의 데이터를 쪼개서 만든다
    // 그래서 두 데이터를 한번에 다운받거나 하나씩 다운받거나 할 수 있다.
    // 2. s1 - JS로 보낼 데이터를 만든다.
    // * /api -> JS에게 보내는 JSON타입으로 직렬화된 데이터 URL
    @GetMapping("/api/category")
    public @ResponseBody List<Category> categoryApi() {
        return categoryRepository.findAll();
    }

    // 2. s2
    @GetMapping("/api/tech")
    public @ResponseBody List<Tech> techApi(
            @RequestParam(defaultValue = "1") Integer categoryId) {
        return techRepository.findByCategoryId(categoryId);
    }

    // mustache - 서버사이드렌더링
    // @GetMapping("/tech")
    public String techMustache(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        List<Tech> techList = techRepository.findByCategoryId(1);
        // 모든 기술을 find한다.
        // Eager 전략으로 쿼리실행이 총 3번 일어난다.
        // tech select번 / category select 2번
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("techList", techList);
        // model은 getter를 발동하지 않는다.
        return "main";
    }
}