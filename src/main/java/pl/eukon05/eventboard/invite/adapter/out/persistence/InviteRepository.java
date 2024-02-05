package pl.eukon05.eventboard.invite.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface InviteRepository extends JpaRepository<InviteEntity, Long> {
    boolean existsByInviteeIdEqualsAndEventIdEquals(String inviteeId, long eventId);

    void deleteAllByInviteeIdAndEventId(String inviteeId, long eventId);

    List<InviteEntity> getAllByInviteeId(String userId);
}
