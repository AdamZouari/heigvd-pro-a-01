
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServiceMeteo extends Service{

    private String id;
    private String password;
    private final String urlService = "api.openweathermap.org/data/2.5/";

    public ServiceMeteo(String id, String password) {

        this.id = id;
        this.password = password;
    }

    public void connect() {

        try {
            URL url = new URL(urlService);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(String username, String password) {
        // Do Something
    }

    public void disconnect() {
        // Do Something
    }


}
