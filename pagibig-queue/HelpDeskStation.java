/**
 * One of the three help desk stations. All stations share the same QueueManager.
 */
public class HelpDeskStation {

    private final int stationId;
    private final QueueManager queue;

    public HelpDeskStation(int stationId, QueueManager queue) {
        this.stationId = stationId;
        this.queue = queue;
    }

    public int getStationId() { return stationId; }

    /** Call the next queue number. Returns -1 if no one is waiting. */
    public int callNext() {
        return queue.callNext(stationId);
    }

    /** Reset the queue number based on an inputted number. */
    public void resetQueue(int newNumber) {
        queue.reset(stationId, newNumber);
    }
}
