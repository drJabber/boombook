package rnk.bb.domain.util;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name="document",schema="public")
public class Document {
    @Id
    @SequenceGenerator(name="document_id_seq",sequenceName = "document_id_seq",schema = "public",allocationSize = 1)
    private Integer id;

    @OneToOne
    @JoinColumn(name="type_id")
    private DocumentType documentType;

    @NotNull
    @Size(max = 10)
    private String serial;
    @NotNull
    @Size(max = 40)
    private String number;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date issueDate;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
}
