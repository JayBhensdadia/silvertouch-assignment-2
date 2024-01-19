import java.util.ArrayList;
import java.util.List;

class Fact {
    String name;

    public Fact(String name) {
        this.name = name;
    }
}

class AlphaNode {
    List<Fact> matchingFacts = new ArrayList<>();

    public void addMatchingFact(Fact fact) {
        matchingFacts.add(fact);
    }
}

class BetaNode {
    AlphaNode leftInput;
    AlphaNode rightInput;

    public void addMatchingFacts(Fact factFromLeft, Fact factFromRight) {

    }
}

class ReteNetwork {
    List<AlphaNode> alphaNodes = new ArrayList<>();
    List<BetaNode> betaNodes = new ArrayList<>();

    public AlphaNode createAlphaNode(String condition) {
        AlphaNode alphaNode = new AlphaNode();
        alphaNodes.add(alphaNode);

        return alphaNode;
    }

    public BetaNode createBetaNode(AlphaNode leftInput, AlphaNode rightInput) {
        BetaNode betaNode = new BetaNode();
        betaNode.leftInput = leftInput;
        betaNode.rightInput = rightInput;
        betaNodes.add(betaNode);

        return betaNode;
    }
}

class Main {

    public static void main(String[] args) {

        ReteNetwork reteNetwork = new ReteNetwork();

        AlphaNode alphaNode1 = reteNetwork.createAlphaNode("Condition1");
        AlphaNode alphaNode2 = reteNetwork.createAlphaNode("Condition2");

        BetaNode betaNode = reteNetwork.createBetaNode(alphaNode1, alphaNode2);

        Fact fact1 = new Fact("Fact1");
        Fact fact2 = new Fact("Fact2");

        alphaNode1.addMatchingFact(fact1);
        alphaNode2.addMatchingFact(fact2);

        betaNode.addMatchingFacts(fact1, fact2);

    }

    @Override
    public String toString() {
        return "ReteIISystem []";
    }
}
