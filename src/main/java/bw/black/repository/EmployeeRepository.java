package bw.black.repository;

import bw.black.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByActive(Integer active);

    @Query("SELECT e FROM Employee e WHERE e.active = 1 AND " +
            "(LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.surname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Employee> searchByKeyword(@Param("keyword") String keyword);

    Employee findByIdAndActive(Long id, Integer active);
    Optional<Employee> findByEmail(String email);
    /*boolean existsEmployeeByEmailIgnoreCaseAndActive(String email, Integer active);

    boolean existsEmployeeByPhoneIgnoreCaseAndActive(String phone, Integer active);

    List<Employee> findAllByActive(Integer active);

    Employee findEmployeeByIdAndActive(Long id, Integer active);

    boolean existsEmployeeByEmailIgnoreCaseAndActiveAndIdNot(String email,Integer active,Long id);

    boolean existsEmployeeByPhoneIgnoreCaseAndActiveAndIdNot(String phone,Integer active,Long id);*/

    Employee findByEmailAndActive(String email,Integer active);
}