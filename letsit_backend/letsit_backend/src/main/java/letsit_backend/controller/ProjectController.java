//package letsit_backend.controller;
//
//import letsit_backend.CurrentUser;
//import letsit_backend.dto.ProjectDto;
//import letsit_backend.model.Member;
//import letsit_backend.service.ProjectService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/projects")
//public class ProjectController {
//    private final ProjectService projectService;
//
//    @Autowired
//    public ProjectController(ProjectService projectService) {
//        this.projectService = projectService;
//    }
//
//    @GetMapping("/{userId}/organizinglist")
//    public ResponseEntity<List<ProjectDto>> getOrganizingList(@CurrentUser Member member) {
//        List<ProjectDto> projects = projectService.getProjectsByUserId(member);
//        return ResponseEntity.ok(projects);
//    }
//}
