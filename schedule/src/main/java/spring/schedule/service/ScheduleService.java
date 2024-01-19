package spring.schedule.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.schedule.dto.ScheduleRequestDto;
import spring.schedule.dto.ScheduleResponseDto;
import spring.schedule.dto.ScheduleUpdateRequestDto;
import spring.schedule.entity.Schedule;
import spring.schedule.repository.ScheduleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    @Autowired EntityManager em;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        Schedule saveSchedule = scheduleRepository.save(schedule);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(saveSchedule);
        return responseDto;
    }

    public ScheduleResponseDto readSchedule(long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID 값을 가지는 일정이 존재하지 않습니다."));
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }


    @Transactional
    public ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleUpdateRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID 값을 가지는 일정이 존재하지 않습니다."));
        schedule.update(requestDto);
//        em.flush();
//        try {
//            Thread.sleep(10000); // 1000 밀리초(1초) 동안 현재 스레드를 일시 중지
//        } catch (InterruptedException e) {
//            // InterruptedException이 발생할 수 있으므로 예외 처리 필요
//            e.printStackTrace();
//        }
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }
}
