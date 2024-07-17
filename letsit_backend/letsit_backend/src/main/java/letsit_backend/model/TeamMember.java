package letsit_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private TeamPost fk_teamId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member fk_userId;

    //private Long profileId;

    @Enumerated(EnumType.STRING)
    private Role teamMemberRole;
    public enum Role {
        Team_Leader,
        Team_Member
    }


    public TeamMember(TeamPost teamId, Member userId, Role teamMemberRole) {
        this.fk_teamId = teamId;
        this.fk_userId = userId;
        this.teamMemberRole = teamMemberRole;
    }

    public void setTeamMemberRole(Role teamMemberRole) {
        this.teamMemberRole = teamMemberRole;
    }
}
