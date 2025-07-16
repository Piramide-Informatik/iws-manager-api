package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


import com.iws_manager.iws_manager_api.models.Contractor;
import com.iws_manager.iws_manager_api.repositories.ContractorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Contractor Service Implementation Tests")
public class ContractorServiceImplTest {
    @Mock
    private ContractorRepository contractorRepository;

    @InjectMocks
    private ContractorServiceImpl contractorService;

    private Contractor sampleContractor;

    @BeforeEach
    void setUp(){
        sampleContractor = new Contractor();
        sampleContractor.setId(1L);
        sampleContractor.setName("SRL");
    }
    @Test
    @DisplayName("Should save contractor successfully")
    void creatShouldReturnSavedTitle(){
        //Arrange
        when(contractorRepository.save(any(Contractor.class))).thenReturn(sampleContractor);

        //Act
        Contractor result = contractorService.create(sampleContractor);

        //Assert
        assertNotNull(result);
        assertEquals("SRL", result.getName());
        verify(contractorRepository,times(1)).save(any(Contractor.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null contractor")
    void createShouldThrowExceptionWhenTitleIsNull(){
        assertThrows(IllegalArgumentException.class,()-> contractorService.create(null));
        verify(contractorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find contractor by ID")
    void findByIdShouldReturnContractorWhenExists(){
        //Arrage
        when(contractorRepository.findById(1L)).thenReturn(Optional.of(sampleContractor));

        //Act
        Optional<Contractor> result = contractorService.findById(1L);

        //Assert
        assertTrue(result.isPresent());
        assertEquals("SRL", result.get().getName());
        verify(contractorRepository,times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull(){
        assertThrows(IllegalArgumentException.class, () -> contractorService.findById(null));
        verify(contractorRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all contractors")
    void findAllShouldReturnAllContractors(){
        //Arrange
        Contractor contractor2 = new Contractor();
        contractor2.setId(2L);
        contractor2.setName("LRS");

        when(contractorRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(sampleContractor,contractor2));

        //Act
        List<Contractor> result = contractorService.findAll();

        //Assert
        assertEquals(2, result.size());
        verify(contractorRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should return all contractor ordered by name")
    void findAllShouldReturnAllContractorOrderedByName(){
        //Arrange
        Contractor contractor1 = new Contractor();
        contractor1.setId(1L);
        contractor1.setName("A");

        Contractor contractor2 = new Contractor();
        contractor2.setId(2L);
        contractor2.setName("D");

        //Mockea el metodo que realmente usa el servicio
        when(contractorRepository.findAllByOrderByNameAsc())
                .thenReturn(List.of(contractor1,contractor2));

        //Act
        List<Contractor> result = contractorService.findAll();

        //Assert
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getName());//verfica el orden
        assertEquals("D", result.get(1).getName());
        verify(contractorRepository, times(1)).findAllByOrderByNameAsc();//Verfica el metodo correcto
    }
    @Test
    @DisplayName("Should throw exception when updating non-existent title")
    void updateShouldThrowExceptionWhenContractorNotFound(){
        //Arrange
        when(contractorRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RuntimeException.class,
                () -> contractorService.update(99L, new Contractor()));
        verify(contractorRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptioWhenOptimisticLockingFails(){
        //Setup
        Long contractorId = 1L;
        Contractor currentContractor = new Contractor();
        currentContractor.setId(contractorId);
        currentContractor.setName("SRL");
        currentContractor.setVersion(2L); //Current version in DB

        Contractor outdatedContractor = new Contractor();
        outdatedContractor.setId(contractorId);
        outdatedContractor.setName("LRS");
        outdatedContractor.setVersion(1L);

        when(contractorRepository.findById(contractorId)).thenReturn(Optional.of(currentContractor));
        when(contractorRepository.save(any(Contractor.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(Contractor.class, contractorId)));

        //Execution and verification
        Exception exception = assertThrows(RuntimeException.class,
                () -> contractorService.update(contractorId, outdatedContractor)
        );

        assertNotNull(exception, "An exception should have been thrown");

        //Verify if it's the direct exception or wrapped
        if(!(exception instanceof ObjectOptimisticLockingFailureException)){
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");

            //Verify repository interactions
            verify(contractorRepository).findById(contractorId);
            verify(contractorRepository.save(any(Contractor.class)));
        }
    }
}
