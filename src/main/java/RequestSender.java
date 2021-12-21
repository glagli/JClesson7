import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.File;
import java.io.IOException;

public class RequestSender {
    final static private String API_KEY = "vz9rU5dnry9ZLFY3YEOW6kCZ9lRWhdjo";
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
                .readTree(new File("src/main/java/City Search.txt"))
                .at("/0/Key");

        return cityIdNode.asText();

    }

    static public String WeatherResponse(String cityId,String cityName) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegments("forecasts/v1/daily/1day/" + cityId)
                .addQueryParameter("apikey", API_KEY)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseJson = response.body().string();

        JsonNode dateNode = objectMapper
                .readTree(new File("src/main/java/5 Days.txt"))
                .at("/DailyForecasts/0/Date");

        JsonNode temperatureNode = objectMapper
                .readTree(new File("src/main/java/5 Days.txt"))
                .at("/DailyForecasts/0/Temperature/Minimum/Value");

        JsonNode IconPhrase = objectMapper
                .readTree(new File("src/main/java/5 Days.txt"))
                .at("/DailyForecasts/0/Day/IconPhrase");


        String result;
        result = "В городе " + cityName + " " + "на дату" + " " + dateNode.asText().substring(0, 10) + " " + "ожидается" + " " + IconPhrase.asText() +
                " " + ", температура -" + " " + temperatureNode.asText() + " F";
        return result;

    }
}