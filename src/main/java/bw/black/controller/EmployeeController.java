package bw.black.controller;

import bw.black.dto.request.LoginRequest;
import bw.black.dto.request.ReqEmployee;
import bw.black.dto.request.ResetPasswordRequest;
import bw.black.dto.response.GetEmployeeInfoResponse;
import bw.black.entity.Employee;
import bw.black.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        // 1. İstifadəçini yoxla, uğurludursa token hazırla
        String token = employeeService.login(request);

        // 2. Token-u HttpOnly cookie kimi yarat
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);           // JavaScript tokenu görə bilməz
        cookie.setSecure(true);             // HTTPS istifadə olunursa true qoy
        cookie.setPath("/");                // cookie bütün path-lərdə keçərlidir

        // Əgər Servlet API 4.0+ istifadə edirsə, SameSite flag-i əlavə et (lazımdırsa)
        // cookie.setSameSite("Lax");

        // 3. Cookie cavaba əlavə et
        response.addCookie(cookie);

        // 4. İstəyə görə müştəriyə cavab ver (məsələn, success mesajı)
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/me")
    public GetEmployeeInfoResponse getEmployeeInfo() {
        return employeeService.getLoggedInEmployeeInfo();
    }

    @PostMapping("/logout")
    public String logout() {

        return "Logout successful";
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
