import java.util.*;
import java.text.SimpleDateFormat
class Car {
    String id, make, model;
    int year;
    boolean rented;
    Car(String id, String make, String model, int year) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
    }
    void rent() { 
        if (rented) throw new IllegalStateException("Car already rented");
        rented = true; 
    }
    void returnCar() { 
        if (!rented) throw new IllegalStateException("Car not rented");
        rented = false; 
    }
    public String toString() { 
        return year + " " + make + " " + model + " (" + id + ")"; 
    }
}
class Customer {
    String id, name, email;
    Customer(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public String toString() {
        return name + " (" + id + ")";
    }
}
class RentalTransaction {
    Customer customer;
    Car car;
    Date start, end;
    RentalTransaction(Customer customer, Car car, Date start, Date end) {
        this.customer = customer;
        this.car = car;
        this.start = start;
        this.end = end;
    }
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Car: " + car + ", Customer: " + customer +
               ", From: " + sdf.format(start) + " to " + sdf.format(end);
    }
}

class RentalAgency {
    Map<String, Car> cars = new HashMap<>();
    Map<String, Customer> customers = new HashMap<>();
    List<RentalTransaction> transactions = new ArrayList<>();
    void addCar(Car c) { cars.put(c.id, c); }
    void addCustomer(Customer c) { customers.put(c.id, c); }

    List<Car> availableCars() {
        List<Car> avail = new ArrayList<>();
        for (Car c : cars.values()) {
            if (!c.rented) avail.add(c);
        }
        return avail;
    }

    RentalTransaction rent(String carId, String custId, Date start, Date end) {
        Car car = cars.get(carId);
        Customer cust = customers.get(custId);
        if (car == null || cust == null)
            throw new IllegalArgumentException("Invalid car or customer ID");
        car.rent();
        RentalTransaction trans = new RentalTransaction(cust, car, start, end);
        transactions.add(trans);
        return trans;
    }

    void returnCar(String carId) {
        Car car = cars.get(carId);
        if (car == null || !car.rented)
            throw new IllegalArgumentException("Car not rented or invalid ID");
        car.returnCar();
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        RentalAgency agency = new RentalAgency();
        agency.addCar(new Car("CAR001", "Toyota", "Corolla", 2020));
        agency.addCar(new Car("CAR002", "Honda", "Civic", 2019));
        agency.addCustomer(new Customer("CUST001", "Alice", "alice@example.com"));
        agency.addCustomer(new Customer("CUST002", "Bob", "bob@example.com"));

        System.out.println("Available Cars:");
        agency.availableCars().forEach(System.out::println);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse("2025-07-01"), end = sdf.parse("2025-07-05");
        RentalTransaction trans = agency.rent("CAR001", "CUST001", start, end);
        System.out.println("\nRented: " + trans);

        System.out.println("\nAvailable Cars after renting:");
        agency.availableCars().forEach(System.out::println);

        agency.returnCar("CAR001");
        System.out.println("\nAfter return:");
        agency.availableCars().forEach(System.out::println);
    }
}

