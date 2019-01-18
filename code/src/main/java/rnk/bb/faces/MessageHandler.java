package rnk.bb.faces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
 

public class MessageHandler implements PhaseListener {
	private static final long serialVersionUID = 1L;
 
//	 a name to save messages in the session
	private static final String sessionToken = "MULTI_PAGE_MESSAGES_SUPPORT";
 
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
 
	public void beforePhase(PhaseEvent event) {
 
		if(event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			FacesContext facesContext = event.getFacesContext();
			restoreMessages(facesContext);
		}
	}
 
	public void afterPhase(PhaseEvent event) {
 
		if(event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES ||
				event.getPhaseId() == PhaseId.PROCESS_VALIDATIONS ||
				event.getPhaseId() == PhaseId.UPDATE_MODEL_VALUES || 
				event.getPhaseId() == PhaseId.INVOKE_APPLICATION) {
 
			FacesContext facesContext = event.getFacesContext();
			saveMessages(facesContext);
		}
 
	}
 
	private int saveMessages(FacesContext facesContext) {
		List<FacesMessage> messages = new ArrayList<FacesMessage>();
		for(Iterator<FacesMessage> i = facesContext.getMessages(null); i.hasNext(); ) {
			messages.add(i.next());
			i.remove();
		}
		if(messages.size() == 0) {
			return 0;
		}
		Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
		List<FacesMessage> existingMessages = (List<FacesMessage>) sessionMap.get(sessionToken);
		if(existingMessages != null) {
			existingMessages.addAll(messages);
		}
		else {
			sessionMap.put(sessionToken, messages);
		}
 
		return messages.size();
	}
 
	private int restoreMessages(FacesContext facesContext) {
		Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
		List<FacesMessage> messages = (List<FacesMessage>)sessionMap.remove(sessionToken);
		if(messages == null) {
			return 0;
		}
		int restoredCount = messages.size();
		for(Iterator<FacesMessage> i = messages.iterator(); i.hasNext(); ) {
			facesContext.addMessage(null, i.next());
		}
 
		return restoredCount;
	}
}