import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String cityId;
        cityId = RequestSender.sendCityIdRequests("Pariz");
        System.out.println(RequestSender.WeatherResponse(cityId, "Pariz"));
    }
}