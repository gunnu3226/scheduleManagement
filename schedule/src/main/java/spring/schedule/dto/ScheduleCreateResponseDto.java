package spring.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import spring.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter @Setter
public class ScheduleCreateResponseDto {

    private Long id;

    private String title;

    private String contents;

    private String author;

    private String inputTime;

    public ScheduleCreateResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.author = schedule.getAuthor();
        this.inputTime = schedule.getInputTime();
    }
}
