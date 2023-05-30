package pl.eukon05.eventboard.event.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface EventRepository extends JpaRepository<EventEntity, Long> {
}
