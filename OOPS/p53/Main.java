

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class SchemaEvolutionSerializer {

    public static byte[] serialize(Object obj) throws IOException, IllegalAccessException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        objectOutputStream.writeObject(clazz.getName());

        for (Field field : fields) {
            field.setAccessible(true);
            objectOutputStream.writeObject(field.get(obj));
        }

        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static Object deserialize(byte[] data)
            throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        String className = (String) objectInputStream.readObject();
        Class<?> clazz = Class.forName(className);
        Object obj = clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = objectInputStream.readObject();
            field.set(obj, value);
        }

        objectInputStream.close();
        return obj;
    }
}

class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private int age;

    public User(String name) {
        this.name = name;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + '}';
    }
}

public class Main {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        User user = new User("John");
        byte[] serializedData = SchemaEvolutionSerializer.serialize(user);

        try {
            User deserializedUser = (User) SchemaEvolutionSerializer.deserialize(serializedData);
            System.out.println("Deserialized User (version 1): " + deserializedUser);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        User userV2 = new User("Alice", 25);
        serializedData = SchemaEvolutionSerializer.serialize(userV2);

        try {
            User deserializedUser = (User) SchemaEvolutionSerializer.deserialize(serializedData);
            System.out.println("Deserialized User (version 2): " + deserializedUser);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
