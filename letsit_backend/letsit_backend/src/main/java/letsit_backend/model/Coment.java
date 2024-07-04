package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Coment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comentId;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member userId;

    @Column(nullable = false)
    private String comContent;

    @CreationTimestamp
    private Timestamp comCreateDate;

    @UpdateTimestamp
    private Timestamp comUpdateDate;


}
