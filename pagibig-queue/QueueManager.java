import java.util.ArrayList;
import java.util.List;

/**
 * Centralized queue shared by ALL help desk stations (Singleton).
 * Every method is synchronized so the three stations and the ticket
 * kiosk can safely use it at the same time.
 */
public class QueueManager {

    private static QueueManager instance;

    private int lastIssued = 0;   // last queue number given to a visitor
    private int nowServing = 0;   // queue number currently being served
    private final List<QueueObserver> observers = new ArrayList<>();

    private QueueManager() { }

    public static synchronized QueueManager getInstance() {
        if (instance == null) {
            instance = new QueueManager();
        }
        return instance;
    }

    /** A visitor obtains a queue number from the central system. */
    public synchronized int issueNumber() {
        lastIssued++;
        notifyObservers("Issued queue number " + lastIssued);
        return lastIssued;
    }

    /** A help desk station calls the next number in line. Returns -1 if nobody is waiting. */
    public synchronized int callNext(int stationId) {
        if (nowServing >= lastIssued) {
            return -1;
        }
        nowServing++;
        notifyObservers("Station " + stationId + " is now serving number " + nowServing);
        return nowServing;
    }

    /**
     * Reset the queue so the NEXT issued number is newNumber
     * (e.g., reorganization or technical issues).
     */
    public synchronized void reset(int stationId, int newNumber) {
        if (newNumber < 1) {
            throw new IllegalArgumentException("Queue number must be 1 or higher.");
        }
        lastIssued = newNumber - 1;
        nowServing = newNumber - 1;
        notifyObservers("Station " + stationId + " reset the queue. Next number: " + newNumber);
    }

    public synchronized int getNowServing() { return nowServing; }
    public synchronized int getLastIssued() { return lastIssued; }
    public synchronized int getWaitingCount() { return lastIssued - nowServing; }

    public synchronized void addObserver(QueueObserver o) { observers.add(o); }
    public synchronized void removeObserver(QueueObserver o) { observers.remove(o); }

    private void notifyObservers(String message) {
        for (QueueObserver o : observers) {
            o.onQueueUpdated(nowServing, lastIssued, message);
        }
    }
}
