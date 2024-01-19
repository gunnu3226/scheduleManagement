package spring.schedule.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import spring.schedule.dto.ScheduleUpdateRequestDto;
import spring.schedule.entity.Schedule;
import spring.schedule.repository.ScheduleRepository;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduleServiceTest {

    @Autowired ScheduleService scheduleService;
    @Autowired ScheduleRepository scheduleRepository;
    @PersistenceContext
    EntityManager em;

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
        Schedule findSchedule = em.find(Schedule.class, responseDto.getId());  //찾아올 필요가 있나...? 어짜피 위 dto가 서비스에서 리턴받는 값인데
        //then
        assertThat("제목").isEqualTo(findSchedule.getTitle());
        assertThat("내용").isEqualTo(findSchedule.getContents());
    }


    @Test
    @Rollback
    void 일정조회() throws Exception {
        //given
        ScheduleResponseDto createSchedule = scheduleService.createSchedule(requestDto);
        em.clear();
        //when
        ScheduleResponseDto savedResponseDto = scheduleService.readSchedule(createSchedule.getId());
        //then
        assertThat(createSchedule.getTitle()).isEqualTo(savedResponseDto.getTitle());
    }

    @Test
    @Transactional
    @Rollback(false)
    void 일정수정() throws Exception {
        //given
        Schedule saved = new Schedule(requestDto);
        em.persist(saved);
        em.flush();
        ScheduleUpdateRequestDto updateRequestDto = new ScheduleUpdateRequestDto();
        updateRequestDto.setId(saved.getId());
        updateRequestDto.setTitle("변경된제목");
        updateRequestDto.setContents("변경된내용");
        updateRequestDto.setAuthor("변경된글쓴이");
        updateRequestDto.setPassword("1234");

        //when
        Schedule updateDto = scheduleRepository.save(new Schedule(updateRequestDto));
        em.flush();
        em.clear();
        Schedule schedule = em.find(Schedule.class, saved.getId());

        //then
//        assertThat(schedule.getTitle()).isEqualTo("변경된제목");
//        assertThat(schedule.getContents()).isEqualTo("변경된내용");
//        assertThat(schedule.getAuthor()).isEqualTo("변경된글쓴이");
    }

//    @Test
//    @Transactional
//    @Rollback(false)
//    void 일정수정2() throws Exception {
//        //given
//        Schedule savedschedule = scheduleRepository.save(new Schedule(requestDto));
//
//        em.persist(saved);
//        em.flush();
//        ScheduleUpdateRequestDto updateRequestDto = new ScheduleUpdateRequestDto();
//        updateRequestDto.setId(saved.getId());
//        updateRequestDto.setTitle("변경된제목");
//        updateRequestDto.setContents("변경된내용");
//        updateRequestDto.setAuthor("변경된글쓴이");
//        updateRequestDto.setPassword("1234");
//
//        //when
//        Schedule updateDto = scheduleRepository.save(new Schedule(updateRequestDto));
//        em.flush();
//        em.clear();
//        Schedule schedule = em.find(Schedule.class, saved.getId());
//
//        //then
//        assertThat(schedule.getTitle()).isEqualTo("변경된제목");
//        assertThat(schedule.getContents()).isEqualTo("변경된내용");
//        assertThat(schedule.getAuthor()).isEqualTo("변경된글쓴이");
//    }
}