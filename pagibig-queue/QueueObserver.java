/**
 * Implemented by anything that wants to be notified when the queue changes
 * (e.g., the online monitoring system).
 */
public interface QueueObserver {
    void onQueueUpdated(int nowServing, int lastIssued, String message);
}
