package rnk.bb.domain.util;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="document_type",schema = "public")
public class DocumentType {
    @Id
    private Integer id;

    @NotNull
    @Size(max=500)
    private String description;
}
