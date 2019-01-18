package rnk.bb.faces;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

    //@Inject
    private final static Logger logger = Logger.getLogger(DefaultExceptionHandler.class.getName());

    public DefaultExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
    }

    @Override
    public void handle() throws FacesException {
        logger.log(Level.INFO, "invoking custom jsf ExceptionHandlder...");
        Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

        while (events.hasNext()) {
            ExceptionQueuedEvent event = events.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();
            logger.log(Level.INFO, "Exception@" + t.getClass().getName());
            logger.log(Level.INFO, "ExceptionHandlder start.");
            //t.printStackTrace();
            if (t instanceof ViewExpiredException) {
                try {
                    handleViewExpiredException((ViewExpiredException) t);
                } finally {
                    events.remove();
                }
            }else {

                getWrapped().handle();
            }
            logger.log(Level.INFO, "ExceptionHandlder end.");
        }

    }

    private void handleViewExpiredException(ViewExpiredException vee) {
        logger.log(Level.INFO, " handling viewExpiredException...");
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = vee.getViewId();
        logger.log(Level.INFO, "view id @" + viewId);
        NavigationHandler nav
                = context.getApplication().getNavigationHandler();
        nav.handleNavigation(context, null, viewId);
        context.renderResponse();
    }

    private void handleNotFoundException(Exception e) {
        logger.log(Level.INFO, "handling exception:...");
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = "/error.xhtml";
        logger.log(Level.INFO, "view id @" + viewId);
        NavigationHandler nav
                = context.getApplication().getNavigationHandler();
        nav.handleNavigation(context, null, viewId);
        context.getViewRoot().getViewMap(true).put("ex", e);
        context.renderResponse();
    }
}
