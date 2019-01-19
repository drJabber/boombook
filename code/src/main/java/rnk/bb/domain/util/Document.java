package rnk.bb.domain.util;

import lombok.Data;
import rnk.bb.domain.blank.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name="document",schema="public")
public class Document extends AbstractEntity {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="type_id")
    private DocumentType documentType;

    @NotNull
    @Size(max = 10)
    @Column(nullable = false)
    private String serial;

    @NotNull
    @Size(max = 40)
    @Column(nullable = false)
    private String number;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date issueDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date expirationDate;
}
