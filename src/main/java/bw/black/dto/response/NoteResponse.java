package bw.black.dto.response;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class NoteResponse {
    private Long id;
    private String title;
    private String content;
}
