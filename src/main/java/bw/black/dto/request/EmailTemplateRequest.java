package bw.black.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class EmailTemplateRequest {
    private String recipient;
    private String subject;
    private String message;
    private boolean active;
}
