import java.util.ArrayList;
import java.util.List;

class Musician {
    private String musicianId;
    private String musicianName;
    private List<Instrument> instruments;

    public Musician(String musicianId, String musicianName) {
        this.musicianId = musicianId;
        this.musicianName = musicianName;
        this.instruments = new ArrayList<>();
    }

    public String getMusicianId() {
        return musicianId;
    }

    public String getMusicianName() {
        return musicianName;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void addInstrument(Instrument instrument) {
        instruments.add(instrument);
        System.out.println("Instrument '" + instrument.getInstrumentName() +
                "' added to musician '" + musicianName + "'.");
    }
}

class Instrument {
    private String instrumentId;
    private String instrumentName;
    private double price;

    public Instrument(String instrumentId, String instrumentName, double price) {
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
        this.price = price;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public double getPrice() {
        return price;
    }
}

class Inventory {
    private List<Instrument> instruments;
    private List<Musician> musicians;
    private double totalSales;

    public Inventory() {
        this.instruments = new ArrayList<>();
        this.musicians = new ArrayList<>();
        this.totalSales = 0.0;
    }

    public void addInstrument(Instrument instrument) {
        instruments.add(instrument);
        System.out.println("Instrument '" + instrument.getInstrumentName() + "' added to inventory.");
    }

    public void addMusician(Musician musician) {
        musicians.add(musician);
        System.out.println("Musician '" + musician.getMusicianName() + "' added.");
    }

    public void sellInstrument(Musician musician, Instrument instrument) {
        if (musicians.contains(musician) && instruments.contains(instrument)) {
            musician.addInstrument(instrument);
            instruments.remove(instrument);
            totalSales += instrument.getPrice();
            System.out.println("Instrument '" + instrument.getInstrumentName() +
                    "' sold to musician '" + musician.getMusicianName() + "'.");
        } else {
            System.out.println("Invalid musician or instrument.");
        }
    }

    public double getTotalSales() {
        return totalSales;
    }
}

public class Main {
    public static void main(String[] args) {
        Instrument guitar = new Instrument("I001", "Acoustic Guitar", 500.0);
        Instrument piano = new Instrument("I002", "Digital Piano", 800.0);

        Musician musician1 = new Musician("M001", "namit");
        Musician musician2 = new Musician("M002", "gamit");

        Inventory inventory = new Inventory();

        inventory.addInstrument(guitar);
        inventory.addInstrument(piano);

        inventory.addMusician(musician1);
        inventory.addMusician(musician2);

        inventory.sellInstrument(musician1, guitar);
        inventory.sellInstrument(musician2, piano);

        System.out.println("Total Sales: " + inventory.getTotalSales());
    }
}
