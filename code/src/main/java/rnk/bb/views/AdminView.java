package rnk.bb.views;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("adminView")
@SessionScoped
public class AdminView implements Serializable {

    public void update(){

    }

    public String getDummyTitle(){
        return "интерфейс админа";
    }
}
