package rnk.bb.services.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import rnk.bb.domain.util.Country;
import rnk.bb.domain.util.DocumentType;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class DocumentTypeBean {
    @NotNull
    private String description;

    public DocumentTypeBean(DocumentType dt){
        this.description=dt.getDescription();
    }
}
