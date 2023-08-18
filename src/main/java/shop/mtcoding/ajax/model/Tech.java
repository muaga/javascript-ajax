package shop.mtcoding.ajax.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.TypeResolutionStrategy.Lazy;

@NoArgsConstructor
// DB 조회 -> PC에 category 객체라 생성된다. -> 빈 생성자를 호출한다.
// JPA는 빈 생성자를 이용하여 엔티티를 인스턴스화한 후에 필요한 값을 세팅하거나 초기화 작업을 수행
// 빈 생성자가 없는 경우 JPA는 엔티티를 생성하고 초기화하는 데 문제가 발생
@Setter
@Getter
@Table(name = "tech_tb")
@Entity
// Hibernate가 관리 = entityManager가 관리
public class Tech {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name;
    // Spring, Java, JavaScript, React, HTML

    // @JsonIgnore
    // LazyLoading이 안되도록 한다.
    // MessageConvert가 JSON으로 직렬화 할 때, category 객체는 직렬화하지 말라고 막았다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
