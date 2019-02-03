package rnk.bb.domain.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rnk.bb.domain.blank.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="document_type",schema = "public")
public class DocumentType extends AbstractEntity {
    @NotNull
    @Size(max=500)
    @Column(nullable = false)
    private String description;
}
