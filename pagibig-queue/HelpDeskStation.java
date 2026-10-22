public class HelpDeskStation {

    private final int stationId;
    private final QueueManager queue;

    public HelpDeskStation(int stationId, QueueManager queue) {
        this.stationId = stationId;
        this.queue = queue;
    }

    public int getStationId() { return stationId; }

    public int callNext() {
        return queue.callNext(stationId);
    }

    public void resetQueue(int newNumber) {
        queue.reset(stationId, newNumber);
    }
}
