public final class Main {
    private final String firstName;
    private final String lastName;
    private final int age;

    Main(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public static void main(String[] args) {
        Main person = new Main("John", "Doe", 30);

        System.out.println("Name: " + person.getFirstName() + " " + person.getLastName());
        System.out.println("Age: " + person.getAge());
    }
}
