package com.penguin.cuppingnote.pref.domain;

import com.penguin.cuppingnote.common.domain.BaseEntity;
import com.penguin.cuppingnote.common.exception.jwt.ExpiredTokenException;
import com.penguin.cuppingnote.user.domain.user.User;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user_preference SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Table(name = "user_preference")
public class UserPreference extends BaseEntity {

    @Id
    @Column(name = "id")
    @Comment("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @Comment("사용자 ID")
    private Long userId;

    @Column(name = "flavor", nullable = false)
    @Comment("향")
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
}
