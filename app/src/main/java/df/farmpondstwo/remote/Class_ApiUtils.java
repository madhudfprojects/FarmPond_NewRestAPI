package df.farmpondstwo.remote;

public class Class_ApiUtils {
    public static final String BASE_URL = "https://agri.dfindia.org/api/";

    public static Interface_userservice getUserService() {
        return Class_RetrofitClient.getClient(BASE_URL).create(Interface_userservice.class);
    }


}
