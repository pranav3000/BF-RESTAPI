package proj.gorest.restapi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class APIMockTests {
    
    // initializations
    int page = 3;
    HttpService service = new HttpService();
    
    
    @SuppressWarnings("deprecation")
    @Test
    public void TestAPIConnection() throws ClientProtocolException, IOException, Exception {

        HttpResponse response = service.getUsersPage(page);

        // Assert that Connected to the API and Performed Get request successfully
        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void RetrievedPage3() throws ClientProtocolException, IOException, Exception {

        HttpResponse response = service.getUsersPage(page);
        String responseBody = EntityUtils.toString(response.getEntity()); // Fetches body from response

        //Parse JSON response and save to List<User>
        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<User>>(){}.getType();

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) gson.fromJson( responseBody , collectionType);

        User firstUser = users.get(0);

        // Assert that first user's name is not empty
        Assert.assertNotEquals("", firstUser.getName());

    }

    @Test
    public void TotalNumberOfUsersInPage3() throws ClientProtocolException, IOException, Exception {

        HttpResponse response = service.getUsersPage(page);
        String responseBody = EntityUtils.toString(response.getEntity()); // Fetches body from response

        //Parse JSON response and save to List<User>
        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<User>>(){}.getType();

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) gson.fromJson( responseBody , collectionType);

        // Assert that there are 10 users on page three
        Assert.assertEquals(users.size(), 10);

    }

    @Test
    public void ValidateLastUserOnPage3() throws ClientProtocolException, IOException, Exception {

        HttpResponse response = service.getUsersPage(page);
        String responseBody = EntityUtils.toString(response.getEntity()); // Fetches body from response

        //Parse JSON response and save to List<User>
        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<User>>(){}.getType();

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) gson.fromJson( responseBody , collectionType);
        
        Collections.sort(users);
        User lastUser = users.get(users.size() - 1);

        // After sorting Assert that last user's name is not empty
        Assert.assertNotEquals("", lastUser.getName());

    }

    @SuppressWarnings({ "deprecation" })
    @Test
    public void UpdateUserName() throws ClientProtocolException, IOException, Exception {

        HttpResponse response = service.getUsersPage(page);
        String responseBody = EntityUtils.toString(response.getEntity()); // Fetches body from response

        //Parse JSON response and save to List<User>
        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<User>>(){}.getType();

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) gson.fromJson( responseBody , collectionType);
        
        Collections.sort(users);
        User lastUser = users.get(users.size() - 1);
        
        String newName = "Harry Smith";
        response = service.updateUserName(lastUser.getId(), newName);
        responseBody = EntityUtils.toString(response.getEntity()); // Fetches body from response
        
        // Assert that response contains update user message
        assertThat(responseBody, containsString(newName));

    }

    @Test
    public void DeleteUpdatedUser() throws ClientProtocolException, IOException, Exception {

        HttpResponse response = service.getUsersPage(page);
        String responseBody = EntityUtils.toString(response.getEntity()); // Fetches body from response

        //Parse JSON response and save to List<User>
        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<User>>(){}.getType();

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) gson.fromJson( responseBody , collectionType);
        
        Collections.sort(users);
        User lastUser = users.get(users.size() - 1);
        
        response = service.deleteUser(lastUser.getId()); 
        
          // Assert that response returns 204 after deletion
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
       

    }

    @Test
    public void RetrieveNonExistentUser() throws ClientProtocolException, IOException, Exception {
        
        int nonExistentId = 5555;
        HttpResponse response = service.getUser(nonExistentId);

        // Assert that response code is 404 (not found)
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);

    }
    

}
