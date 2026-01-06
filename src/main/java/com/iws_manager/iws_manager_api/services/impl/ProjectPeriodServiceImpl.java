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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectPeriodServiceImpl implements ProjectPeriodService {
    private final ProjectPeriodRepository projectPeriodRepository;
    private final ProjectRepository projectRepository;

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
                    existingProjectPeriod.setPeriodNo(projectPeriodDetails.getPeriodNo());
                    existingProjectPeriod.setStartDate(projectPeriodDetails.getStartDate());
                    existingProjectPeriod.setEndDate(projectPeriodDetails.getEndDate());
                    existingProjectPeriod.setProject(projectPeriodDetails.getProject());
                    validateProjectPeriod(existingProjectPeriod);
                    return projectPeriodRepository.save(existingProjectPeriod);
                })
                .orElseThrow(() -> new RuntimeException("ProjectPeriod not found with id " + id));
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

        projectPeriod.setPeriodNo(newYear);
        projectPeriod.setProject(project);
        // Default Dates
        setDefaultDates(projectPeriod);

        validateProjectPeriod(projectPeriod);
        return projectPeriodRepository.save(projectPeriod);
    }

    @Override
    public ProjectPeriod createDefaultPeriodForProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("project cannot be null");
        }

        ProjectPeriod projectPeriod = new ProjectPeriod();
        projectPeriod.setPeriodNo((short) 1);
        projectPeriod.setProject(project);

        // Use project's dates if they exist, otherwise leave empty
        projectPeriod.setStartDate(project.getStartDate());
        projectPeriod.setEndDate(project.getEndDate());

        validateProjectPeriod(projectPeriod);
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

        validateDateRange(projectPeriod);

        if (projectPeriod.getProject() == null || projectPeriod.getProject().getId() == null) {
            return;
        }

        validatePeriodSequence(projectPeriod);
    }

    private void validateDateRange(ProjectPeriod projectPeriod) {
        LocalDate start = projectPeriod.getStartDate();
        LocalDate end = projectPeriod.getEndDate();
        if (start != null && end != null && start.isAfter(end)) {
            throw new IllegalArgumentException("The start date cannot be after the end date");
        }
    }

    private void validatePeriodSequence(ProjectPeriod projectPeriod) {
        Short currentPeriodNo = projectPeriod.getPeriodNo();
        if (currentPeriodNo == null) {
            return;
        }

        Long projectId = projectPeriod.getProject().getId();
        validateAgainstPreviousPeriod(projectPeriod, projectId, currentPeriodNo);
        validateAgainstNextPeriod(projectPeriod, projectId, currentPeriodNo);
    }

    private void validateAgainstPreviousPeriod(ProjectPeriod projectPeriod, Long projectId, Short currentPeriodNo) {
        if (currentPeriodNo <= 1) {
            return;
        }

        projectPeriodRepository.findByProjectIdAndPeriodNo(projectId, (short) (currentPeriodNo - 1))
                .ifPresent(prev -> {
                    if (projectPeriod.getStartDate() != null && prev.getEndDate() != null
                            && !projectPeriod.getStartDate().isAfter(prev.getEndDate())) {
                        throw new IllegalArgumentException("The start date of the period "
                                + currentPeriodNo + " (" + projectPeriod.getStartDate()
                                + ") cannot be less than or equal to the end date of the previous period " +
                                (currentPeriodNo - 1) + " (" + prev.getEndDate() + ")");
                    }
                });
    }

    private void validateAgainstNextPeriod(ProjectPeriod projectPeriod, Long projectId, Short currentPeriodNo) {
        projectPeriodRepository.findByProjectIdAndPeriodNo(projectId, (short) (currentPeriodNo + 1))
                .ifPresent(next -> {
                    if (projectPeriod.getEndDate() != null && next.getStartDate() != null
                            && !projectPeriod.getEndDate().isBefore(next.getStartDate())) {
                        throw new IllegalArgumentException("The end date of the period " + currentPeriodNo +
                                " (" + projectPeriod.getEndDate()
                                + ") It cannot be greater than or equal to the start date of the following period "
                                + (currentPeriodNo + 1) + " (" + next.getStartDate() + ")");
                    }
                });
    }
}
