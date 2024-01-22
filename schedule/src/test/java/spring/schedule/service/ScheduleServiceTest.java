package spring.schedule.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
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
import spring.schedule.dto.ScheduleDeleteRequestDto;
import spring.schedule.dto.ScheduleRequestDto;
import spring.schedule.dto.ScheduleResponseDto;
import spring.schedule.dto.ScheduleUpdateRequestDto;
import spring.schedule.entity.Schedule;
import spring.schedule.exception.MismatchPasswordException;
import spring.schedule.repository.ScheduleRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduleServiceTest {

    @Autowired ScheduleService scheduleService;
    @Autowired ScheduleRepository scheduleRepository;
    @PersistenceContext
    EntityManager em;

    private ScheduleRequestDto testDataDto1;
    private ScheduleRequestDto testDataDto2;

    @BeforeEach
    void 데이터생성() {
        testDataDto1 = new ScheduleRequestDto();
        testDataDto1.setTitle("제목");
        testDataDto1.setContents("내용");
        testDataDto1.setAuthor("이름");
        testDataDto1.setPassword("1234");
        testDataDto1.setInputTime("24-01-19");

        testDataDto2 = new ScheduleRequestDto();
        testDataDto2.setTitle("제목2");
        testDataDto2.setContents("내용2");
        testDataDto2.setAuthor("이름2");
        testDataDto2.setPassword("12345");
        testDataDto2.setInputTime("24-01-20");
    }

    @Test
    void 일정등록() throws Exception {
        //given(beforeEach에서 request생성)
        //when
        ScheduleResponseDto responseDto = scheduleService.createSchedule(testDataDto1);
        em.clear();
        Schedule findSchedule = em.find(Schedule.class, responseDto.getId());  //찾아올 필요가 있나...? 어짜피 위 dto가 서비스에서 리턴받는 값인데
        //then
        assertThat(findSchedule.getTitle()).isEqualTo("제목");
        assertThat(findSchedule.getContents()).isEqualTo("내용");
    }


    @Test
    void 일정조회() throws Exception {
        //given
        Schedule savedschedule = scheduleRepository.save(new Schedule(testDataDto1));
        em.clear();
        //when
        ScheduleResponseDto savedResponseDto = scheduleService.readSchedule(savedschedule.getId());
        //then
        assertThat(savedResponseDto.getTitle()).isEqualTo("제목");
    }

    @Test
    public void 일정조회실패() throws Exception {
        //given
        Schedule savedschedule = scheduleRepository.save(new Schedule(testDataDto1));
        em.clear();
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> {
            ScheduleResponseDto savedResponseDto = scheduleService.readSchedule(100);
        });

    }

    @Test
    public void 일정전체조회() throws Exception {
        //given
        Schedule savedSchedule1 = scheduleRepository.save(new Schedule(testDataDto1));
        Schedule savedSchedule2 = scheduleRepository.save(new Schedule(testDataDto2));
        //when
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.readScheduleList();
        //then
        assertThat(scheduleResponseDtoList.size()).isEqualTo(2);
    }

    @Test
    void 일정수정() throws Exception {
        //given
        Schedule savedschedule = scheduleRepository.save(new Schedule(testDataDto1));
        ScheduleUpdateRequestDto updateRequestDto = new ScheduleUpdateRequestDto();
        updateRequestDto.setTitle("변경된제목");
        updateRequestDto.setContents("변경된내용");
        updateRequestDto.setAuthor("변경된글쓴이");
        updateRequestDto.setPassword("1234");
        //when
        scheduleService.updateSchedule(savedschedule.getId(), updateRequestDto);
        em.clear();
        Schedule schedule = em.find(Schedule.class, savedschedule.getId());
        //then
        assertThat(schedule.getTitle()).isEqualTo("변경된제목");
        assertThat(schedule.getContents()).isEqualTo("변경된내용");
        assertThat(schedule.getAuthor()).isEqualTo("변경된글쓴이");
    }

    @Test
    void 일정수정실패() throws Exception {
        //given
        Schedule savedschedule = scheduleRepository.save(new Schedule(testDataDto1));
        ScheduleUpdateRequestDto updateRequestDto = new ScheduleUpdateRequestDto();
        updateRequestDto.setTitle("변경된제목");
        updateRequestDto.setContents("변경된내용");
        updateRequestDto.setAuthor("변경된글쓴이");
        updateRequestDto.setPassword("12345");
        //when
        assertThrows(MismatchPasswordException.class, () -> {
            scheduleService.updateSchedule(savedschedule.getId(), updateRequestDto);
        });
    }

    @Test
    public void 일정삭제() throws Exception {
        //given
        Schedule savedschedule = scheduleRepository.save(new Schedule(testDataDto1));
        ScheduleDeleteRequestDto deleteRequestDto = new ScheduleDeleteRequestDto();
        deleteRequestDto.setPassword(savedschedule.getPassword());
        //when
        Long deletedId = scheduleService.deleteSchedule(savedschedule.getId(), deleteRequestDto);
        //then
        assertThrows(EntityNotFoundException.class, () -> {
            scheduleRepository.findById(savedschedule.getId()).orElseThrow(EntityNotFoundException::new);
        });
    }
}