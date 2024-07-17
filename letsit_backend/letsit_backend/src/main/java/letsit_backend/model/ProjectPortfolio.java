package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ProjectPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member fk_userId;

    @Column(nullable = false)
    private String prtTitle;

    @Column(nullable = false)
    private String prtContents;

    @CreationTimestamp
    private Timestamp prtCreateDate;

    @UpdateTimestamp
    private Timestamp prtUpdateDate;

}
