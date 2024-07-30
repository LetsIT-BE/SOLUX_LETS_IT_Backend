package letsit_backend.controller;

import letsit_backend.CurrentUser;
import letsit_backend.dto.OngoingProjectDto;
import letsit_backend.dto.ProjectDto;
import letsit_backend.dto.Response;
import letsit_backend.model.Member;
import letsit_backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/organizinglist")
    public ResponseEntity<Map<String, List<ProjectDto>>> getOrganizingList(@CurrentUser Member member) {
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body((Map<String, List<ProjectDto>>) Response.fail("인증되지 않은 회원"));
        }
        List<ProjectDto> projects = projectService.getProjectsByUserId(member);
        Map<String, List<ProjectDto>> response = new HashMap<>();
        response.put("projects", projects);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/appliedlist")
    public ResponseEntity<Map<String, List<ProjectDto>>> getAppliedList(@CurrentUser Member member) {
        List<ProjectDto> projects = projectService.getAppliedProjectsByUserId(member);
        Map<String, List<ProjectDto>> response = new HashMap<>();
        response.put("projects", projects);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ongoinglist")
    public ResponseEntity<Map<String, List<OngoingProjectDto>>> getOngoingList(@CurrentUser Member member) {
        List<OngoingProjectDto> ongoingProjects = projectService.getOngoingProjectsByUserId(member);
        Map<String, List<OngoingProjectDto>> response = new HashMap<>();
        response.put("projects", ongoingProjects);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/completedlist")
    public ResponseEntity<Map<String, List<OngoingProjectDto>>> getCompletedList(@CurrentUser Member member) {
        List<OngoingProjectDto> completedProjects = projectService.getCompletedProjectsByUserId(member);
        Map<String, List<OngoingProjectDto>> response = new HashMap<>();
        response.put("projects", completedProjects);
        return ResponseEntity.ok(response);
    }
}