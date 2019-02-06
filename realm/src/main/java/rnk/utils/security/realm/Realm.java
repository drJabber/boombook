package rnk.utils.security.realm;

import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import rnk.bb.utils.security.HashUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Realm extends AppservRealm {
    private final static Logger LOGGER = Logger.getLogger(AppservRealm.class.getName());

    private String jaasCtxName;
    private String dsName;
    private Storage storage;

    @Override
    protected void init(Properties props) throws BadRealmException, NoSuchRealmException {
        jaasCtxName = props.getProperty("jaas-context", "RnkRealm");
        dsName = props.getProperty("dataSource", "jdbc/rnk-jpa");
        storage = new Storage(dsName);
    }

    @Override
    public String getJAASContext() {
        return jaasCtxName;
    }

    @Override
    public String getAuthType() {
        return "RnkJdbcRealm";
    }

    public Boolean authenticate(String login, String password) {
        Boolean result=false;

        LOGGER.log(Level.INFO,"password:"+password);
        // salt it
        String salt = storage.getSaltForLogin(login);
        if (salt != null) {
            HashUtils utils = new HashUtils();


            // get the byte[] from the salt
            byte[] saltBytes = utils.fromBase64(salt);

            // hash password and salt
            byte[] passwordBytes = utils.hash(password, saltBytes);

            // Base64 encode to String
            String encoded_passwd = utils.toBase64(passwordBytes);

            return storage.validateUser(login,encoded_passwd);
        }
        return result;
    }

    @Override
    public Enumeration getGroupNames(String login) throws InvalidOperationException, NoSuchUserException {
        return Collections.enumeration(storage.retrieveGroups(login));
    }
}
