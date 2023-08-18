package shop.mtcoding.ajax.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TechRepository extends JpaRepository<Tech, Integer> {

    // NativeQuery : select * from tech_tb where category_id = :categoryId
    @Query("select t from Tech t where t.category.id = :categoryId")
    // 객체지향쿼리로, 변수를 찾으면 된다.
    List<Tech> findByCategoryId(@Param("categoryId") Integer categoryId);

}
