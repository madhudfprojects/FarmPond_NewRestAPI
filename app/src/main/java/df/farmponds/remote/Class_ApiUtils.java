package df.farmponds.remote;

public class Class_ApiUtils {
    //public static final String BASE_URL = "https://agri.dfindia.org/api/";

    //public static final String BASE_URL = "http://13.250.59.29/api/";

    //http://dfbci.dfindia.org:8089/
    public static final String BASE_URL = "http://dfbci.dfindia.org:8089/api/";

    public static Interface_userservice getUserService() {
        return Class_RetrofitClient.getClient(BASE_URL).create(Interface_userservice.class);
    }


}
