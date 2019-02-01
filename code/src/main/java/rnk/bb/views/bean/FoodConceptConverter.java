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


@Named("facesContextConverter")
@RequestScoped
public class FacesContextConverter implements Converter {

    @Override
    public Object getAsObject(final FacesContext fc, final UIComponent uic, final String id) {
        if (value == null) {
            return null;
        }
         return fromSelect(uic, id);
    }

   @Override
    public String getAsString(final FacesContext arg0, final UIComponent arg1, final Object object) {
        if (object.getClass()==EditFacesContextBean.class){
            return ((EditFacesContextBean)object).getId().toString();
        }
    }    

    private Object fromSelect(final UIComponent currentcomponent, final String id) {
 
        if (currentcomponent.getClass() == UISelectItem.class) {
            final UISelectItem item = (UISelectItem) currentcomponent;
            final Object value = item.getValue();
            if (value.getClass()==EditFacesContextBean.class){
                if (id.equals(((EditFacesContextBean)object).getId().toString()) {
                    return value;
                }
            }
        }
 
        if (currentcomponent.getClass() == UISelectItems.class) {
            final UISelectItems items = (UISelectItems) currentcomponent;
            final List<Object> elements = (List<Object>) items.getValue();
            for (final Object element : elements) {
                if (element.getClass()==EditFacesContextBean.class){
                    if (id.equals((EditFacesContextBean)element).getId().toString()) {
                        return element;
                    }
                }
            }
        }
 
 
        if (!currentcomponent.getChildren().isEmpty()) {
            for (final UIComponent component : currentcomponent.getChildren()) {
                final Object result = fromSelect(component, id);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }    
}