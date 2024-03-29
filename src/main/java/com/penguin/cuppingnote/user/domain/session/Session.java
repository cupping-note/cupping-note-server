package com.penguin.cuppingnote.user.domain.session;

import com.penguin.cuppingnote.common.domain.BaseEntity;
import com.penguin.cuppingnote.common.exception.jwt.ExpiredTokenException;
import com.penguin.cuppingnote.user.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE sessions SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Table(name = "sessions")
public class Session extends BaseEntity {

    @Id
    @Column(name = "id")
    @Comment("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @Comment("사용자 ID")
    private User user;

    @Column(name = "refresh_token", nullable = false)
    @Comment("사용자 refresh_token")
    private String refreshToken;

    @Column(name = "issues_at", nullable = false)
    @Comment("refresh_token 발행한 시간")
    private LocalDateTime issuesAt;

    @Column(name = "expires_at", nullable = false)
    @Comment("refresh_token 만료 시간")
    private LocalDateTime expiresAt;

    public boolean isNotValid() {
        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(issuesAt) ||
                expiresAt.isBefore(now);
    }

    public void expires() {
        this.delete();
        throw new ExpiredTokenException();
    }
}
