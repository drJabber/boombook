package rnk.bb.rest.util;

import lombok.Data;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Data
@Named("hotelSearchCriteria")
@SessionScoped
public class HotelSearchCriteria implements Serializable {
    private Integer firstResult;
    private Integer pageSize;
    private Integer resultSize;
    private String sortField;
    private Boolean ascending;

    private String country;
    private String town;
    private String hotelName;
    private String stars;
}
