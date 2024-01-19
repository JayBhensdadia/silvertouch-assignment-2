import java.util.ArrayList;
import java.util.List;

class Patient {
    private String patientId;
    private String name;
    private int age;

    public Patient(String patientId, String name, int age) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Doctor {
    private String doctorId;
    private String name;
    private String specialization;

    public Doctor(String doctorId, String name, String specialization) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId='" + doctorId + '\'' +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}

class Appointment {
    private Patient patient;
    private Doctor doctor;
    private String appointmentDate;

    public Appointment(Patient patient, Doctor doctor, String appointmentDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "patient=" + patient +
                ", doctor=" + doctor +
                ", appointmentDate='" + appointmentDate + '\'' +
                '}';
    }
}

class Hospital {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;

    public Hospital() {
        this.doctors = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void scheduleAppointment(Patient patient, Doctor doctor, String appointmentDate) {
        Appointment appointment = new Appointment(patient, doctor, appointmentDate);
        appointments.add(appointment);
        System.out.println("Appointment scheduled: " + appointment);
    }

    public void displayAppointments() {
        System.out.println("All Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();

        Doctor doctor1 = new Doctor("D001", "Dr. Smith", "Cardiology");
        Doctor doctor2 = new Doctor("D002", "Dr. Johnson", "Orthopedics");

        Patient patient1 = new Patient("P001", "John Doe", 30);
        Patient patient2 = new Patient("P002", "Jane Doe", 25);

        hospital.addDoctor(doctor1);
        hospital.addDoctor(doctor2);

        hospital.addPatient(patient1);
        hospital.addPatient(patient2);

        hospital.scheduleAppointment(patient1, doctor1, "2022-02-01");
        hospital.scheduleAppointment(patient2, doctor2, "2022-02-03");

        hospital.displayAppointments();
    }
}

