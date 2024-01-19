package spring.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import spring.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter @Setter
public class ScheduleResponseDto {

    private Long id;

    private String title;

    private String contents;

    private String author;

    private String inputTime;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.author = schedule.getAuthor();
        this.inputTime = schedule.getInputTime();
    }
}
