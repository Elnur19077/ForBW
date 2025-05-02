package bw.black.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import java.time.LocalDateTime;
@Entity
@Table(name = "ChatMessage")
@Data
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacts_add_seq")
    @SequenceGenerator(name = "contacts_add_seq", sequenceName = "CONTACTS_ADD_SEQ", allocationSize = 1)
    private Long id;
    private String sender;
    private String content;
    private LocalDateTime timestamp;
}
