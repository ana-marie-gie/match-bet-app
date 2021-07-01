package pl.sda.matchbetapp.api;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.matchbetapp.api.model.Error;
import pl.sda.matchbetapp.api.model.Match;
import pl.sda.matchbetapp.exception.DateInPastException;
import pl.sda.matchbetapp.exception.MatchNotFoundException;
import pl.sda.matchbetapp.service.MatchService;
import pl.sda.matchbetapp.exception.IllegalStateException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchEndpoint {

    public final static Logger LOGGER = LoggerFactory.getLogger(MatchEndpoint.class);
    private final MatchService matchService;

    @GetMapping
    public List<Match> getAll() {
        return matchService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMatch(@RequestBody Match match) {
        matchService.create(match);
    }

    @PutMapping
    public void updateMatch(@RequestBody Match match) {
        matchService.update(match);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMatch(@RequestParam Long id) {
        matchService.delete(id);
    }

    @ExceptionHandler(value = {DateInPastException.class})
    public ResponseEntity<Error> handleDateInPast(DateInPastException ex) {
        String code = UUID.randomUUID().toString();
        LOGGER.error("Error occurred" + code, ex);
        Error error = Error.builder()
                .code(code)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<Error> handleIllegalState(IllegalStateException ex){
        String code = UUID.randomUUID().toString();
        LOGGER.error("Error occurred" + code, ex);
        Error error = Error.builder()
                .code(code)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    @ExceptionHandler(value = {DateInPastException.class})
    public ResponseEntity<Error> handleMatchNotFound(MatchNotFoundException ex) {
        String code = UUID.randomUUID().toString();
        LOGGER.error("Error occurred" + code, ex);
        Error error = Error.builder()
                .code(code)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}


