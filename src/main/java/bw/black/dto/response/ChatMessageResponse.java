package bw.black.dto.response;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessageResponse {
    private Long id;
    private String sender;
    private String content;
    private LocalDateTime timestamp;
}
