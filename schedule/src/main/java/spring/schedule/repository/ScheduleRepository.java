package spring.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.schedule.dto.ScheduleCreateResponseDto;
import spring.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
