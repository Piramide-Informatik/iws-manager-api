package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.contractordercommission.*;
import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import com.iws_manager.iws_manager_api.models.BasicContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractOrderCommissionMapper {

    // Convert Entity → DTO 
    public ContractOrderCommissionDTO toDTO(ContractOrderCommission entity) {
        if (entity == null) {
            return null;
        }
        
        return new ContractOrderCommissionDTO(
            entity.getId(),
            entity.getCommission(),
            entity.getFromOrderValue(),
            entity.getMinCommission(),
            entity.getBasicContract() != null ? entity.getBasicContract().getId() : null,
            entity.getBasicContract() != null ? entity.getBasicContract().getContractLabel() : null,
            entity.getBasicContract() != null ? entity.getBasicContract().getContractNo() : null
        );
    }

    // Convert Entity → Detailed DTO 
    public ContractOrderCommissionDetailDTO toDetailDTO(ContractOrderCommission entity) {
        if (entity == null) {
            return null;
        }
        
        BasicContractInfoDTO basicContractDTO = null;
        if (entity.getBasicContract() != null) {
            BasicContract basicContract = entity.getBasicContract();
            basicContractDTO = new BasicContractInfoDTO(
                basicContract.getId(),
                basicContract.getContractLabel(),
                basicContract.getContractNo(),
                basicContract.getContractTitle(),
                basicContract.getConfirmationDate()
            );
        }
        
        return new ContractOrderCommissionDetailDTO(
            entity.getId(),
            entity.getCommission(),
            entity.getFromOrderValue(),
            entity.getMinCommission(),
            basicContractDTO
        );
    }

    // Convert input DTO → Entity
    public ContractOrderCommission toEntity(ContractOrderCommissionInputDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ContractOrderCommission entity = new ContractOrderCommission();
        entity.setCommission(dto.commission());
        entity.setFromOrderValue(dto.fromOrderValue());
        entity.setMinCommission(dto.minCommission());
        
        // ✅ CORREGIDO: Usar basicContract() consistentemente
        if (dto.basicContract() != null) {
            BasicContract basicContract = new BasicContract();
            basicContract.setId(dto.basicContract().id());
            basicContract.setVersion(dto.basicContract().version());  // ← Importante!
            entity.setBasicContract(basicContract);
        }
        
        return entity;
    }

    // Convert entities list → list of DTOs
    public List<ContractOrderCommissionDTO> toDTOList(List<ContractOrderCommission> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Update with dto data - CORREGIDO
    public void updateEntityFromDTO(ContractOrderCommissionInputDTO dto, ContractOrderCommission entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setCommission(dto.commission());
        entity.setFromOrderValue(dto.fromOrderValue());
        entity.setMinCommission(dto.minCommission());
        
        // ✅ CORREGIDO: Usar basicContract() en lugar de basicContractId()
        if (dto.basicContract() != null) {
            BasicContract basicContract = new BasicContract();
            basicContract.setId(dto.basicContract().id());
            basicContract.setVersion(dto.basicContract().version());  // ← Agregar version aquí también
            entity.setBasicContract(basicContract);
        }
    }
}