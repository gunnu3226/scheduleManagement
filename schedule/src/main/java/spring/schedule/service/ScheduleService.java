package spring.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.schedule.dto.ScheduleDeleteRequestDto;
import spring.schedule.dto.ScheduleRequestDto;
import spring.schedule.dto.ScheduleResponseDto;
import spring.schedule.dto.ScheduleUpdateRequestDto;
import spring.schedule.entity.Schedule;
import spring.schedule.exception.MismatchPasswordException;
import spring.schedule.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

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

    public List<ScheduleResponseDto> readScheduleList() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        if(scheduleList.size() == 0) {
            throw new NoSuchElementException("등록된 일정이 없습니다.");
        }
        List<ScheduleResponseDto> scheduleResponseDtoList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            scheduleResponseDtoList.add(new ScheduleResponseDto(schedule));
        }
        return scheduleResponseDtoList;
    }


    @Transactional
    public ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleUpdateRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID 값을 가지는 일정이 존재하지 않습니다."));
        schedule.update(requestDto);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }


    @Transactional
    public Long deleteSchedule(Long scheduleId, ScheduleDeleteRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID 값을 가지는 일정이 존재하지 않습니다."));
        if(schedule.getPassword() != requestDto.getPassword()) {
            throw new MismatchPasswordException();
        }
        scheduleRepository.delete(schedule);
        return scheduleId;
    }
}
