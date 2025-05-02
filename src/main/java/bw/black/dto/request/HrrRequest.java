package bw.black.dto.request;

import lombok.Data;

@Data
public class HrrRequest {
    private String startDate; // Format: "YYYY-MM-DD"
    private String endDate;
}
