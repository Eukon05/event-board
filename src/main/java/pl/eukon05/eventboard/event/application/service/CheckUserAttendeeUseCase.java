package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.CheckUserAttendeeOutPort;

@UseCase
@RequiredArgsConstructor
class CheckUserAttendeeUseCase {
    private final CheckUserAttendeeOutPort checkUserAttendeeOutPort;

    boolean checkUserAttendee(String userId, long eventId) {
        return checkUserAttendeeOutPort.checkUserAttendee(userId, eventId);
    }
}
