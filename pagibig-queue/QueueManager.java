import java.util.ArrayList;
import java.util.List;

public class QueueManager {

    private static QueueManager instance;

    private int lastIssued = 0;  
    private int nowServing = 0;   
    private final List<QueueObserver> observers = new ArrayList<>();

    private QueueManager() { }

    public static synchronized QueueManager getInstance() {
        if (instance == null) {
            instance = new QueueManager();
        }
        return instance;
    }

    public synchronized int issueNumber() {
        lastIssued++;
        notifyObservers("Issued queue number " + lastIssued);
        return lastIssued;
    }

    public synchronized int callNext(int stationId) {
        if (nowServing >= lastIssued) {
            return -1;
        }
        nowServing++;
        notifyObservers("Station " + stationId + " is now serving number " + nowServing);
        return nowServing;
    }

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
