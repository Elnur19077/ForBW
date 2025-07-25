package bw.black.service.impl;

import bw.black.dto.request.LoginRequest;
import bw.black.dto.request.ReqEmployee;
import bw.black.dto.request.ResetPasswordRequest;
import bw.black.dto.response.GetEmployeeInfoResponse;
import bw.black.entity.Employee;
import bw.black.enums.EnumAvailableStatus;
import bw.black.exception.ContactsException;
import bw.black.exception.ExceptionConstant;
import bw.black.repository.EmployeeRepository;
import bw.black.security.CustomUserDetailsService;
import bw.black.security.JwtGenerator;
import bw.black.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    public List<GetEmployeeInfoResponse> getAllActiveEmployees() {
        List<Employee> employees = employeeRepository.findAllByActive(1);
        return employees.stream()
                .map(emp -> new GetEmployeeInfoResponse(
                        emp.getId(),
                        emp.getName(),
                        emp.getSurname(),
                        emp.getRole()))
                .toList();
    }

    @Override
    public List<GetEmployeeInfoResponse> searchEmployeesByKeyword(String keyword) {
        List<Employee> employees = employeeRepository.searchByKeyword(keyword);
        return employees.stream()
                .map(emp -> new GetEmployeeInfoResponse(
                        emp.getId(),
                        emp.getName(),
                        emp.getSurname(),
                        emp.getRole()))
                .toList();
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findByIdAndActive(id, 1);
        if (employee == null) {
            throw new ContactsException("Employee not found", ExceptionConstant.EMPLOYEE_NOT_FOUND);
        }
        employee.setActive(0);
        employeeRepository.save(employee);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        Employee employee = employeeRepository.findByEmailAndActive(request.getEmail(), EnumAvailableStatus.ACTIVE.getValue());

        if (employee == null) {
            throw new ContactsException("Employee not found", ExceptionConstant.EMPLOYEE_NOT_FOUND);
        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        employee.setPassword(encodedPassword);
        employeeRepository.save(employee);
    }


    @Override
    public Employee createEmployee(ReqEmployee reqEmployee) {
        Employee employee = new Employee();
        employee.setName(reqEmployee.getName());
        employee.setSurname(reqEmployee.getSurname());
        employee.setPassword(passwordEncoder.encode(reqEmployee.getPassword()));
        employee.setEmail(reqEmployee.getEmail());
        employee.setPhone(reqEmployee.getPhone());
        employee.setRole(reqEmployee.getRole());
       Employee savedEmployee =  employeeRepository.save(employee);
       return savedEmployee;
    }
    @Override
    public String getEncodedPasswordByEmail(String email) {
        Employee employee = employeeRepository.findByEmailAndActive(email, 1);
        if (employee == null) {
            throw new ContactsException("Employee not found", ExceptionConstant.EMPLOYEE_NOT_FOUND);
        }
        return employee.getPassword();
    }
    @Override
    public String login(LoginRequest request) {
        Employee employee = employeeRepository.findByEmailAndActive(request.getEmail(), EnumAvailableStatus.ACTIVE.getValue());
        if (employee == null) {
            throw new ContactsException( "Email or password is incorrect", ExceptionConstant.INVALID_REQUEST_DATA);
        }
        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            throw new ContactsException( "Email or password is incorrect", ExceptionConstant.INVALID_REQUEST_DATA);        }
        customUserDetailsService.setRole(employee.getRole());
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        return jwtGenerator.generateTokenEmployee(employee);
    }
    @Override
    public GetEmployeeInfoResponse getLoggedInEmployeeInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeRepository.findByEmailAndActive(email, EnumAvailableStatus.ACTIVE.getValue());

        if (employee == null) {
            throw new ContactsException("Employee not found", ExceptionConstant.EMPLOYEE_NOT_FOUND);
        }

        return new GetEmployeeInfoResponse(
                employee.getId(),
                employee.getName(),
                employee.getSurname(),
                employee.getRole()
        );
    }

}
