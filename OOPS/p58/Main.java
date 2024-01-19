import java.util.ArrayList;
import java.util.List;

class TransactionParticipant {
    private String data;

    public void prepare(String data) {

        System.out.println("Preparing to commit: " + data);
        this.data = data;
    }

    public void commit() {

        System.out.println("Committing: " + data);
    }

    public void rollback() {

        System.out.println("Rolling back: " + data);
    }
}

class TransactionCoordinator {
    private List<TransactionParticipant> participants;

    public TransactionCoordinator(List<TransactionParticipant> participants) {
        this.participants = participants;
    }

    public boolean executeDistributedTransaction(String data) {

        for (TransactionParticipant participant : participants) {
            participant.prepare(data);
        }

        boolean allPrepared = true;
        for (TransactionParticipant participant : participants) {
            try {
                participant.commit();
            } catch (Exception e) {

                allPrepared = false;
                for (TransactionParticipant rollbackParticipant : participants) {
                    rollbackParticipant.rollback();
                }
                break;
            }
        }

        return allPrepared;
    }
}

public class Main {
    public static void main(String[] args) {

        TransactionParticipant participant1 = new TransactionParticipant();
        TransactionParticipant participant2 = new TransactionParticipant();

        List<TransactionParticipant> participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);

        TransactionCoordinator coordinator = new TransactionCoordinator(participants);

        String data = "Data to be committed";
        boolean transactionResult = coordinator.executeDistributedTransaction(data);

        if (transactionResult) {
            System.out.println("Distributed transaction successful");
        } else {
            System.out.println("Distributed transaction failed");
        }
    }
}
