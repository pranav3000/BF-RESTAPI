package proj.gorest.restapi;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpService {
    private String url;
    private HttpClient client;
    
    HttpService () {
        url = Settings.getUrl();
        client = HttpClientBuilder.create().build();
    }
    
    
    public HttpResponse getUser(int userID) throws URISyntaxException, IOException {
        String pageUrl = url + "//" + userID;
        HttpGet httpGet = new HttpGet(pageUrl);
        return client.execute(httpGet);
    }
    
    public HttpResponse getUsersPage(int page) throws URISyntaxException, IOException {
        String pageUrl = url + "?page=" + page;
        HttpGet httpGet = new HttpGet(pageUrl);
        return client.execute(httpGet);
    }
    
    public HttpResponse updateUserName(int userID, String newUserName) throws URISyntaxException, IOException {
        String pageUrl = url + "//" + userID;
        HttpPatch httpPatch = new HttpPatch(pageUrl);
        StringEntity input = new StringEntity("{\"name\":\""+newUserName+"\"}");

        httpPatch.setEntity(input);

        httpPatch.addHeader("Authorization", "Bearer " + Settings.getToken());
        httpPatch.addHeader("Content-Type", "application/json");
        httpPatch.addHeader("Accept", "application/json");
        httpPatch.addHeader("Cache-Control", "no-cache");
        
        return client.execute(httpPatch);
    }
    
    public HttpResponse deleteUser(int userID) throws URISyntaxException, IOException {
        String pageUrl = url + "//" + userID;
        HttpDelete httpDelete = new HttpDelete(pageUrl);
        httpDelete.addHeader("Authorization", "Bearer " + Settings.getToken());
        return client.execute(httpDelete);
    }
    
}
