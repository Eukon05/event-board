package pl.eukon05.eventboard.invite.adapter.out.persistence;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.eukon05.eventboard.common.Adapter;
import pl.eukon05.eventboard.invite.application.port.out.CheckUserInvitedOutPort;
import pl.eukon05.eventboard.invite.application.port.out.DeleteInvitePort;
import pl.eukon05.eventboard.invite.application.port.out.GetInvitePort;
import pl.eukon05.eventboard.invite.application.port.out.SaveInvitePort;
import pl.eukon05.eventboard.invite.domain.Invite;

import java.util.List;
import java.util.Optional;

@Adapter
@RequiredArgsConstructor
class InvitePersistenceAdapter implements GetInvitePort, SaveInvitePort, DeleteInvitePort, CheckUserInvitedOutPort {
    private final InviteRepository repository;
    private final InviteEntityMapper mapper;

    @Override
    public boolean checkUserInvited(String userId, long eventId) {
        return repository.existsByInviteeIdEqualsAndEventIdEquals(userId, eventId);
    }

    @Override
    public void saveInvite(Invite invite) {
        InviteEntity entity = mapper.mapDomainToEntity(invite);
        repository.save(entity);
    }

    @Override
    public Optional<Invite> getById(long id) {
        return repository.findById(id).map(mapper::mapEntityToDomain);
    }

    @Override
    public List<Invite> getForUser(String userId) {
        return repository.getAllByInviteeId(userId).stream().map(mapper::mapEntityToDomain).toList();
    }

    @Override
    @Transactional
    public void deleteInvite(String userId, long eventId) {
        repository.deleteAllByInviteeIdAndEventId(userId, eventId);
    }
}
