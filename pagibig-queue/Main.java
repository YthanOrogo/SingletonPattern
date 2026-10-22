import java.util.Scanner;

/** Console simulation of the Pag-ibig centralized queuing system. */
public class Main {

    public static void main(String[] args) {
        QueueManager queue = QueueManager.getInstance();
        new OnlineMonitor(queue);

        HelpDeskStation[] stations = {
            new HelpDeskStation(1, queue),
            new HelpDeskStation(2, queue),
            new HelpDeskStation(3, queue)
        };

        Scanner in = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== PAG-IBIG CENTRALIZED QUEUING SYSTEM =====");
            System.out.println("1. Get a queue number (visitor)");
            System.out.println("2. Call next number (help desk station)");
            System.out.println("3. Reset queue number (help desk station)");
            System.out.println("4. View queue status");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            switch (readInt(in)) {
                case 1:
                    System.out.println("Your queue number is: " + queue.issueNumber());
                    break;
                case 2: {
                    int id = askStation(in);
                    if (id == -1) break;
                    int served = stations[id - 1].callNext();
                    if (served == -1) System.out.println("No one is waiting in the queue.");
                    break;
                }
                case 3: {
                    int id = askStation(in);
                    if (id == -1) break;
                    System.out.print("Enter the new queue number: ");
                    int n = readInt(in);
                    try {
                        stations[id - 1].resetQueue(n);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 4:
                    System.out.println("Now serving : " + queue.getNowServing());
                    System.out.println("Last issued : " + queue.getLastIssued());
                    System.out.println("Waiting     : " + queue.getWaitingCount());
                    break;
                case 5:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        in.close();
    }

    private static int askStation(Scanner in) {
        System.out.print("Which station (1-3)? ");
        int id = readInt(in);
        if (id < 1 || id > 3) {
            System.out.println("Invalid station.");
            return -1;
        }
        return id;
    }

    private static int readInt(Scanner in) {
        while (!in.hasNextInt()) {
            if (!in.hasNext()) return 5;   // end of input -> exit
            in.next();
            System.out.print("Enter a valid number: ");
        }
        return in.nextInt();
    }
}
