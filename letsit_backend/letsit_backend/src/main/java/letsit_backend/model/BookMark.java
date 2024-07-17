package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member fk_userId;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post fk_postId;


}
