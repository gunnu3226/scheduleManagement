package spring.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.schedule.dto.ScheduleRequestDto;
import spring.schedule.dto.ScheduleCreateResponseDto;
import spring.schedule.entity.Schedule;
import spring.schedule.repository.ScheduleRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleCreateResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        Schedule saveSchedule = scheduleRepository.save(schedule);
        ScheduleCreateResponseDto responseDto = new ScheduleCreateResponseDto(saveSchedule);
        return responseDto;
    }
}
