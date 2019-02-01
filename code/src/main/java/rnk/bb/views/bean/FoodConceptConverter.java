package rnk.bb.views.bean;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import rnk.bb.services.HotelService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;


@Named("foodConceptConverter")
@RequestScoped
public class FoodConceptConverter implements Converter {

    @Override
    public Object getAsObject(final FacesContext fc, final UIComponent uic, final String id) {
        if (id == null) {
            return null;
        }
         return fromSelect(uic, id);
    }

   @Override
    public String getAsString(final FacesContext arg0, final UIComponent arg1, final Object object) {
        if (object.getClass()==EditFoodConceptBean.class){
            return ((EditFoodConceptBean)object).getId().toString();
        }
        return null;
    }    

    private Object fromSelect(final UIComponent currentcomponent, final String id) {
 
        if (currentcomponent.getClass() == UISelectItem.class) {
            final UISelectItem item = (UISelectItem) currentcomponent;
            final Object value = item.getValue();
            if (value.getClass()==EditFoodConceptBean.class){
                if (id.equals(((EditFoodConceptBean)value).getId().toString())) {
                    return value;
                }
            }
        }
 
        if (currentcomponent.getClass() == UISelectItems.class) {
            final UISelectItems items = (UISelectItems) currentcomponent;
            final List<Object> elements = (List<Object>) items.getValue();
            for (final Object element : elements) {
                if (element.getClass()==EditFoodConceptBean.class){
                    if (id.equals(((EditFoodConceptBean)element).getId().toString())) {
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