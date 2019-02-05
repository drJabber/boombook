package rnk.bb.views.bean.order;

import rnk.bb.domain.util.Document;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SessionScoped
public class EditDocumentBean implements Serializable {
    private Long id;

    @NotNull
    private String documentType="ПАСПОРТ";

    @NotNull
    private Long documentTypeId=21l;

    @NotNull
    @Size(max = 10)
    private String serial="";

    @NotNull
    @Size(max = 40)
    private String number="";

    @NotNull
    private Date issueDate=null;

    @NotNull
    private Date expirationDate=null;

    public EditDocumentBean(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        List<String> list=new ArrayList<>();
        if (!documentType.trim().isEmpty()){
            ((ArrayList) list).add(documentType.trim());
        }

        if (!serial.trim().isEmpty()){
            list.add(serial.trim());
        }

        if (!number.trim().isEmpty()){
            list.add(number.trim());
        }

        if (issueDate!=null){
            String ds= (new SimpleDateFormat("dd.MM.yyyy").format(issueDate));
            list.add(ds);
        }

        if (expirationDate!=null){
            String ds= (new SimpleDateFormat("dd.MM.yyyy").format(expirationDate));
            list.add(ds);
        }

        return list.stream().collect(Collectors.joining(","));
    }

}
