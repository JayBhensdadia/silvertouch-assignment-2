import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class ObjectSerializer {
    public static Map<String, Object> serialize(Object obj) {
        Map<String, Object> serializedData = new HashMap<>();
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                serializedData.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace(); 
            }
        }

        return serializedData;
    }

    public static <T> T deserialize(Map<String, Object> serializedData, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (serializedData.containsKey(field.getName())) {
                    field.set(obj, serializedData.get(field.getName()));
                }
            }

            return obj;
        } catch (Exception e) {
            e.printStackTrace(); 
            return null;
        }
    }
}

class SampleObject {
    private int intValue;
    private String stringValue;

    public SampleObject(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "SampleObject{" +
                "intValue=" + intValue +
                ", stringValue='" + stringValue + '\'' +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        
        SampleObject originalObject = new SampleObject(42, "Hello, Reflection!");

        
        Map<String, Object> serializedData = ObjectSerializer.serialize(originalObject);
        System.out.println("Serialized Data: " + serializedData);

        
        SampleObject deserializedObject = ObjectSerializer.deserialize(serializedData, SampleObject.class);
        System.out.println("Deserialized Object: " + deserializedObject);
    }
}

