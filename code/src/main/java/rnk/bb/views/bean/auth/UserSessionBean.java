package rnk.bb.views.bean.auth;

@Named("userSession")
@SessionScoped
class UserSessionBean implements Serializable{
    private boolean loggedIn;
    private String  originalURL;
    
    public void recordOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
}
