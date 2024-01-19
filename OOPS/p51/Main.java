import java.util.HashMap;
import java.util.Map;

class DistributedCache {
    private final Map<String, String> cache = new HashMap<>();

    
    private final String[] nodes = {"Node1", "Node2", "Node3"};

    public String get(String key) {
        int nodeIndex = hash(key) % nodes.length;
        String node = nodes[nodeIndex];
        System.out.println("Getting from Node: " + node);
        return cache.get(key);
    }

    public void put(String key, String value) {
        int nodeIndex = hash(key) % nodes.length;
        String node = nodes[nodeIndex];
        System.out.println("Putting on Node: " + node);
        cache.put(key, value);
        replicateToOtherNodes(node, key, value);
    }

    private void replicateToOtherNodes(String sourceNode, String key, String value) {
        for (String node : nodes) {
            if (!node.equals(sourceNode)) {
                
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Replicating to Node: " + node);
                cache.put(key, value);
            }
        }
    }

    private int hash(String key) {
        
        return key.hashCode();
    }
}

public class Main {
    public static void main(String[] args) {
        DistributedCache distributedCache = new DistributedCache();

        
        new Thread(() -> {
            distributedCache.put("key1", "value1");
            System.out.println("Retrieved: " + distributedCache.get("key1"));
        }).start();

        new Thread(() -> {
            distributedCache.put("key2", "value2");
            System.out.println("Retrieved: " + distributedCache.get("key2"));
        }).start();
    }
}
