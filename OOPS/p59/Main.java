import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class DependencyInjector {
    private final Map<Class<?>, Object> objectMap = new HashMap<>();

    public <T> void register(Class<T> clazz) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        objectMap.put(clazz, constructor.newInstance());
    }

    public <T> void register(Class<T> clazz, Object instance) {
        objectMap.put(clazz, instance);
    }

    public <T> T resolve(Class<T> clazz) throws Exception {
        T instance = (T) objectMap.get(clazz);

        if (instance == null) {
            instance = createInstance(clazz);
            injectDependencies(instance);
            objectMap.put(clazz, instance);
        }

        return instance;
    }

    private <T> T createInstance(Class<T> clazz) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private <T> void injectDependencies(T instance) throws Exception {
        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);

                Class<?> fieldType = field.getType();
                Object dependencyInstance = resolve(fieldType);

                field.set(instance, dependencyInstance);
            }
        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Inject {
}

class ServiceA {
    public void execute() {
        System.out.println("ServiceA executed");
    }
}

class ServiceB {
    public void perform() {
        System.out.println("ServiceB performed");
    }
}

class Client {
    @Inject
    private ServiceA serviceA;

    @Inject
    private ServiceB serviceB;

    public void executeServices() {
        serviceA.execute();
        serviceB.perform();
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        DependencyInjector injector = new DependencyInjector();

        injector.register(ServiceA.class);
        injector.register(ServiceB.class);

        Client client = injector.resolve(Client.class);

        client.executeServices();
    }
}
