package bw.black.repository;

import bw.black.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmailIgnoreCaseAndActiveAndIdNot(String email, Integer active, Long id);

    boolean existsCustomerByPhoneIgnoreCaseAndActiveAndIdNot(String phone, Integer active, Long id);

    boolean existsCustomerByEmailIgnoreCaseAndActive(String email, Integer active);

    boolean existsCustomerByPhoneIgnoreCaseAndActive(String phone, Integer active);

    boolean existsCustomerByIdAndActive(Long id, Integer active);

    Customer findCustomerByIdAndActive(Long id, Integer active);

    Customer findByEmailAndActive(String email, Integer active);

}