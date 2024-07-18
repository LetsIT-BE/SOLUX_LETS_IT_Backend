package letsit_backend.service;

import letsit_backend.dto.ProjectDto;
import letsit_backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectDto> getProjectsByUserId(Long userId) {
        // 기존 엔티티와 매핑하는 로직을 추가합니다.
        return projectRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProjectDto convertToDto(Object[] entity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setPostId((Long) entity[0]);
        projectDto.setPrtTitle((String) entity[1]);
        projectDto.setRegionId((String) entity[2]);
        projectDto.setOnoff((String) entity[3]);
        projectDto.setRequiredStack((List<String>) entity[4]);
        projectDto.setDifficulty((String) entity[5]);
        projectDto.setUserId((Long) entity[6]);
        return projectDto;
    }
}