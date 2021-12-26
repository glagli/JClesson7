import java.sql.*;
import java.util.ArrayList;

public class Db {

    private final String PATH_TO_DB = "jdbc:postgresql://192.168.0.131:5432/GbJavaCoreTest";
    private Connection connection;

    public Db() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            this.connection = DriverManager.getConnection(PATH_TO_DB,"postgres","2812");
            Statement stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addData(CityWeather cityWeather){
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "INSERT INTO public.\"WEATHER\"(city,\"localDate\",\"weatherText\",temperature) VALUES(?,?,?,?)"
        )) {
            preparedStatement.setObject(1,cityWeather.city);
            preparedStatement.setObject(2,cityWeather.localDate);
            preparedStatement.setObject(3,cityWeather.weatherText);
            preparedStatement.setObject(4,cityWeather.temperature);
            preparedStatement.execute();
            System.out.println("Запись в базу добавлена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CityWeather> selectAll(){

        ArrayList<CityWeather> result = new ArrayList<>();
        try (Statement statement = this.connection.createStatement())
        {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM public.\"WEATHER\"");

                while (resultSet.next()){
                    CityWeather cityWeather = new CityWeather(
                            resultSet.getString("city"),
                            resultSet.getString("localDate"),
                            resultSet.getString("weatherText"),
                            resultSet.getDouble("temperature")
                    );
                    result.add(cityWeather);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Все данные из базы:");
        return result;
    }
}
