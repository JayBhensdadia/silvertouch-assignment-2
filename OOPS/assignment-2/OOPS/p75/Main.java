import java.util.ArrayList;
import java.util.List;

class WeatherData {
    private String timestamp;
    private double temperature;
    private double humidity;
    private double pressure;

    public WeatherData(String timestamp, double temperature, double humidity, double pressure) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "timestamp='" + timestamp + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                '}';
    }
}

class Sensor {
    private String sensorId;
    private String location;

    public Sensor(String sensorId, String location) {
        this.sensorId = sensorId;
        this.location = location;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getLocation() {
        return location;
    }

    public WeatherData collectWeatherData() {

        double temperature = Math.random() * 30 + 10;
        double humidity = Math.random() * 50 + 30;
        double pressure = Math.random() * 20 + 980;

        String timestamp = Long.toString(System.currentTimeMillis());

        return new WeatherData(timestamp, temperature, humidity, pressure);
    }
}

class WeatherStation {
    private List<Sensor> sensors;
    private List<WeatherData> weatherDataHistory;

    public WeatherStation() {
        this.sensors = new ArrayList<>();
        this.weatherDataHistory = new ArrayList<>();
    }

    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
    }

    public void collectWeatherData() {
        for (Sensor sensor : sensors) {
            WeatherData weatherData = sensor.collectWeatherData();
            weatherDataHistory.add(weatherData);
            System.out.println("Weather data collected from sensor " + sensor.getSensorId() +
                    " at " + sensor.getLocation() + ": " + weatherData);
        }
    }

    public List<WeatherData> getWeatherDataHistory() {
        return weatherDataHistory;
    }

    public WeatherData getCurrentWeather() {

        if (!weatherDataHistory.isEmpty()) {
            return weatherDataHistory.get(weatherDataHistory.size() - 1);
        } else {
            return null;
        }
    }

    public void analyzeWeatherTrends() {

        System.out.println("Analyzing weather trends...");
    }

    public void provideWeatherForecast() {

        System.out.println("Providing weather forecast...");
    }
}

public class Main {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();

        Sensor sensor1 = new Sensor("S001", "City Center");
        Sensor sensor2 = new Sensor("S002", "Suburb Area");

        weatherStation.addSensor(sensor1);
        weatherStation.addSensor(sensor2);

        for (int i = 0; i < 3; i++) {
            weatherStation.collectWeatherData();
            weatherStation.analyzeWeatherTrends();
            weatherStation.provideWeatherForecast();
            System.out.println();
        }

        System.out.println("Weather Data History:");
        for (WeatherData weatherData : weatherStation.getWeatherDataHistory()) {
            System.out.println(weatherData);
        }
    }
}
