package CodersBay.Kino.controllerExceptionhandler;

import CodersBay.Kino.controllerExceptionhandler.customExeption.*;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.sql.Time;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("type", "about:blank");
        body.put("title", "Bad Request");
        body.put("detail", "Please check your Values and try again");
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("instance", request.getDescription(false).toString().substring(4));
        body.put("timestamp", Instant.now().toString());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckPropertiesException.class)
    public ProblemDetail handleCheckPropertiesException(CheckPropertiesException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Bad Request");
        problem.setDetail(e.getMessage());
        problem.setStatus(HttpStatus.BAD_REQUEST);
        problem .setProperty("TimeStamp", Instant.now().toString());
        return problem;
    }
    @ExceptionHandler(MovieVersionIsNotSupported.class)
    public ProblemDetail movieVersionIsNotSupported(MovieVersionIsNotSupported e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setInstance(URI.create("/api/movie"));
        problem.setInstance(URI.create("/api/hall"));
        problem.setTitle("Change the Version");
        problem.setDetail(e.getMessage());
        problem.setStatus(HttpStatus.BAD_REQUEST);
        problem .setProperty("TimeStamp", Instant.now().toString());
        return problem;
    }

    @ExceptionHandler(NotPossibleBecauseThereAreSomeMovies.class)
    public ProblemDetail notPossibleBecauseThereAreSomeMovies(NotPossibleBecauseThereAreSomeMovies e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setInstance(URI.create("/api/cinema"));
        problem.setTitle("First delete the Movies");
        problem.setDetail(e.getMessage());
        problem.setStatus(HttpStatus.BAD_REQUEST);
        problem .setProperty("TimeStamp", Instant.now().toString());
        return problem;
    }

    @ExceptionHandler(IsNotPossibleBeacauseMaxHallsIsReached.class)
    public ProblemDetail isNotPossibleBecauseMaxHallsIsReached(IsNotPossibleBeacauseMaxHallsIsReached e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setInstance(URI.create("/api/cinema"));
        problem.setTitle("Reached Max Halls!");
        problem.setDetail(e.getMessage());
        problem.setStatus(HttpStatus.BAD_REQUEST);
        problem .setProperty("TimeStamp", Instant.now().toString());
        return problem;
    }

    @ExceptionHandler(CanNotChangeVersion.class)
    public ProblemDetail canNotChangeVersion(CanNotChangeVersion e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setInstance(URI.create("/api/hall"));
        problem.setTitle("This Change is not Allowed");
        problem.setDetail(e.getMessage());
        problem.setStatus(HttpStatus.BAD_REQUEST);
        problem .setProperty("TimeStamp", Instant.now().toString());
        return problem;
    }
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFoundException(NotFoundException e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setInstance(URI.create(e.getUrl()));
        problem.setTitle("Not Found");
        problem.setDetail(e.getMessage());
        problem.setStatus(HttpStatus.NOT_FOUND);
        problem .setProperty("TimeStamp", Instant.now().toString());
        return problem;
    }
    @ExceptionHandler(PasswordNotMatch.class)
    public ProblemDetail passwordNotMatchException(PasswordNotMatch e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setInstance(URI.create("/api/cinema"));
        problem.setTitle("Password Not Match");
        problem.setDetail(e.getMessage());
        problem.setStatus(HttpStatus.BAD_REQUEST);
        problem .setProperty("TimeStamp", Instant.now().toString());
        return problem;
    }

}
