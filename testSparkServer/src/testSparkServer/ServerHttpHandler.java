package testSparkServer;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerHttpHandler {

	private MySQLHandler database;
	
	public ServerHttpHandler() {
		// TODO Auto-generated constructor stub
		database = new MySQLHandler();
		
		getHandling();
		postHandling();
		putHandling();
		deleteHandling();
	
	}
	
	private void getHandling() {
        
		get("/not", (request, response) -> {
        	
        	database.openConnect();
        	
        	String []arg = database.selectFromUsers("Select * from users");
        	
        	database.closeConnection();
        	
        	/*
        	arg[0]="nWSc5tKX9gw:APA91bEmWUrbKCNUuZ2d5oe6dhVuCWcbsVIBsfwQySa_KRnQNpJ8jKrnCXCe9DafYWkMADgXBeYrkppEfXiy_w7hs9TpirdQ9wp1LdFnCEtY8QQzp02tYvmtqlEv8pvsY352GKCrDTaY";
        	arg[1]="mBKGdyMV48A:APA91bG4gUuGhCEVwmixJ0Wk49wvbxklyY7mSBMAV2J56mnHq1qdybE9sneJLfVQ3_Z9pifd0ld9-YPYpIA2_7kRWEbP9WpKdN9m0dZhutmpaSsuYFCc0O6oz7PEvzyXniqvBLn7omGH";
        	cm6-q0KTYIc:APA91bHVimliCRLHlVMyP88q5ZmkHqljnxvvbUc_rxyurCJUIGwoYGzmItF7bfFaR6Dgz3-U0RBDBx0mTpwBi6FW4oLHXlmCMvG9iY0Tm6YGkjsnBlvSEz1hnKJdzph_-ziMIEDHbc0F
        	*/
        	GcmSender.send(arg,"Valami",true);
        	GcmSender.send(arg,"valami",false);
        	
            return "elküldve";
        });
		
	}
	
	private void postHandling() {
        
        post("/registrate", (request, response) -> {
            String id = request.queryParams("id");
            String type = request.queryParams("type");

            database.openConnect();
            database.executeUpdate("Delete from users where token ='"+id+"'");
            database.executeUpdate("Insert into users (token,telehponetype) values ('"+id+"', '"+type+"')");
            database.closeConnection();
            
            response.status(201); // 201 Created
            
            return id+"\n";
        });
		
	}
	
	private void putHandling() {
		
	}
	
	private void deleteHandling() {
		
		delete("/deleteAllUser",(request, response) -> {

            database.openConnect();
            database.executeUpdate("Delete from users");
            database.closeConnection();
            
            response.status(201); // 201 Created
            
            return "Finished\n";
        });
		
	}

}
