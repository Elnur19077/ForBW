package bw.black.repository;

import bw.black.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /*boolean existsEmployeeByEmailIgnoreCaseAndActive(String email, Integer active);

    boolean existsEmployeeByPhoneIgnoreCaseAndActive(String phone, Integer active);

    List<Employee> findAllByActive(Integer active);

    Employee findEmployeeByIdAndActive(Long id, Integer active);

    boolean existsEmployeeByEmailIgnoreCaseAndActiveAndIdNot(String email,Integer active,Long id);

    boolean existsEmployeeByPhoneIgnoreCaseAndActiveAndIdNot(String phone,Integer active,Long id);*/

    Employee findByEmailAndActive(String email,Integer active);
}