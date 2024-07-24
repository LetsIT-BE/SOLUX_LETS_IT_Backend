package letsit_backend.controller;

import letsit_backend.dto.OngoingProjectDto;
import letsit_backend.dto.ProjectDto;
import letsit_backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{userId}/organizinglist")
    public ResponseEntity<List<ProjectDto>> getOrganizingList(@PathVariable Long userId) {
        List<ProjectDto> projects = projectService.getProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{userId}/ongoinglist")
    public ResponseEntity<List<OngoingProjectDto>> getOngoingList(@PathVariable Long userId) {
        List<OngoingProjectDto> projects = projectService.getOngoingProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }
}
