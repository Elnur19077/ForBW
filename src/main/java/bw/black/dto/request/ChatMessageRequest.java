package bw.black.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ChatMessageRequest {
    private String sender;
    private String content;

}
