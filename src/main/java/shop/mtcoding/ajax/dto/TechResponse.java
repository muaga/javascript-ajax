package shop.mtcoding.ajax.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.ajax.model.Category;
import shop.mtcoding.ajax.model.Tech;

// 프론트엔드에게 LazyLoading이 발동되지 않은 상태에서 데이터를 잘 전달해야 한다.
// 왜 LazyLoading이 발동되지 않을까?
// DTO로 변환해서....! = "깊은 복사"
// 1) DTO는 영속화된 객체가 아니다.
// 2) 화면에만 필요한 객체를 복사했기 때문에 연관관계인 category_id는 가져오지 않는다.
// 그래서 DTO를 JSON으로 변환할 때 category_id를 읽지 못하기 때문에
// LazyLoading이 발동하지 않는다.

public class TechResponse {
    @Getter
    @Setter
    public static class MainDTO {
        private List<CategoryDTO> categories;
        private List<TechDTO> techs;

        public MainDTO(List<Category> categories, List<Tech> techs) {
            this.categories = categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
            this.techs = techs.stream().map(TechDTO::new).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class CategoryDTO {
            private Integer id;
            private String name;

            public CategoryDTO(Category category) {
                this.id = category.getId();
                this.name = category.getName();
            }
        }

        @Getter
        @Setter
        public class TechDTO {
            private Integer id;
            private String name;

            public TechDTO(Tech tech) {
                this.id = tech.getId();
                this.name = tech.getName();
            }
        }
    }
    // List를 modle로 받으면
    // 그 속의 연관관계 때문에 MainDTO를 만들어도 LazyLoading이 발생
}
