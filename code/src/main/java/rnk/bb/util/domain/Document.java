package rnk.bb.util.domain;

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
