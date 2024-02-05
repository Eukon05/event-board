package pl.eukon05.eventboard.event.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface EventRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {
    Page<EventEntity> findByGuestIDsContainingOrderByIdDesc(String userID, Pageable pageable);

    Page<EventEntity> findByOrganizerIDOrderByIdDesc(String userID, Pageable pageable);

    boolean existsByOrganizerIDEqualsAndIdEquals(String userId, long eventId);

    boolean existsByGuestIDsContainingAndIdEquals(String userId, long eventId);
}
