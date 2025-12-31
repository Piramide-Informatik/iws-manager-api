package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.services.interfaces.ProjectServiceV2;
import com.iws_manager.iws_manager_api.dtos.project.ProjectResponseDTO;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.project.ProjectRequestDTO;
import com.iws_manager.iws_manager_api.mappers.ProjectMapper;
import com.iws_manager.iws_manager_api.models.EmployeeIws;
import com.iws_manager.iws_manager_api.models.FundingProgram;
import com.iws_manager.iws_manager_api.models.Network;
import com.iws_manager.iws_manager_api.models.Order;
import com.iws_manager.iws_manager_api.models.Project;
import com.iws_manager.iws_manager_api.models.ProjectStatus;
import com.iws_manager.iws_manager_api.models.Promoter;
import com.iws_manager.iws_manager_api.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.EntityNotFoundException;

import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.repositories.CustomerRepository;
import com.iws_manager.iws_manager_api.repositories.EmployeeIwsRepository;
import com.iws_manager.iws_manager_api.repositories.OrderRepository;
import com.iws_manager.iws_manager_api.repositories.NetworkRepository;
import com.iws_manager.iws_manager_api.repositories.FundingProgramRepository;
import com.iws_manager.iws_manager_api.repositories.PromoterRepository;
import com.iws_manager.iws_manager_api.repositories.ProjectStatusRepository;
import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * V2 Implementation of the {@link ProjectServiceV2} interface using DTOs.
 * Provides CRUD operations and business logic for Project management with DTOs.
 */
@Service
@Transactional
public class ProjectServiceImplV2 implements ProjectServiceV2 {

    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeIwsRepository employeeIwsRepository;
    private final OrderRepository orderRepository;
    private final NetworkRepository networkRepository;
    private final FundingProgramRepository fundingProgramRepository;
    private final PromoterRepository promoterRepository;
    private final ProjectStatusRepository projectStatusRepository;

    @Autowired
    public ProjectServiceImplV2(ProjectRepository projectRepository,
            CustomerRepository customerRepository,
            EmployeeIwsRepository employeeIwsRepository,
            OrderRepository orderRepository,
            NetworkRepository networkRepository,
            FundingProgramRepository fundingProgramRepository,
            PromoterRepository promoterRepository,
            ProjectStatusRepository projectStatusRepository) {
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
        this.employeeIwsRepository = employeeIwsRepository;
        this.orderRepository = orderRepository;
        this.networkRepository = networkRepository;
        this.fundingProgramRepository = fundingProgramRepository;
        this.promoterRepository = promoterRepository;
        this.projectStatusRepository = projectStatusRepository;
    }

