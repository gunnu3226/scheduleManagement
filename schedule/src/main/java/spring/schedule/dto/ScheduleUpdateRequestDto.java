package spring.schedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScheduleUpdateRequestDto {

    private Long id;

    private String title;

    private String contents;

    private String author;

    private String password;
}
