import java.util.ArrayList;
import java.util.List;

class Car {
    private String registrationNumber;
    private String model;
    private boolean available;

    public Car(String registrationNumber, String model) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.available = true;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return available;
    }

    public void rent() {
        if (available) {
            available = false;
            System.out.println("Car " + registrationNumber + " rented successfully.");
        } else {
            System.out.println("Car " + registrationNumber + " is not available for rent.");
        }
    }

    public void returnCar() {
        available = true;
        System.out.println("Car " + registrationNumber + " returned successfully.");
    }
}

class RentalAgency {
    private List<Car> cars;

    public RentalAgency() {
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }
}

class Customer {
    private String customerId;
    private String name;
    private List<String> rentalHistory;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.rentalHistory = new ArrayList<>();
    }

    public void rentCar(Car car) {
        if (car.isAvailable()) {
            car.rent();
            rentalHistory.add("Rented Car " + car.getRegistrationNumber());
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        rentalHistory.add("Returned Car " + car.getRegistrationNumber());
    }

    public List<String> getRentalHistory() {
        return rentalHistory;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create cars
        Car car1 = new Car("ABC123", "Sedan");
        Car car2 = new Car("XYZ789", "SUV");
        Car car3 = new Car("DEF456", "Compact");

        // Create a rental agency
        RentalAgency rentalAgency = new RentalAgency();
        rentalAgency.addCar(car1);
        rentalAgency.addCar(car2);
        rentalAgency.addCar(car3);

        Customer customer1 = new Customer("C001", "Alice");

        List<Car> availableCars = rentalAgency.getAvailableCars();
        if (!availableCars.isEmpty()) {
            Car rentedCar = availableCars.get(0);
            customer1.rentCar(rentedCar);
        }

        List<String> rentalHistory = customer1.getRentalHistory();
        if (!rentalHistory.isEmpty()) {
            Car returnedCar = rentalAgency.getAvailableCars().get(0);
            customer1.returnCar(returnedCar);
        }
    }
}
