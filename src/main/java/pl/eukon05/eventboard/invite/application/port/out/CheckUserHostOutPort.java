package pl.eukon05.eventboard.invite.application.port.out;

public interface CheckUserHostOutPort {
    boolean checkUserHost(String userId, long eventId);
}
