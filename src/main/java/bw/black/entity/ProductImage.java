package bw.black.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Table(name = "product_image")
@Setter
@Getter
@DynamicInsert
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_image_seq")
    @SequenceGenerator(name = "product_image_seq",sequenceName = "PRODUCT_IMAGE_SEQ",allocationSize = 1)
    private Long id;
    @Lob
    private byte[] data;
    @Column(nullable = false, length = 200)
    private String fileName;
    private String fileType;
    @ManyToOne
    @JoinColumn
    private Contacts contacts;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;

    public ProductImage(String fileName,String fileType,byte[] data,Contacts contacts){
        this.fileName=fileName;
        this.fileType=fileType;
        this.data=data;
        this.contacts=contacts;
    }
}

