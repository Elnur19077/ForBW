package bw.black.controller;

import bw.black.dto.request.LoginRequest;
import bw.black.dto.request.ReqEmployee;
import bw.black.dto.response.GetEmployeeInfoResponse;
import bw.black.entity.Employee;
import bw.black.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
@Tag(name = "User API", description = "İstifadəçi giriş və məlumat API-si")
public class EmployeeController {

    private final EmployeeService employeeService;
    @PostMapping("/create")
    public Employee createEmployee(@RequestBody ReqEmployee reqEmployee) {
        return employeeService.createEmployee(reqEmployee);
    }

        @PostMapping("/login")
        @Operation(summary = "Login", description = "İstifadəçi adı və şifrə ilə daxil olur")
        public String login(@RequestBody LoginRequest request) {
            return employeeService.login(request);
        }
    @GetMapping("/me")
    public GetEmployeeInfoResponse getEmployeeInfo() {
        return employeeService.getLoggedInEmployeeInfo();
    }

    @PostMapping("/logout")
    public String logout() {

        return "Logout successful";
    }

    }
