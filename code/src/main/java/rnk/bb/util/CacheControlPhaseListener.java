package rnk.bb.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CacheControlPhaseListener implements PhaseListener {
    private static Logger log=Logger.getLogger(CacheControlPhaseListener.class.getName());
    public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }

    public void afterPhase(PhaseEvent event)
    {
    }

    public void beforePhase(PhaseEvent event)
    {
        log.log(Level.INFO,"render response phase");

        FacesContext facesContext = event.getFacesContext();
        HttpServletResponse response = (HttpServletResponse) facesContext
                .getExternalContext().getResponse();
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        // Stronger according to blog comment below that references HTTP spec
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");
        // some date in the past
        response.addHeader("Expires", "Sat, 1 Jan 2000 00:00:00 GMT");
    }}
