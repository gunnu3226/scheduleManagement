package spring.schedule.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import spring.schedule.dto.ScheduleRequestDto;
import spring.schedule.dto.ScheduleResponseDto;
import spring.schedule.entity.Schedule;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ScheduleServiceTest {

    @Autowired ScheduleService scheduleService;

    @Autowired EntityManager em;

    private ScheduleRequestDto requestDto;

    @BeforeEach
    void 데이터생성() {
        requestDto = new ScheduleRequestDto();
        requestDto.setTitle("제목");
        requestDto.setContents("내용");
        requestDto.setAuthor("이름");
        requestDto.setPassword("1234");
        requestDto.setInputTime("24-01-19");
    }

    @Test
    @Rollback
    void 일정등록() throws Exception {
        //given(beforeEach에서 request생성)
        //when
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);
        em.clear();
        Schedule findSchedule = em.find(Schedule.class, responseDto.getId());
        //then
        assertThat("제목").isEqualTo(findSchedule.getTitle());
        assertThat("내용").isEqualTo(findSchedule.getContents());
    }

    @Test
    @Rollback
    public void 일정조회() throws Exception {
        //given
        ScheduleResponseDto createSchedule = scheduleService.createSchedule(requestDto);
        em.clear();
        //when
        ScheduleResponseDto savedResponseDto = scheduleService.readSchedule(createSchedule.getId());
        //then
        assertThat(createSchedule.getTitle()).isEqualTo(savedResponseDto.getTitle());
    }
}