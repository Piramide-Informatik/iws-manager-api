package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.ProjectPeriod;
import com.iws_manager.iws_manager_api.repositories.ProjectPeriodRepository;
import com.iws_manager.iws_manager_api.repositories.ProjectRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import com.iws_manager.iws_manager_api.exception.exceptions.ConflictException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectPeriodServiceImpl implements ProjectPeriodService {
    private final ProjectPeriodRepository projectPeriodRepository;
    private final ProjectRepository projectRepository;

    private static final String PERIOD_STRING = "Period ";

    @Autowired
    public ProjectPeriodServiceImpl(ProjectPeriodRepository projectPeriodRepository,
            ProjectRepository projectRepository) {
        this.projectPeriodRepository = projectPeriodRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectPeriod create(ProjectPeriod projectPeriod) {
        if (projectPeriod == null) {
            throw new IllegalArgumentException("projectPeriod cannot be null");
        }
        validateProjectPeriod(projectPeriod);
        validateNoOverlap(projectPeriod, null);
        return projectPeriodRepository.save(projectPeriod);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectPeriod> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return projectPeriodRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> findAll() {
        return projectPeriodRepository.findAll();
    }

    @Override
    public ProjectPeriod update(Long id, ProjectPeriod projectPeriodDetails) {
        if (id == null || projectPeriodDetails == null) {
            throw new IllegalArgumentException("id and projectPeriodDetails cannot be null");
        }
        return projectPeriodRepository.findById(id)
                .map(existingProjectPeriod -> {
                    validateDateRange(projectPeriodDetails.getStartDate(), projectPeriodDetails.getEndDate());
                    validateNoOverlapForUpdate(
                            existingProjectPeriod.getProject().getId(),
                            projectPeriodDetails.getStartDate(),
                            projectPeriodDetails.getEndDate(),
                            id,
                            existingProjectPeriod.getPeriodNo());

                    existingProjectPeriod.setPeriodNo(projectPeriodDetails.getPeriodNo());
                    existingProjectPeriod.setStartDate(projectPeriodDetails.getStartDate());
                    existingProjectPeriod.setEndDate(projectPeriodDetails.getEndDate());
                    return projectPeriodRepository.save(existingProjectPeriod);
                })
                .orElseThrow(() -> new EntityNotFoundException("ProjectPeriod not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        if (!projectPeriodRepository.existsById(id)) {
            throw new EntityNotFoundException("ProjectPeriod not found with id: " + id);
        }

        projectPeriodRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> getAllProjectPeriodsByPeriodNoAsc() {
        return projectPeriodRepository.findAllByOrderByPeriodNoAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> getAllProjectPeriodsByStartDateAsc() {
        return projectPeriodRepository.findAllByOrderByStartDateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> getAllProjectPeriodsByEndDateAsc() {
        return projectPeriodRepository.findAllByOrderByEndDateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPeriod> findAllByProjectId(Long id) {
        return projectPeriodRepository.findAllByProjectIdFetchProject(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Short getNextYear(Long projectId) {
        Short maxYear = projectPeriodRepository.findMaxPeriodNoByProject(projectId);
        return (maxYear == null) ? 1 : (short) (maxYear + 1);
    }

    @Override
    public ProjectPeriod createWithNextYear(ProjectPeriod projectPeriod, Long projectId) {
        if (projectPeriod == null) {
            throw new IllegalArgumentException("projectPeriod cannot be null");
        }
        Project project = projectRepository.getReferenceById(projectId);

        Short newYear = getNextYear(projectId);

        projectPeriod.setPeriodNo(newYear.toString());
        projectPeriod.setProject(project);
        // Default Dates
        setDefaultDates(projectPeriod);

        validateProjectPeriod(projectPeriod);
        validateNoOverlap(projectPeriod, null);
        return projectPeriodRepository.save(projectPeriod);
    }

    @Override
    public ProjectPeriod createDefaultPeriodForProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("project cannot be null");
        }

        ProjectPeriod projectPeriod = new ProjectPeriod();
        projectPeriod.setPeriodNo("1");
        projectPeriod.setProject(project);

        // Use project's dates if they exist, otherwise leave empty
        projectPeriod.setStartDate(project.getStartDate());
        projectPeriod.setEndDate(project.getEndDate());

        validateProjectPeriod(projectPeriod);
        validateNoOverlap(projectPeriod, null);
        return projectPeriodRepository.save(projectPeriod);
    }

    private void setDefaultDates(ProjectPeriod projectPeriod) {
        int year = LocalDate.now().getYear();
        if (projectPeriod.getStartDate() == null) {
            projectPeriod.setStartDate(LocalDate.of(year, 1, 1));
        }
        if (projectPeriod.getEndDate() == null) {
            projectPeriod.setEndDate(LocalDate.of(year, 12, 31));
        }
    }

    private void validateProjectPeriod(ProjectPeriod projectPeriod) {
        if (projectPeriod == null) {
            return;
        }

        validateDateRange(projectPeriod.getStartDate(), projectPeriod.getEndDate());
    }

    private void validateDateRange(ProjectPeriod projectPeriod) {
        validateDateRange(projectPeriod.getStartDate(), projectPeriod.getEndDate());
    }

    private void validateDateRange(LocalDate start, LocalDate end) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new IllegalArgumentException("The start date cannot be after the end date");
        }
    }

    /**
     * Main method to validate overlaps using the repository
     * 
     * @param projectPeriod Period to validate
     * @param excludeId     ID to exclude (for updates), null for creations
     */
    private void validateNoOverlap(ProjectPeriod projectPeriod, Long excludeId) {
        if (projectPeriod == null ||
                projectPeriod.getProject() == null ||
                projectPeriod.getProject().getId() == null ||
                projectPeriod.getStartDate() == null ||
                projectPeriod.getEndDate() == null) {
            // Can't validate if we don't have the necessary information
            return;
        }

        Long projectId = projectPeriod.getProject().getId();
        LocalDate startDate = projectPeriod.getStartDate();
        LocalDate endDate = projectPeriod.getEndDate();

        // Use the repository method to find overlapping periods
        List<ProjectPeriod> overlappingPeriods = projectPeriodRepository.findOverlappingPeriods(
                projectId, startDate, endDate, excludeId);

        // If there are overlapping periods, throw exception
        if (!overlappingPeriods.isEmpty()) {
            String errorMessage = buildOverlapErrorMessage(projectPeriod, overlappingPeriods);
            String fullMessage = "Overlapping Periods Detected: " + errorMessage;
            throw new ConflictException("PERIOD_OVERLAP", errorMessage, fullMessage);
        }
    }

    private void validateNoOverlapForUpdate(Long projectId, LocalDate startDate, LocalDate endDate,
            Long excludeId, String periodNo) {
        if (projectId == null || startDate == null || endDate == null) {
            return;
        }

        // First check quickly if there is an overlap
        boolean hasOverlap = projectPeriodRepository.existsOverlappingPeriod(
                projectId, startDate, endDate, excludeId);

        if (hasOverlap) {
            // If there is an overlap, get the details for the error message
            List<ProjectPeriod> overlappingPeriods = projectPeriodRepository.findOverlappingPeriods(
                    projectId, startDate, endDate, excludeId);

            String errorMessage = buildOverlapErrorMessageForUpdate(
                    startDate, endDate, periodNo, overlappingPeriods);
            String fullMessage = "Overlapping Periods Detected: " + errorMessage;
            throw new ConflictException("PERIOD_OVERLAP", errorMessage, fullMessage);
        }
    }

    private String buildOverlapErrorMessageForUpdate(LocalDate startDate, LocalDate endDate,
            String periodNo, List<ProjectPeriod> overlappingPeriods) {
        // Create a temporary period only for the error message
        ProjectPeriod tempPeriod = new ProjectPeriod();
        tempPeriod.setStartDate(startDate);
        tempPeriod.setEndDate(endDate);
        tempPeriod.setPeriodNo(periodNo);
        tempPeriod.setId(1L);

        return buildOverlapErrorMessage(tempPeriod, overlappingPeriods);
    }

    /**
     * Build a detailed error message with information about overlapping periods
     */
    private String buildOverlapErrorMessage(ProjectPeriod newPeriod, List<ProjectPeriod> overlappingPeriods) {
        StringBuilder details = new StringBuilder();

        // Build main message
        details.append("The period ");

        if (newPeriod.getPeriodNo() != null) {
            details.append(PERIOD_STRING).append(newPeriod.getPeriodNo()).append(" ");
        }

        details.append("(").append(newPeriod.getStartDate())
                .append(" to ").append(newPeriod.getEndDate()).append(")");

        if (newPeriod.getId() != null) {
            details.append(" cannot be updated because it overlaps with ");
        } else {
            details.append(" cannot be created because it overlaps with ");
        }

        // Add information about overlapping periods
        if (overlappingPeriods.size() == 1) {
            ProjectPeriod overlapping = overlappingPeriods.get(0);
            details.append(PERIOD_STRING).append(overlapping.getPeriodNo())
                    .append(" (").append(overlapping.getStartDate())
                    .append(" to ").append(overlapping.getEndDate()).append(")");
        } else {
            details.append("existing periods: ");
            for (int i = 0; i < overlappingPeriods.size(); i++) {
                ProjectPeriod overlapping = overlappingPeriods.get(i);
                details.append(PERIOD_STRING).append(overlapping.getPeriodNo())
                        .append(" (").append(overlapping.getStartDate())
                        .append(" to ").append(overlapping.getEndDate()).append(")");

                if (i < overlappingPeriods.size() - 1) {
                    details.append(", ");
                }
            }
        }

        return details.toString();
    }

}
