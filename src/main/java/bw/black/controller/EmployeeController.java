package bw.black.controller;

import bw.black.dto.request.LoginRequest;
import bw.black.dto.request.ReqEmployee;
import bw.black.dto.request.ResetPasswordRequest;
import bw.black.dto.response.GetEmployeeInfoResponse;
import bw.black.entity.Employee;
import bw.black.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.servlet.http.Cookie;


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
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = employeeService.login(request);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true); // İstifadəçi tərəfindən görünməsin
        cookie.setSecure(true);   // HTTPS üçün (dev-də test üçün false da ola bilər)
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 gün

        response.addCookie(cookie);
        return ResponseEntity.ok("Login successful");
    }
    @GetMapping("/me")
    public GetEmployeeInfoResponse getEmployeeInfo() {
        return employeeService.getLoggedInEmployeeInfo();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Silmək üçün

        response.addCookie(cookie);
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/password/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> getEmployeePassword(@PathVariable String email) {
        return ResponseEntity.ok(employeeService.getEncodedPasswordByEmail(email));
    }
    @PostMapping("/reset-password")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        employeeService.resetPassword(request);
        return ResponseEntity.ok("Password successfully reset");
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<GetEmployeeInfoResponse>> getAllActiveEmployees() {
        return ResponseEntity.ok(employeeService.getAllActiveEmployees());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<GetEmployeeInfoResponse>> searchEmployees(@RequestParam String keyword) {
        return ResponseEntity.ok(employeeService.searchEmployeesByKeyword(keyword));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted (set active = 0)");
    }

}
