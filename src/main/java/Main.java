import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        String cityId;
        ArrayList<String> arrayList = new ArrayList();
        try {
            String cityName = "Tula";
            cityId = RequestSender.sendCityIdRequests(cityName);
            arrayList = RequestSender.WeatherResponse(cityId, cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cityName = arrayList.get(0);
        String dateNode = arrayList.get(1);
        String IconPhrase = arrayList.get(2);
        double temperature = Double.parseDouble(arrayList.get(3));

        CityWeather city = new CityWeather(cityName,dateNode, IconPhrase, temperature);
        Db db = new Db();
        db.addData(city);

        System.out.println(db.selectAll());
    }


}