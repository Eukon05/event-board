package pl.eukon05.eventboard.event.application.port.out;

public interface CheckUserHostOutPort {
    boolean checkUserHost(String userId, long eventId);
}
