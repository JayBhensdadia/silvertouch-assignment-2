
interface SmartContract {
    void execute();
}

abstract class AbstractSmartContract implements SmartContract {
    private String contractName;

    public AbstractSmartContract(String contractName) {
        this.contractName = contractName;
    }

    public String getContractName() {
        return contractName;
    }

    @Override
    public void execute() {
        System.out.println("Executing smart contract: " + contractName);
    }
}

class PaymentContract extends AbstractSmartContract {
    private double amount;

    public PaymentContract(String contractName, double amount) {
        super(contractName);
        this.amount = amount;
    }

    @Override
    public void execute() {
        super.execute();
        System.out.println("Processing payment of $" + amount);
    }
}

class TokenContract extends AbstractSmartContract {
    private int tokenAmount;

    public TokenContract(String contractName, int tokenAmount) {
        super(contractName);
        this.tokenAmount = tokenAmount;
    }

    @Override
    public void execute() {
        super.execute();
        System.out.println("Transferring " + tokenAmount + " tokens");
    }
}

public class Main {
    public static void main(String[] args) {

        PaymentContract paymentContract = new PaymentContract("PaymentContract1", 500.0);
        TokenContract tokenContract = new TokenContract("TokenContract1", 100);

        executeSmartContract(paymentContract);
        executeSmartContract(tokenContract);
    }

    private static void executeSmartContract(SmartContract contract) {
        contract.execute();
    }
}
