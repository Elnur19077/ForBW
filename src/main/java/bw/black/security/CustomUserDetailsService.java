package bw.black.security;

import bw.black.entity.Customer;
import bw.black.entity.Employee;
import bw.black.enums.EnumAvailableStatus;
import bw.black.exception.ContactsException;
import bw.black.exception.ExceptionConstant;
import bw.black.repository.CustomerRepository;
import bw.black.repository.EmployeeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.Collection;
import bw.black.enums.Role;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    @Setter
    @Getter
    private Role role;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(role==Role.ADMIN|| role == Role.SUPER_ADMIN || role ==Role.OPERATOR) {
            Employee employee =employeeRepository.findByEmailAndActive(username, EnumAvailableStatus.ACTIVE.getValue());
            if (employee == null){
                throw new UsernameNotFoundException("Admin Username "+ username+ "not found");
            }
            return createUserDetails(employee.getEmail(),employee.getPassword(),role);

        } else if(role == Role.CUSTOMER) {
            Customer customer = customerRepository.findByEmailAndActive(username,EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null){
                throw new UsernameNotFoundException("Customer Email "+ username+ "not found");
            }
            return createUserDetails(customer.getEmail(),customer.getPassword(),role);
        }
        throw new ContactsException("Username or Email " + username + " not found",ExceptionConstant.USERNAME_OR_PASSWORD_ARE_INCORRECT);
    }

    private UserDetails createUserDetails(String email, String password, Role role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return new User(email, password, authorities);
    }

}