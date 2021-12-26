public class CityWeather {
    String city;
    String localDate;
    String weatherText;
    double temperature;

    @Override
    public String toString() {
        return "CityWeather{" +
                "city='" + city + '\'' +
                ", localDate='" + localDate + '\'' +
                ", weatherText='" + weatherText + '\'' +
                ", temperature=" + temperature +
                '}';
    }

    public CityWeather(String city, String localDate, String weatherText, double temperature) {
        this.city = city;
        this.localDate = localDate;
        this.weatherText = weatherText;
        this.temperature = temperature;
    }
}
