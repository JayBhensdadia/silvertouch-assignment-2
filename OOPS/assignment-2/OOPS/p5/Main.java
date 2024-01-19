
interface Command {
    void execute();
}

class ElectronicDevice {
    public void turnOn() {
        System.out.println("Electronic device is ON");
    }

    public void turnOff() {
        System.out.println("Electronic device is OFF");
    }
}

class TurnOnCommand implements Command {
    private ElectronicDevice device;

    public TurnOnCommand(ElectronicDevice device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.turnOn();
    }
}

class TurnOffCommand implements Command {
    private ElectronicDevice device;

    public TurnOffCommand(ElectronicDevice device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.turnOff();
    }
}

class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}

public class Main {
    public static void main(String[] args) {

        ElectronicDevice electronicDevice = new ElectronicDevice();

        Command turnOnCommand = new TurnOnCommand(electronicDevice);
        Command turnOffCommand = new TurnOffCommand(electronicDevice);

        RemoteControl remoteControl = new RemoteControl();

        remoteControl.setCommand(turnOnCommand);
        remoteControl.pressButton();

        remoteControl.setCommand(turnOffCommand);
        remoteControl.pressButton();
    }
}
