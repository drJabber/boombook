package rnk.bb.views.bean;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import rnk.bb.services.HotelService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;


@Named("foodConceptConverter")
@RequestScoped
public class FoodConceptConverter implements Converter {

    @Inject
    HotelService service;

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                EditFoodConceptBean bean=new EditFoodConceptBean();
                return service.initFoodConceptBean(bean, Long.parseLong(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid food concept."));
            }
        }
        else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((EditFoodConceptBean) object).getId());
        }
        else {
            return null;
        }
    }

}