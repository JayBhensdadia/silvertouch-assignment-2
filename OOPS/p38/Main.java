import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static String serialize(Object obj) {
        StringBuilder result = new StringBuilder();
        serializeObject(obj, result);
        return result.toString();
    }

    private static void serializeObject(Object obj, StringBuilder result) {
        Class<?> clazz = obj.getClass();
        result.append(clazz.getName()).append("{");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            result.append(field.getName()).append(":");
            try {
                Object fieldValue = field.get(obj);
                serializeFieldValue(fieldValue, result);
            } catch (IllegalAccessException e) {
                e.printStackTrace(); 
            }
            result.append(",");
        }

        result.deleteCharAt(result.length() - 1); 
        result.append("}");
    }

    private static void serializeFieldValue(Object fieldValue, StringBuilder result) {
        if (fieldValue == null) {
            result.append("null");
        } else if (fieldValue.getClass().isArray()) {
            serializeArray(fieldValue, result);
        } else {
            result.append(fieldValue.toString());
        }
    }

    private static void serializeArray(Object array, StringBuilder result) {
        result.append("[");
        int length = java.lang.reflect.Array.getLength(array);
        for (int i = 0; i < length; i++) {
            serializeFieldValue(java.lang.reflect.Array.get(array, i), result);
            result.append(",");
        }
        if (length > 0) {
            result.deleteCharAt(result.length() - 1); 
        }
        result.append("]");
    }

    public static <T> T deserialize(String data, Class<T> clazz) {
        Map<String, Object> map = parseData(data);
        return deserializeObject(map, clazz);
    }

    private static Map<String, Object> parseData(String data) {
        
        Map<String, Object> map = new HashMap<>();
        String[] tokens = data.split(",");
        for (String token : tokens) {
            String[] parts = token.split(":");
            if (parts.length == 2) {
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
        }
        return map;
    }

    private static <T> T deserializeObject(Map<String, Object> map, Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (map.containsKey(fieldName)) {
                    Object fieldValue = map.get(fieldName);
                    field.set(obj, fieldValue);
                }
            }
            return obj;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace(); 
            return null;
        }
    }

    public static void main(String[] args) {
        
        SampleObject originalObject = new SampleObject(42, "Hello, Serialization!");
        String serializedData = Main.serialize(originalObject);
        System.out.println("Serialized Data: " + serializedData);

        SampleObject deserializedObject = Main.deserialize(serializedData, SampleObject.class);
        System.out.println("Deserialized Object: " + deserializedObject);
    }
}

class SampleObject {
    private int intValue;
    private String stringValue;

    public SampleObject() {
        
    }

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

