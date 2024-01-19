package spring.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.schedule.dto.ScheduleRequestDto;
import spring.schedule.dto.ScheduleUpdateRequestDto;
import spring.schedule.exception.MismatchPasswordException;

@Entity
@Table(name = "shcedule")
@Getter
@NoArgsConstructor
public class Schedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    private String title;

    private String contents;

    private String author;

    private String password;

    private String inputTime;

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.author = requestDto.getAuthor();
        this.password = requestDto.getPassword();
        this.inputTime = requestDto.getInputTime();
    }

    public Schedule(ScheduleUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.author = requestDto.getAuthor();
        this.password = requestDto.getPassword();
    }


    public void update(ScheduleUpdateRequestDto requestDto) {
        if (!requestDto.getPassword().equals(this.password)) {
            throw new MismatchPasswordException();
        }
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.author = requestDto.getAuthor();
    }
}
