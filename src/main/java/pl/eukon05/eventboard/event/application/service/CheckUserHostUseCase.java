package pl.eukon05.eventboard.event.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.event.application.port.out.CheckUserHostOutPort;

@UseCase
@RequiredArgsConstructor
class CheckUserHostUseCase {
    private final CheckUserHostOutPort checkUserHostOutPort;

    boolean checkUserHost(String userId, long eventId) {
        return checkUserHostOutPort.checkUserHost(userId, eventId);
    }
}
