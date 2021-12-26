import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RequestSender {
    final static private String API_KEY = "G6YKKnDw4ivWOLmbsdKu2WQy3GUI7PyP";
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();


    static public String sendCityIdRequests(String cityName) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegments("locations/v1/cities/search")
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("q", cityName)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseJson = response.body().string(); // ответы сложил в файлы
        // так как число запросов в сутки ограниченно

        JsonNode cityIdNode = objectMapper
                .readTree(responseJson)
                .at("/0/Key");

        return cityIdNode.asText();

    }

    static public ArrayList WeatherResponse(String cityId,String cityName) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegments("forecasts/v1/daily/5day/" + cityId)
                .addQueryParameter("apikey", API_KEY)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseJson = response.body().string();

        JsonNode dateNode = objectMapper
                .readTree(responseJson)
                .at("/DailyForecasts/0/Date");

        JsonNode temperatureNode = objectMapper
                .readTree(responseJson)
                .at("/DailyForecasts/0/Temperature/Minimum/Value");

        JsonNode IconPhrase = objectMapper
                .readTree(responseJson)
                .at("/DailyForecasts/0/Day/IconPhrase");


        String result;
        ArrayList<String> cityList = new ArrayList<>();

        result = "В городе " + cityName + " " + "на дату" + " " + dateNode.asText().substring(0, 10) + " " + "ожидается" + " " + IconPhrase.asText() +
                " " + ", температура -" + " " + temperatureNode.asText() + " F";

        System.out.println(result);
        cityList.add(cityName);
        cityList.add(dateNode.asText().substring(0, 10));
        cityList.add(IconPhrase.asText());
        cityList.add(temperatureNode.asText());


        return cityList;

    }
}