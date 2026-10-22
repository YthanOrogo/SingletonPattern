public class OnlineMonitor implements QueueObserver {

    public OnlineMonitor(QueueManager queue) {
        queue.addObserver(this);
    }

    @Override
    public void onQueueUpdated(int nowServing, int lastIssued, String message) {
        System.out.println("  [ONLINE MONITOR] Now serving: " + nowServing
                + " | Last issued: " + lastIssued + "  (" + message + ")");
    }
}
