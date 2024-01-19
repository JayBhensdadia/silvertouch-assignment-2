import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Instance {
    private final Map<String, Double> attributes;

    public Instance(Map<String, Double> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }
}

class CobwebNode {
    private final Map<String, Double> centroid;
    private final List<Instance> instances;
    private final Map<CobwebNode, Integer> children;

    public CobwebNode(Map<String, Double> centroid) {
        this.centroid = centroid;
        this.instances = new ArrayList<>();
        this.children = new HashMap<>();
    }

    public Map<String, Double> getCentroid() {
        return centroid;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public Map<CobwebNode, Integer> getChildren() {
        return children;
    }
}

class Main {
    private CobwebNode root;

    public Main() {
        this.root = new CobwebNode(new HashMap<>());
    }

    public void addInstance(Instance instance) {
        cobweb(root, instance);
    }

    private void cobweb(CobwebNode node, Instance instance) {
        if (node.getChildren().isEmpty()) {
            node.getInstances().add(instance);
        } else {
            CobwebNode nearestChild = findNearestChild(node, instance);
            cobweb(nearestChild, instance);
        }
    }

    private CobwebNode findNearestChild(CobwebNode node, Instance instance) {
        double minDistance = Double.MAX_VALUE;
        CobwebNode nearestChild = null;

        for (Map.Entry<CobwebNode, Integer> entry : node.getChildren().entrySet()) {
            CobwebNode child = entry.getKey();
            double distance = calculateDistance(child.getCentroid(), instance.getAttributes());

            if (distance < minDistance) {
                minDistance = distance;
                nearestChild = child;
            }
        }

        return nearestChild;
    }

    private double calculateDistance(Map<String, Double> centroid, Map<String, Double> attributes) {
        double distance = 0;

        for (Map.Entry<String, Double> entry : centroid.entrySet()) {
            String attribute = entry.getKey();
            double centroidValue = entry.getValue();
            double attributeValue = attributes.getOrDefault(attribute, 0.0);
            distance += Math.pow(centroidValue - attributeValue, 2);
        }

        return Math.sqrt(distance);
    }

    public static void main(String[] args) {
        Main cobwebAlgorithm = new Main();

        Map<String, Double> attributes1 = Map.of("Feature1", 1.2, "Feature2", 2.5);
        Map<String, Double> attributes2 = Map.of("Feature1", 0.8, "Feature2", 2.0);
        Map<String, Double> attributes3 = Map.of("Feature1", 1.5, "Feature2", 2.8);

        Instance instance1 = new Instance(attributes1);
        Instance instance2 = new Instance(attributes2);
        Instance instance3 = new Instance(attributes3);

        cobwebAlgorithm.addInstance(instance1);
        cobwebAlgorithm.addInstance(instance2);
        cobwebAlgorithm.addInstance(instance3);
    }
}
