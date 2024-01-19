package spring.schedule.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ScheduleRequestDto {

    private String title;

    private String contents;

    private String author;

    private String password;

    private String inputTime;
}