    @Override
    public ProjectResponseDTO create(ProjectRequestDTO projectRequest) {
        if (projectRequest == null) {
            throw new IllegalArgumentException("ProjectRequestDTO cannot be null");
        }

        validateUniqueProjectNameForCreation(projectRequest.projectName());
        // Convert DTO to Entity
        Project project = convertToEntity(projectRequest);

        //Default Dates
        if ( project.getStartDate() == null || project.getEndDate() == null ) {
            int year = LocalDate.now().getYear();
            project.setStartDate(LocalDate.of(year, 1, 1));
            project.setEndDate(LocalDate.of(year, 12, 31));
        }
        Project savedProject = projectRepository.save(project);

        return ProjectMapper.toResponseDTO(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectResponseDTO> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return projectRepository.findById(id)
                .map(ProjectMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> findAll() {
        return projectRepository.findAllByOrderByProjectLabelAsc()
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDTO update(Long id, ProjectRequestDTO projectDetails) {
        if (id == null || projectDetails == null) {
            throw new IllegalArgumentException("ID and project details cannot be null");
        }

        return projectRepository.findById(id)
                .map(existingProject -> {
                    validateUniqueProjectNameForUpdate(existingProject, projectDetails, id);
                    // Update fields from DTO
                    updateEntityFromDTO(existingProject, projectDetails);
                    Project updatedProject = projectRepository.save(existingProject);
                    return ProjectMapper.toResponseDTO(updatedProject);
                })
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!projectRepository.existsById(id)) {
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    // VALIDATIONS
    /**
     * Validates that the projectName is unique for creation (case-insensitive) across all projects.
     */
    private void validateUniqueProjectNameForCreation(String projectName) {
        if (projectName != null && projectRepository.existsByProjectNameIgnoreCase(projectName)) {
            throw new DuplicateResourceException(
                "Project duplication with attribute 'projectName' = '" + projectName + "'. " +
                "A project with this name already exists."
            );
        }
    }

    /**
     * Validates that the projectName is unique for update, considering only other records (case-insensitive).
     * Only validates if the projectName has changed.
     */
    private void validateUniqueProjectNameForUpdate(Project existingProject, 
                                                ProjectRequestDTO newProjectDTO, 
                                                Long id) {
        
        // Check if projectName has changed
        boolean projectNameChanged = !existingProject.getProjectName().equals(newProjectDTO.projectName());
        
        if (projectNameChanged) {
            boolean projectNameExists = projectRepository
                .existsByProjectNameIgnoreCaseAndIdNot(newProjectDTO.projectName(), id);
            
            if (projectNameExists) {
                throw new DuplicateResourceException(
                    "Project duplication with attribute 'projectName' = '" + newProjectDTO.projectName() + "'. " +
                    "A project with this name already exists."
                );
            }
        }
    }

    // Date Fields
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByApprovalDate(LocalDate approvalDate) {
        return projectRepository.findByApprovalDate(approvalDate)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByAuthorizationDate(LocalDate authorizationDate) {
        return projectRepository.findByAuthorizationDate(authorizationDate)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByEndApproval(LocalDate endApproval) {
        return projectRepository.findByEndApproval(endApproval)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByEndDate(LocalDate endDate) {
        return projectRepository.findByEndDate(endDate)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByStartApproval(LocalDate startApproval) {
        return projectRepository.findByStartApproval(startApproval)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByStartDate(LocalDate startDate) {
        return projectRepository.findByStartDate(startDate)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Number Fields
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByChance(BigDecimal chance) {
        return projectRepository.findByChance(chance)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByFundingRate(BigDecimal fundingRate) {
        return projectRepository.findByFundingRate(fundingRate)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByHourlyRateMueu(BigDecimal hourlyRateMueu) {
        return projectRepository.findByHourlyRateMueu(hourlyRateMueu)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByMaxHoursPerMonth(BigDecimal maxHoursPerMonth) {
        return projectRepository.findByMaxHoursPerMonth(maxHoursPerMonth)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByMaxHoursPerYear(BigDecimal maxHoursPerYear) {
        return projectRepository.findByMaxHoursPerYear(maxHoursPerYear)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByProductiveHoursPerYear(BigDecimal productiveHoursPerYear) {
        return projectRepository.findByProductiveHoursPerYear(productiveHoursPerYear)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByShareResearch(BigDecimal shareResearch) {
        return projectRepository.findByShareResearch(shareResearch)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByStuffFlat(BigDecimal stuffFlat) {
        return projectRepository.findByStuffFlat(stuffFlat)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Order Fields
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByOrderIdFue(Long orderIdFue) {
        return projectRepository.findByOrderFueId(orderIdFue)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByOrderIdAdmin(Long orderIdAdmin) {
        return projectRepository.findByOrderAdminId(orderIdAdmin)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Text Fields
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByCommentContaining(String keyword) {
        return projectRepository.findByCommentContaining(keyword)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByFinanceAuthority(String authority) {
        return projectRepository.findByFinanceAuthority(authority)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByFundingLabel(String label) {
        return projectRepository.findByFundingLabel(label)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByNoteContaining(String text) {
        return projectRepository.findByNoteContaining(text)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByProjectLabel(String projectLabel) {
        return projectRepository.findByProjectLabel(projectLabel)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByProjectName(String projectName) {
        return projectRepository.findByProjectName(projectName)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByTitle(String title) {
        return projectRepository.findByTitle(title)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Entity Relationships
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByCustomerId(Long customerId) {
        return projectRepository.findByCustomerIdOrderByProjectLabelAsc(customerId)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByEmpiws20Id(Long empiws20Id) {
        return projectRepository.findByEmpiws20Id(empiws20Id)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByFundingProgramId(Long fundingProgramId) {
        return projectRepository.findByFundingProgramId(fundingProgramId)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByPromoterId(Long promoterId) {
        return projectRepository.findByPromoterId(promoterId)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByStatusId(Long statusId) {
        return projectRepository.findByStatusId(statusId)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Date Helpers
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByApprovalDateBetween(LocalDate start, LocalDate end) {
        return projectRepository.findByApprovalDateBetween(start, end)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByAuthorizationDateBefore(LocalDate date) {
        return projectRepository.findByAuthorizationDateBefore(date)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByEndDateAfter(LocalDate date) {
        return projectRepository.findByEndDateAfter(date)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByStartDateBetween(LocalDate start, LocalDate end) {
        return projectRepository.findByStartDateBetween(start, end)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Number Helpers
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByChanceGreaterThan(BigDecimal chance) {
        return projectRepository.findByChanceGreaterThan(chance)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByFundingRateLessThan(BigDecimal fundingRate) {
        return projectRepository.findByFundingRateLessThan(fundingRate)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByHourlyRateMueuBetween(BigDecimal min, BigDecimal max) {
        return projectRepository.findByHourlyRateMueuBetween(min, max)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Text Helpers
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByProjectNameContainingIgnoreCase(String name) {
        return projectRepository.findByProjectNameContainingIgnoreCase(name)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByFundingLabelStartingWith(String prefix) {
        return projectRepository.findByFundingLabelStartingWith(prefix)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByTitleEndingWith(String suffix) {
        return projectRepository.findByTitleEndingWith(suffix)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Special Queries
    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByCustomerIdOrderByStartDateDesc(Long customerId) {
        return projectRepository.findByCustomerIdOrderByStartDateDesc(customerId)
                .stream()
                .map(ProjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Helper methods for DTO-Entity conversion
    private Project convertToEntity(ProjectRequestDTO dto) {
        Project project = new Project();
        updateEntityFromDTO(project, dto);
        return project;
    }

    private void updateEntityFromDTO(Project project, ProjectRequestDTO dto) {
        setBasicFields(project, dto);
        setRelationshipFields(project, dto);
    }

    private void setBasicFields(Project project, ProjectRequestDTO dto) {
        project.setApprovalDate(dto.approvalDate());
        project.setAuthorizationDate(dto.authorizationDate());
        project.setChance(dto.chance());
        project.setComment(dto.comment());
        project.setDate1(dto.date1());
        project.setDate2(dto.date2());
        project.setDate3(dto.date3());
        project.setDate4(dto.date4());
        project.setDate5(dto.date5());
        project.setDateLevel1(dto.dateLevel1());
        project.setDateLevel2(dto.dateLevel2());
        project.setDonation(dto.donation());
        project.setEndApproval(dto.endApproval());
        project.setEndDate(dto.endDate());
        project.setFinanceAuthority(dto.financeAuthority());
        project.setFundingLabel(dto.fundingLabel());
        project.setFundingRate(dto.fundingRate());
        project.setHourlyRateMueu(dto.hourlyRateMueu());
        project.setIncome1(dto.income1());
        project.setIncome2(dto.income2());
        project.setIncome3(dto.income3());
        project.setIncome4(dto.income4());
        project.setIncome5(dto.income5());
        project.setMaxHoursPerMonth(dto.maxHoursPerMonth());
        project.setMaxHoursPerYear(dto.maxHoursPerYear());
        project.setStuffFlat(dto.stuffFlat());
        project.setProductiveHoursPerYear(dto.productiveHoursPerYear());
        project.setProjectLabel(dto.projectLabel());
        project.setProjectName(dto.projectName());
        project.setNote(dto.note());
        project.setShareResearch(dto.shareResearch());
        project.setStartApproval(dto.startApproval());
        project.setStartDate(dto.startDate());
        project.setTitle(dto.title());
    }

    private void setRelationshipFields(Project project, ProjectRequestDTO dto) {
        setCustomerRelationship(project, dto);
        setEmployeeRelationships(project, dto);
        setOrderRelationships(project, dto);
        setOtherRelationships(project, dto);
    }

    private void setCustomerRelationship(Project project, ProjectRequestDTO dto) {
        if (dto.customer() != null) {
            Customer customer = customerRepository.findById(dto.customer().id())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + dto.customer().id()));
            project.setCustomer(customer);
        } else {
            project.setCustomer(null);
        }
    }

    private void setEmployeeRelationships(Project project, ProjectRequestDTO dto) {
        String employeeIws = "EmployeeIws";
        setEmployeeRelationship(project::setEmpiws20, dto.empiws20(), employeeIws, employeeIwsRepository);
        setEmployeeRelationship(project::setEmpiws30, dto.empiws30(), employeeIws, employeeIwsRepository);
        setEmployeeRelationship(project::setEmpiws50, dto.empiws50(), employeeIws, employeeIwsRepository);
    }

    private void setOrderRelationships(Project project, ProjectRequestDTO dto) {
        setRelationship(project::setOrderFue, dto.orderFue(), "Order", orderRepository);
        setRelationship(project::setOrderAdmin, dto.orderAdmin(), "Order", orderRepository);
    }

    private void setOtherRelationships(Project project, ProjectRequestDTO dto) {
        setRelationship(project::setNetwork, dto.network(), "Network", networkRepository);
        setRelationship(project::setFundingProgram, dto.fundingProgram(), "FundingProgram", fundingProgramRepository);
        setRelationship(project::setPromoter, dto.promoter(), "Promoter", promoterRepository);
        setRelationship(project::setStatus, dto.status(), "ProjectStatus", projectStatusRepository);
    }

    private <T> void setRelationship(Consumer<T> setter, BasicReferenceDTO reference,
            String entityName, JpaRepository<T, Long> repository) {
        if (reference != null) {
            T entity = repository.findById(reference.id())
                    .orElseThrow(() -> new RuntimeException(entityName + " not found with id: " + reference.id()));
            setter.accept(entity);
        } else {
            setter.accept(null);
        }
    }

    private void setEmployeeRelationship(Consumer<EmployeeIws> setter, BasicReferenceDTO reference,
            String entityName, EmployeeIwsRepository repository) {
        setRelationship(setter, reference, entityName, repository);
    }

}