package rnk.bb.views.bean.util;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.services.HotelService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named("hotelImages") // Or @ManagedBean
@ApplicationScoped
public class HotelImagesView {

    @Inject
    private HotelService hotels;

    private List<String> topImages;

    @PostConstruct
    public void init(){
        topImages =new ArrayList<>();
        for (int i=1;i<12;i++){
            topImages.add(String.format("gallery%02d.jpg", i));
        }
    }

    public List<String> getTopImages(){
        return topImages;
    }

    public StreamedContent getStreamedContent(byte[] image){
        FacesContext context = FacesContext.getCurrentInstance();

        if (image!=null){
            return new DefaultStreamedContent(new ByteArrayInputStream(image));
        }else{
            return null;
        }
    }

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String hotelId = context.getExternalContext().getRequestParameterMap().get("hotelId");
            Hotel hotel = hotels.findById(Long.valueOf(hotelId));
            return getStreamedContent(hotel.getPicture());
        }
    }

}