package rnk.bb.domain.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rnk.bb.domain.blank.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="document_type",schema = "public")
public class DocumentType extends AbstractEntity {
    @NotNull
    @Size(max=500)
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "documentType")
    private List<Document> documents;

}
