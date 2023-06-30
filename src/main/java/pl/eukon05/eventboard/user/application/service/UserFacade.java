package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.eukon05.eventboard.common.Result;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final BefriendUserUseCase befriendUserUseCase;
    private final DefriendUserUseCase defriendUserUseCase;

    public Result befriend(String selfID, String friendID) {
        return befriendUserUseCase.execute(selfID, friendID);
    }

    public Result defriend(String selfID, String friendID) {
        return defriendUserUseCase.execute(selfID, friendID);
    }
}
