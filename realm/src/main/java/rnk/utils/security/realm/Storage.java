package rnk.utils.security.realm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Storage {

    private Connection conn;
    private String dataSource;
    private final static Logger LOGGER = Logger.getLogger(Storage.class.getName());

    private final static String PASSWD_AND_SALT_FOR_USER = "SELECT password FROM public.auth u WHERE login = ?;";
    private final static String VERIFY_USER = "SELECT login FROM public.auth u WHERE login = ? AND password = ?;";
    private final static String EXTRACT_GROUPS = "SELECT roles_role FROM public.role_auth r WHERE accounts_login = ?;";

    public Storage(String dataSource) {
//            this does not work directly in init phase, so use lazy connection init
        this.dataSource=dataSource;
    }

    private Connection getConnection(){
        if (conn==null){
            Context ctx = null;
            try {
                ctx = new InitialContext();
                DataSource ds = (javax.sql.DataSource) ctx.lookup(dataSource);
                conn = ds.getConnection();
            } catch (NamingException | SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant create connection", e);
            } finally {
                if (ctx != null) {
                    try {
                        ctx.close();
                    } catch (NamingException e) {
                        LOGGER.log(Level.SEVERE, "Cant close context", e);
                    }
                }
            }
        }

        return conn;
    }
    public Storage(String user, String passwd) {
        try {
            Class.forName("org.postgresql.Driver").newInstance();

            conn = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/dbotus" + user + "&password=" + passwd + "");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting connection", ex);
        }
    }

    public String getSaltForLogin(String login) {
        String passwd_and_salt = null;
        String salt=null;
        try {
            PreparedStatement s = getConnection().prepareStatement(PASSWD_AND_SALT_FOR_USER);
            s.setString(1, login);
            ResultSet rs = s.executeQuery();

            if (rs.next()) {
                passwd_and_salt = rs.getString(1);
                salt=passwd_and_salt.split("==")[0]+"==";
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Cant get salt for user", ex);
        }
        return salt;
    }

    public boolean validateUser(String login, String password) {

        try {
//            LOGGER.log(Level.INFO, String.format("rnk realm - validate user %s, password %s", login, password));
            PreparedStatement s = getConnection().prepareStatement(VERIFY_USER,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            s.setString(1, login);
            s.setString(2, password);
            ResultSet rs = s.executeQuery();
            if (rs.first()) {
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "User validation failed", ex);
        }
        return false;
    }

    public List<String> retrieveGroups(String login)    {
        String salt = null;
        try {
            PreparedStatement s = getConnection().prepareStatement(EXTRACT_GROUPS);
            List<String> l=new ArrayList<>();
            s.setString(1, login);
            ResultSet rs = s.executeQuery();
//            LOGGER.log(Level.INFO,"groups:");
            while (rs.next()){
                String ss=rs.getString("roles_role");
//                LOGGER.log(Level.INFO,"\tgroup: "+ss);
                l.add(ss);
            }
            return l;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "User not found", ex);
            return null;
        }
    }
}
