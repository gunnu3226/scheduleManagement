package spring.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> readSchedule(@PathVariable Long scheduleId) {
        ScheduleResponseDto responseDto = scheduleService.readSchedule(scheduleId);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleUpdateRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(scheduleId, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }


}