package bw.black.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "Notes")
@Setter
@Getter
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_seq")
    @SequenceGenerator(name = "note_seq", sequenceName = "Note_SEQ", allocationSize = 1)
    private Long id;
    private String title;
    private String content;
    @ColumnDefault(value = "1")
    private Integer active;
}
