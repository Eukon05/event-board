package pl.eukon05.eventboard.user.application.service;

import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.common.ResultWrapper;
import pl.eukon05.eventboard.common.UseCase;
import pl.eukon05.eventboard.user.application.port.out.GetUserPort;
import pl.eukon05.eventboard.user.domain.User;

import java.util.Optional;
import java.util.Set;

@UseCase
@RequiredArgsConstructor
class GetFriendsUseCase {
    private final GetUserPort getUserPort;

    ResultWrapper<?> getFriends(String userID) {
        Optional<User> userOptional = getUserPort.getUserById(userID);

        if (userOptional.isEmpty()) return ResultWrapper.wrap(Result.USER_NOT_FOUND);

        Set<String> friendIDs = userOptional.get().getFriendIDs();
        return ResultWrapper.builder().result(Result.SUCCESS).data(friendIDs).build();
    }

    ResultWrapper<?> getFriendRequests(String userID) {
        Optional<User> userOptional = getUserPort.getUserById(userID);

        if (userOptional.isEmpty()) return ResultWrapper.wrap(Result.USER_NOT_FOUND);

        Set<String> friendRequestIDs = userOptional.get().getFriendRequestIDs();
        return ResultWrapper.builder().result(Result.SUCCESS).data(friendRequestIDs).build();
    }

}
