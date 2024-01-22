package spring.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.schedule.dto.ScheduleDeleteRequestDto;
import spring.schedule.dto.ScheduleRequestDto;
import spring.schedule.dto.ScheduleResponseDto;
import spring.schedule.dto.ScheduleUpdateRequestDto;
import spring.schedule.entity.Schedule;
import spring.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/new")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> readSchedule(@PathVariable Long scheduleId) {
        ScheduleResponseDto responseDto = scheduleService.readSchedule(scheduleId);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ScheduleResponseDto>> readScheduleList() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.readScheduleList();
        return ResponseEntity.ok().body(scheduleResponseDtoList);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleUpdateRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(scheduleId, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<Long> deleteSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDeleteRequestDto requestDto) {
        Long id = scheduleService.deleteSchedule(scheduleId, requestDto);
        return ResponseEntity.ok().body(id);
    }
}