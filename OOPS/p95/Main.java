class Pet {
    private String name;
    private int health;
    private int happiness;

    public Pet(String name) {
        this.name = name;
        this.health = 100; 
        this.happiness = 10; 
    }

    public void feed(Food food) {
        int nutrition = food.getNutrition();
        health = Math.min(100, health + nutrition);
        System.out.println(name + " is fed with " + food.getName() + ". Health " + health);
    }

    public void play() {
        happiness = Math.min(100, happiness + 10);
        System.out.println(name + " is playing. Happiness "+ happiness);
    }

    public void showStatus() {
        System.out.println(name + ": Health = " + health + ", Happiness = " + happiness);
    }
}

class Food {
    private String name;
    private int nutrition;

    public Food(String name, int nutrition) {
        this.name = name;
        this.nutrition = nutrition;
    }

    public String getName() {
        return name;
    }

    public int getNutrition() {
        return nutrition;
    }
}

public class Main {
    public static void main(String[] args) {
        Pet myPet = new Pet("dogg");

        System.out.println("Welcome to Virtual Pet Simulation!");

        Food healthyFood = new Food("good Food", 20);
        Food treat = new Food("Treat", 10);

        myPet.feed(healthyFood);
        myPet.play();
        myPet.feed(treat);

        myPet.showStatus();
    }
}
