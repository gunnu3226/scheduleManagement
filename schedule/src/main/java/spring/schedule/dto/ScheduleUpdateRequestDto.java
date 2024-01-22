package spring.schedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScheduleUpdateRequestDto {

    private String title;

    private String contents;

    private String author;

    private String password;
}
