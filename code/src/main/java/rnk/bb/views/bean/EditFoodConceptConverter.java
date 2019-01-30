package rnk.bb.views.bean;

import rnk.bb.rest.hotel.resource.FoodConceptController;
import rnk.bb.services.HotelService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter("foodConceptConverter")
public class EditFoodConceptConverter implements Converter {

    @Inject
    FoodConceptController foodConcepts;
    @Inject
    HotelService hotelService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        EditFoodConceptBean foodConceptBean=new EditFoodConceptBean();
        if (s.isEmpty()){
            return hotelService.initFoodConceptBean(foodConceptBean,(EditFoodConceptBean) null);
        }else{
            return hotelService.initFoodConceptBean(foodConceptBean,foodConcepts.findById(Long.valueOf(s)));
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((EditFoodConceptBean) o).getDescription();
    }
}
