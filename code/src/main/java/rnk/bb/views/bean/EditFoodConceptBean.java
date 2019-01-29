package rnk.bb.views.bean;

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
public class EditFoodConceptBean implements Serializable {
    @NotNull
    @Size(max=200)
    private String name;

    @NotNull
    @Min(0)
    private Double basePrice=0.0;

    @NotNull
    @Size(max=500)
    private String description;

    public String toString(){
        StringBuilder sb=new StringBuilder();
        List<String> list=new ArrayList<>();
        if (!name.trim().isEmpty()){
            ((ArrayList) list).add(name.trim());
        }

        if (!description.trim().isEmpty()){
            list.add(description.trim());
        }

        return list.stream().collect(Collectors.joining(","));
    }

}
