import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class WeatherData {
    private double temperature;
    private double humidity;
    private double windSpeed;

    public WeatherData(double temperature, double humidity, double windSpeed) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}

class WeatherStation {
    private String stationId;
    private List<WeatherData> weatherDataList;

    public WeatherStation(String stationId) {
        this.stationId = stationId;
        this.weatherDataList = new ArrayList<>();
    }

    public String getStationId() {
        return stationId;
    }

    public void collectWeatherData(double temperature, double humidity, double windSpeed) {
        WeatherData weatherData = new WeatherData(temperature, humidity, windSpeed);
        weatherDataList.add(weatherData);
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }
}

class Forecast {
    private List<WeatherStation> weatherStations;

    public Forecast(List<WeatherStation> weatherStations) {
        this.weatherStations = weatherStations;
    }

    public void generateForecast() {
        for (WeatherStation station : weatherStations) {
            List<WeatherData> stationData = station.getWeatherDataList();
            if (!stationData.isEmpty()) {
                WeatherData latestData = stationData.get(stationData.size() - 1);
                double predictedTemperature = generateRandomValue(latestData.getTemperature() - 5, latestData.getTemperature() + 5);
                double predictedHumidity = generateRandomValue(latestData.getHumidity() - 10, latestData.getHumidity() + 10);
                double predictedWindSpeed = generateRandomValue(latestData.getWindSpeed() - 2, latestData.getWindSpeed() + 2);

                System.out.println("Forecast for Weather Station " + station.getStationId() + ":");
                System.out.println(" Temperature: " + predictedTemperature + " °C");
                System.out.println(" Humidity: " + predictedHumidity + " %");
                System.out.println(" Wind Speed: " + predictedWindSpeed + " m/s");
                System.out.println("-------------------------------");
            } else {
                System.out.println("No data available for Weather Station " + station.getStationId());
            }
        }
    }

    private double generateRandomValue(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}

public class Main {
    public static void main(String[] args) {
        WeatherStation station1 = new WeatherStation("WS001");
        WeatherStation station2 = new WeatherStation("WS002");

        station1.collectWeatherData(25.5, 60.0, 8.0);
        station2.collectWeatherData(22.0, 70.5, 6.5);
        station1.collectWeatherData(24.0, 65.5, 7.2);

        List<WeatherStation> weatherStations = List.of(station1, station2);

        Forecast forecast = new Forecast(weatherStations);
        forecast.generateForecast();
    }
}
