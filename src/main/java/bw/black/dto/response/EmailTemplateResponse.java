package bw.black.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplateResponse {
    private Long id;
    private String recipient;
    private String subject;
    private String message;
    private boolean active;
}