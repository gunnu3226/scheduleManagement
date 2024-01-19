package spring.schedule.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import spring.schedule.dto.ScheduleCreateResponseDto;
import spring.schedule.dto.ScheduleRequestDto;
import spring.schedule.entity.Schedule;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ScheduleServiceTest {

    @Autowired ScheduleService scheduleService;

    @Autowired EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    void 일정등록() throws Exception {
        //given
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        requestDto.setTitle("제목2");
        requestDto.setContents("내용2");
        requestDto.setAuthor("아기두부");
        requestDto.setPassword("1234");
        requestDto.setInputTime("24-01-19");
        //when
        ScheduleCreateResponseDto responseDto = scheduleService.createSchedule(requestDto);
        Schedule findSchedule = em.find(Schedule.class, responseDto.getId());
        //then
        Assertions.assertThat("제목2").isEqualTo(findSchedule.getTitle());
        Assertions.assertThat("내용2").isEqualTo(findSchedule.getContents());
    }

}