package letsit_backend.dto;
import java.util.List;

public class ProjectDto {
    private Long postId;
    private String prtTitle;
    private String regionId;
    private String onoff;
    private List<String> requiredStack;
    private String difficulty;
    private Long userId;

    // Getters and Setters

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPrtTitle() {
        return prtTitle;
    }

    public void setPrtTitle(String prtTitle) {
        this.prtTitle = prtTitle;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getOnoff() {
        return onoff;
    }

    public void setOnoff(String onoff) {
        this.onoff = onoff;
    }

    public List<String> getRequiredStack() {
        return requiredStack;
    }

    public void setRequiredStack(List<String> requiredStack) {
        this.requiredStack = requiredStack;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}