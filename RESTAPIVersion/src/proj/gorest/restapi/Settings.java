package proj.gorest.restapi;

public class Settings {
    private static String url = "https://gorest.co.in/public/v2/users";
    private static String logFilePath = "/Users/Pveldurthy/desktop/RESTAPI.log"; //ADD LOG FILE PATH HERE
    private static String token = "dba2f082831405a0340903e79e598981df4cf13800f8dc4ab7ce5be44958cd3d"; //ADD TOKEN HERE
    
    public static String getUrl() {
        return url;
    }
    
    public static String logFilePath() {
        return logFilePath;
    }
    
    public static String getToken() {
        return token;
    }

}
