package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class TeamPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @OneToOne
    @JoinColumn(name = "POST_ID")
    private Post postId;

    @Column(nullable = false)
    private String prjTitle;

    @CreationTimestamp
    private Timestamp teamCreateDate;

    // 협업링크

    @Column(nullable = false)
    private Boolean isComplete;

}
