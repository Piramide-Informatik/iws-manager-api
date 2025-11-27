package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.services.interfaces.v2.ProjectServiceV2;
import com.iws_manager.iws_manager_api.dtos.project.ProjectResponseDTO;
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

import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.repositories.CustomerRepository;
import com.iws_manager.iws_manager_api.repositories.EmployeeIwsRepository;
import com.iws_manager.iws_manager_api.repositories.OrderRepository;
import com.iws_manager.iws_manager_api.repositories.NetworkRepository;
import com.iws_manager.iws_manager_api.repositories.FundingProgramRepository;
import com.iws_manager.iws_manager_api.repositories.PromoterRepository;
import com.iws_manager.iws_manager_api.repositories.ProjectStatusRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

        // Convert DTO to Entity
        Project project = convertToEntity(projectRequest);
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
                    // Update fields from DTO
                    updateEntityFromDTO(existingProject, projectDetails);
                    Project updatedProject = projectRepository.save(existingProject);
                    return ProjectMapper.toResponseDTO(updatedProject);
                })
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        projectRepository.deleteById(id);
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
        // Campos básicos
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

        // Relaciones - cargar entidades desde los IDs
        if (dto.customer() != null) {
            Customer customer = customerRepository.findById(dto.customer().id())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + dto.customer().id()));
            project.setCustomer(customer);
        } else {
            project.setCustomer(null);
        }

        if (dto.empiws20() != null) {
            EmployeeIws empiws20 = employeeIwsRepository.findById(dto.empiws20().id())
                    .orElseThrow(() -> new RuntimeException("EmployeeIws not found with id: " + dto.empiws20().id()));
            project.setEmpiws20(empiws20);
        } else {
            project.setEmpiws20(null);
        }

        if (dto.empiws30() != null) {
            EmployeeIws empiws30 = employeeIwsRepository.findById(dto.empiws30().id())
                    .orElseThrow(() -> new RuntimeException("EmployeeIws not found with id: " + dto.empiws30().id()));
            project.setEmpiws30(empiws30);
        } else {
            project.setEmpiws30(null);
        }

        if (dto.empiws50() != null) {
            EmployeeIws empiws50 = employeeIwsRepository.findById(dto.empiws50().id())
                    .orElseThrow(() -> new RuntimeException("EmployeeIws not found with id: " + dto.empiws50().id()));
            project.setEmpiws50(empiws50);
        } else {
            project.setEmpiws50(null);
        }

        if (dto.orderFue() != null) {
            Order orderFue = orderRepository.findById(dto.orderFue().id())
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + dto.orderFue().id()));
            project.setOrderFue(orderFue);
        } else {
            project.setOrderFue(null);
        }

        if (dto.orderAdmin() != null) {
            Order orderAdmin = orderRepository.findById(dto.orderAdmin().id())
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + dto.orderAdmin().id()));
            project.setOrderAdmin(orderAdmin);
        } else {
            project.setOrderAdmin(null);
        }

        if (dto.network() != null) {
            Network network = networkRepository.findById(dto.network().id())
                    .orElseThrow(() -> new RuntimeException("Network not found with id: " + dto.network().id()));
            project.setNetwork(network);
        } else {
            project.setNetwork(null);
        }

        if (dto.fundingProgram() != null) {
            FundingProgram fundingProgram = fundingProgramRepository.findById(dto.fundingProgram().id())
                    .orElseThrow(() -> new RuntimeException(
                            "FundingProgram not found with id: " + dto.fundingProgram().id()));
            project.setFundingProgram(fundingProgram);
        } else {
            project.setFundingProgram(null);
        }

        if (dto.promoter() != null) {
            Promoter promoter = promoterRepository.findById(dto.promoter().id())
                    .orElseThrow(() -> new RuntimeException("Promoter not found with id: " + dto.promoter().id()));
            project.setPromoter(promoter);
        } else {
            project.setPromoter(null);
        }

        if (dto.status() != null) {
            ProjectStatus status = projectStatusRepository.findById(dto.status().id())
                    .orElseThrow(() -> new RuntimeException("ProjectStatus not found with id: " + dto.status().id()));
            project.setStatus(status);
        } else {
            project.setStatus(null);
        }
    }
}