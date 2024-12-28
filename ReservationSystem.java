import java.util.*;

class ReservationSystem {

    // Database Simulation
    private static final Map<String, String> users = new HashMap<>(); // Username -> Password
    private static final Map<Integer, Train> trains = new HashMap<>(); // Train Number -> Train Details
    private static final Map<String, Ticket> tickets = new HashMap<>(); // PNR -> Ticket

    private static int pnrCounter = 1000;

    static {
        // Adding dummy users
        users.put("admin", "password123");
        users.put("user1", "userpass");

        // Adding dummy train data
        trains.put(101, new Train(101, "Express Train", 50));
        trains.put(102, new Train(102, "Superfast Train", 100));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Online Reservation System");
        System.out.println("Please Login");

        // Login Module
        if (!login(scanner)) {
            System.out.println("Invalid Login. Exiting System.");
            return;
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Reserve Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    reserveTicket(scanner);
                    break;
                case 2:
                    cancelTicket(scanner);
                    break;
                case 3:
                    System.out.println("Thank you for using the Online Reservation System.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static boolean login(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        return users.containsKey(username) && users.get(username).equals(password);
    }

    private static void reserveTicket(Scanner scanner) {
        System.out.println("\nAvailable Trains:");
        for (Train train : trains.values()) {
            System.out.println(train);
        }

        System.out.print("Enter Train Number: ");
        int trainNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (!trains.containsKey(trainNumber)) {
            System.out.println("Invalid Train Number. Please try again.");
            return;
        }

        Train train = trains.get(trainNumber);

        if (train.getAvailableSeats() <= 0) {
            System.out.println("No seats available on this train.");
            return;
        }

        System.out.print("Enter Your Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Your Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Class Type (e.g., Sleeper, AC): ");
        String classType = scanner.nextLine();

        String pnr = "PNR" + (++pnrCounter);
        Ticket ticket = new Ticket(pnr, name, age, trainNumber, train.getTrainName(), classType);

        tickets.put(pnr, ticket);
        train.bookSeat();

        System.out.println("Ticket Reserved Successfully!\n" + ticket);
    }

    private static void cancelTicket(Scanner scanner) {
        System.out.print("Enter PNR Number: ");
        String pnr = scanner.nextLine();

        if (!tickets.containsKey(pnr)) {
            System.out.println("Invalid PNR Number. No ticket found.");
            return;
        }

        Ticket ticket = tickets.remove(pnr);
        Train train = trains.get(ticket.getTrainNumber());
        train.cancelSeat();

        System.out.println("Ticket Cancelled Successfully!\n" + ticket);
    }
}

class Train {
    private int trainNumber;
    private String trainName;
    private int availableSeats;

    public Train(int trainNumber, String trainName, int availableSeats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.availableSeats = availableSeats;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeat() {
        availableSeats--;
    }

    public void cancelSeat() {
        availableSeats++;
    }

    @Override
    public String toString() {
        return "Train Number: " + trainNumber + ", Train Name: " + trainName + ", Available Seats: " + availableSeats;
    }
}

class Ticket {
    private String pnr;
    private String passengerName;
    private int passengerAge;
    private int trainNumber;
    private String trainName;
    private String classType;

    public Ticket(String pnr, String passengerName, int passengerAge, int trainNumber, String trainName, String classType) {
        this.pnr = pnr;
        this.passengerName = passengerName;
        this.passengerAge = passengerAge;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
    }

    public String getPnr() {
        return pnr;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    @Override
    public String toString() {
        return "PNR: " + pnr + ", Name: " + passengerName + ", Age: " + passengerAge + ", Train: " + trainName + ", Class: " + classType;
    }
}