package bw.black.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class NoteRequest {

    private  String title;
    private  String content;
}
