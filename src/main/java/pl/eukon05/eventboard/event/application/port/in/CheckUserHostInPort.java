package pl.eukon05.eventboard.event.application.port.in;

public interface CheckUserHostInPort {
    boolean checkUserHost(String userId, long eventId);
}
