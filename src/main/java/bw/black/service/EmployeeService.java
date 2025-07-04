package bw.black.service;

import bw.black.dto.request.LoginRequest;
import bw.black.dto.request.ReqEmployee;
import bw.black.dto.request.ResetPasswordRequest;
import bw.black.dto.response.GetEmployeeInfoResponse;
import bw.black.entity.Employee;

public interface EmployeeService{
    Employee createEmployee(ReqEmployee reqEmployee);

    String login(LoginRequest request);
    GetEmployeeInfoResponse getLoggedInEmployeeInfo();
    String getEncodedPasswordByEmail(String email);
    void resetPassword(ResetPasswordRequest request);

}
