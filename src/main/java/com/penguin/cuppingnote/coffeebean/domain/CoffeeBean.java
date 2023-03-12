package com.penguin.cuppingnote.coffeebean.domain;

import com.penguin.cuppingnote.common.domain.BaseEntity;
import com.penguin.cuppingnote.user.domain.user.User;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE coffee_bean SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Table(name = "coffee_bean")
public class CoffeeBean extends BaseEntity {

    @Id
    @Column(name = "id")
    @Comment("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @Comment("원두 이름")
    private String name;

    @Column(name = "flavor", nullable = false)
    @Comment("향")
    // TODO enum?
    private Integer flavor;

    @Column(name = "acidity", nullable = false)
    @Comment("산미")
    private Integer acidity;

    @Column(name = "sweet", nullable = false)
    @Comment("당도")
    private Integer sweet;

    @Column(name = "bitter", nullable = false)
    @Comment("쓴맛")
    private Integer bitter;

    @Column(name = "body", nullable = false)
    @Comment("바디감")
    private Integer body;

    @Column(name = "image_url", nullable = false)
    @Comment("원두 이미지 url")
    private String imageUrl;

    @Column(name = "nation", nullable = false)
    @Comment("국가")
    private String nation;

    @Column(name = "feature", nullable = false)
    @Comment("특징")
    private String feature;

    //TODO: 태그 목록과 one to many?
}
