package bw.black.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class UaeServiceRequest {
    private String rfqNo;
    private String clientName;
    private List<UaeServiceProductRequest> products;
}
