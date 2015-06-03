package testSparkServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLHandler {

	Connection con = null;
	
    public void executeUpdate(String query) {
    	
        Statement st = null;
    	
    	try {
            st = con.createStatement();
            st.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(main.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
        	
            try {
                if (st != null) {
                    st.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(main.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    	
    }
    
    public String[] selectFromUsers(String query) {
    	
    	ArrayList<String> ids = new ArrayList<String>();
        Statement st = null;
        ResultSet rs = null;
        
    	try {
            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getString(2));
                ids.add(rs.getString(2));
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(main.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(main.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    	
    	String[] array = new String[ids.size()];
    	
    	return ids.toArray(array);
    	
    }
    
    public boolean openConnect() {
    	
    	boolean success = true;
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "testuser";
        String password = "test";
    	
    	try {
    		
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(main.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            success = false;
        }
    	
    	return success;
    	
    }
    
    public void closeConnection() {
    	
        if (con != null) {
            try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
    }
    
    

}
