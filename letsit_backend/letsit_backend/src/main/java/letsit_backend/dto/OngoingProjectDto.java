package letsit_backend.dto;

public class OngoingProjectDto {
    private String prjTitle;

    public OngoingProjectDto(String prjTitle) {
        this.prjTitle = prjTitle;
    }

    public String getPrjTitle() {
        return prjTitle;
    }

    public void setPrjTitle(String prjTitle) {
        this.prjTitle = prjTitle;
    }
}