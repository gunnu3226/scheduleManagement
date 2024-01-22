package spring.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MismatchPasswordException extends RuntimeException {
    public MismatchPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
