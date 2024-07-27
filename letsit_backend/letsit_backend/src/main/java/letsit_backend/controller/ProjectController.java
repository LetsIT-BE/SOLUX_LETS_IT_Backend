package letsit_backend.controller;

import letsit_backend.dto.OngoingProjectDto;
import letsit_backend.dto.ProjectDto;
import letsit_backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping("/{userId}/organizinglist")
//    public ResponseEntity<List<ProjectDto>> getOrganizingList(@PathVariable("userId") Long userId) {
//        List<ProjectDto> projects = projectService.getProjectsByUserId(userId);
//        return ResponseEntity.ok(projects);
//    }

    @GetMapping("/{userId}/organizinglist")
    public ResponseEntity<Map<String, List<ProjectDto>>> getOrganizingList(@PathVariable("userId") Long userId) {
        List<ProjectDto> projects = projectService.getProjectsByUserId(userId);
        Map<String, List<ProjectDto>> response = new HashMap<>();
        response.put("projects", projects);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{userId}/appliedlist")
//    public ResponseEntity<List<ProjectDto>> getAppliedList(@PathVariable("userId") Long userId) {
//        List<ProjectDto> projects = projectService.getProjectsByUserId(userId);
//        return ResponseEntity.ok(projects);
//    }

    @GetMapping("/{userId}/appliedlist")
    public ResponseEntity<Map<String, List<ProjectDto>>> getAppliedList(@PathVariable("userId") Long userId) {
        List<ProjectDto> projects = projectService.getProjectsByUserId(userId);
        Map<String, List<ProjectDto>> response = new HashMap<>();
        response.put("projects", projects);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{userId}/ongoinglist")
//    public ResponseEntity<List<OngoingProjectDto>> getOngoingList(@PathVariable("userId") Long userId) {
//        List<OngoingProjectDto> ongoingProjects = projectService.getOngoingProjectsByUserId(userId);
//        return ResponseEntity.ok(ongoingProjects);
//    }

    @GetMapping("/{userId}/ongoinglist")
    public ResponseEntity<Map<String, List<OngoingProjectDto>>> getOngoingList(@PathVariable("userId") Long userId) {
        List<OngoingProjectDto> ongoingProjects = projectService.getOngoingProjectsByUserId(userId);
        Map<String, List<OngoingProjectDto>> response = new HashMap<>();
        response.put("projects", ongoingProjects);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{userId}/completedlist")
//    public ResponseEntity<List<OngoingProjectDto>> getCompletedList(@PathVariable("userId") Long userId) {
//        List<OngoingProjectDto> ongoingProjects = projectService.getOngoingProjectsByUserId(userId);
//        return ResponseEntity.ok(ongoingProjects);
//    }

    @GetMapping("/{userId}/completedlist")
    public ResponseEntity<Map<String, List<OngoingProjectDto>>> getCompletedList(@PathVariable("userId") Long userId) {
        List<OngoingProjectDto> completedProjects = projectService.getCompletedProjectsByUserId(userId);
        Map<String, List<OngoingProjectDto>> response = new HashMap<>();
        response.put("projects", completedProjects);
        return ResponseEntity.ok(response);
    }
}
