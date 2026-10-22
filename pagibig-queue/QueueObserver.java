public interface QueueObserver {
    void onQueueUpdated(int nowServing, int lastIssued, String message);
}