package proj.gorest.restapi;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Program {
    private static final Logger LOGGER = Logger.getLogger(Program.class.getName());
    private static String logFilePath = Settings.logFilePath();
    
    public static void main(String[] args) throws Exception {
        FileHandler fh;   
        fh = new FileHandler(logFilePath);   
        LOGGER.addHandler(fh); 
        
        int page = 3;
        
        HttpService service = new HttpService();
        //get page
        //1. Retrieve page 3 of the list of all users.
        LOGGER.log(Level.INFO, "Retrieve page 3 of the list of all users");
        HttpResponse response = service.getUsersPage(page);
        String responseBody = EntityUtils.toString(response.getEntity()); // Fetches body from response

        //Parse JSON response and save to List<User>
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<User>>(){}.getType();

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) gson.fromJson( responseBody , collectionType);
        
        // List all users in log
        int userNo = 1;
        for(User u:users) {
            String logStr = "User no " + userNo + " on page " + page + " is: " + u.toString();
            LOGGER.log(Level.INFO, logStr);
            userNo++;
        }
        
        //--------------------------------------//
        
        //2. Using a logger, log the total number of pages from the previous request.
        // Fetch number of pages from response header
        List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());
        for(Header h:httpHeaders) {
            if(h.getName().equals("x-pagination-pages"))
                LOGGER.log(Level.INFO, "Total number of pages : " + h.getValue());
        }
        
        //--------------------------------------//
        
        //3. Sort the retrieved user list by name.
        LOGGER.log(Level.INFO, "Sorting the retrieved user list by name ");
        Collections.sort(users);
        
        userNo = 1;
        for(User u:users) {
            String logStr = "User no: " + userNo + " Name : " + u.getName();
            LOGGER.log(Level.INFO, logStr);
            userNo++;
        }
        
        //--------------------------------------//
        
        //4. After sorting, log the name of the last user
        //Get last User
        User lastUser = users.get(users.size() - 1);
        //Log last User's name
        LOGGER.log(Level.INFO, "Last user on page " + page + " {0}", lastUser.getName());
        
        //--------------------------------------//
        
        //5. Update that user's name to a new value and use the correct HTTP method to save it. - Used HTTP PATCH METHOD
        //Update last User's name
        String newName = "Harry Smith";
        response = service.updateUserName(lastUser.getId(), newName);
        responseBody = EntityUtils.toString(response.getEntity()); // Fetches body from response
        
        //--------------------------------------//
        
        //6. Delete last User
        // response.getStatusLine() returns 204 No Content after deletion
        LOGGER.log(Level.INFO, "Deleting User", lastUser.getName() + " " + lastUser.getId());
        response = service.deleteUser(lastUser.getId());

        //--------------------------------------//
        
        //7. Attempt to retrieve a nonexistent user with ID 5555. Log the resulting HTTP response code. - should return 404 as user is not found        
        // Retrieving a nonexistent  user
        int nonExistentId = 5555;
        response = service.getUser(nonExistentId);
        LOGGER.log(Level.INFO, "User with nonexistent ID response code " + response.getStatusLine().getStatusCode());
  
    }

}
