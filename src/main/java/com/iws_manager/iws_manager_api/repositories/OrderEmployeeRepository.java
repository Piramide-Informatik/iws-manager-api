package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.OrderEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderEmployeeRepository extends JpaRepository<OrderEmployee, Long> {

    // Consultas básicas con EntityGraph para cargar relaciones
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findAll();

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findAllByOrderByIdAsc();

    // Consultas por empleado
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByEmployeeId(Long employeeId);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByEmployeeIdOrderByIdAsc(Long employeeId);

    // Consultas por orden
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByOrderId(Long orderId);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByOrderIdOrderByIdAsc(Long orderId);

    // Consultas por qualificationFZ
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByQualificationFZId(Long qualificationFZId);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByQualificationFZIdOrderByIdAsc(Long qualificationFZId);

    // Consultas por qualificationkmui
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByQualificationkmui(String qualificationkmui);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByQualificationkmuiContainingIgnoreCase(String qualificationkmui);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByQualificationkmuiOrderByIdAsc(String qualificationkmui);

    // Consultas por título
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByTitle(String title);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByTitleContainingIgnoreCase(String title);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByTitleOrderByIdAsc(String title);

    // Consultas combinadas (empleado + orden)
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByEmployeeIdAndOrderId(Long employeeId, Long orderId);

    // Consultas combinadas (orden + qualificationFZ)
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByOrderIdAndQualificationFZId(Long orderId, Long qualificationFZId);

    // Consultas combinadas (empleado + qualificationFZ)
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByEmployeeIdAndQualificationFZId(Long employeeId, Long qualificationFZId);

    // Consultas por rangos de hourlyrate
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByHourlyrateGreaterThan(BigDecimal hourlyrate);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByHourlyrateLessThan(BigDecimal hourlyrate);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByHourlyrateBetween(BigDecimal minHourlyrate, BigDecimal maxHourlyrate);

    // Consultas por rangos de plannedhours
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByPlannedhoursGreaterThan(BigDecimal plannedhours);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByPlannedhoursLessThan(BigDecimal plannedhours);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findByPlannedhoursBetween(BigDecimal minPlannedhours, BigDecimal maxPlannedhours);

    // Consultas de ordenamiento por campos específicos
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findAllByOrderByHourlyrateAsc();

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findAllByOrderByHourlyrateDesc();

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findAllByOrderByPlannedhoursAsc();

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findAllByOrderByPlannedhoursDesc();

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findAllByOrderByQualificationkmuiAsc();

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    List<OrderEmployee> findAllByOrderByTitleAsc();

    // Consultas JPQL personalizadas
    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    @Query("SELECT oe FROM OrderEmployee oe WHERE oe.hourlyrate >= :minRate AND oe.plannedhours >= :minHours")
    List<OrderEmployee> findWithMinimumRateAndHours(@Param("minRate") BigDecimal minRate, 
                                                     @Param("minHours") BigDecimal minHours);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    @Query("SELECT oe FROM OrderEmployee oe WHERE oe.employee.id = :employeeId AND oe.order.id = :orderId AND oe.qualificationFZ.id = :qualificationFZId")
    List<OrderEmployee> findByEmployeeOrderAndQualification(@Param("employeeId") Long employeeId, 
                                                             @Param("orderId") Long orderId, 
                                                             @Param("qualificationFZId") Long qualificationFZId);

    @EntityGraph(attributePaths = {"employee", "order", "qualificationFZ"})
    @Query("SELECT oe FROM OrderEmployee oe WHERE LOWER(oe.qualificationkmui) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(oe.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<OrderEmployee> findByQualificationOrTitleContaining(@Param("keyword") String keyword);

    // Consulta para calcular total cost (hourlyrate * plannedhours)
    @Query("SELECT SUM(oe.hourlyrate * oe.plannedhours) FROM OrderEmployee oe WHERE oe.order.id = :orderId")
    BigDecimal calculateTotalCostByOrder(@Param("orderId") Long orderId);

    @Query("SELECT SUM(oe.hourlyrate * oe.plannedhours) FROM OrderEmployee oe WHERE oe.employee.id = :employeeId")
    BigDecimal calculateTotalCostByEmployee(@Param("employeeId") Long employeeId);

    // Consultas de existencia
    boolean existsByEmployeeIdAndOrderId(Long employeeId, Long orderId);
    
    boolean existsByOrderId(Long orderId);
    
    boolean existsByEmployeeId(Long employeeId);
    
    boolean existsByQualificationFZId(Long qualificationFZId);

    // Consultas de conteo
    @Query("SELECT COUNT(oe) FROM OrderEmployee oe WHERE oe.order.id = :orderId")
    Long countByOrder(@Param("orderId") Long orderId);

    @Query("SELECT COUNT(oe) FROM OrderEmployee oe WHERE oe.employee.id = :employeeId")
    Long countByEmployee(@Param("employeeId") Long employeeId);

    // Consultas con proyecciones (si necesitas datos específicos)
    @Query("SELECT oe.hourlyrate, oe.plannedhours FROM OrderEmployee oe WHERE oe.order.id = :orderId")
    List<Object[]> findHourlyRatesAndPlannedHoursByOrder(@Param("orderId") Long orderId);

    // Consultas para obtener empleados únicos por orden
    @Query("SELECT DISTINCT oe.employee FROM OrderEmployee oe WHERE oe.order.id = :orderId")
    List<com.iws_manager.iws_manager_api.models.Employee> findDistinctEmployeesByOrder(@Param("orderId") Long orderId);
}