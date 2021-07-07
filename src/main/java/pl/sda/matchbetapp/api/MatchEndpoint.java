package pl.sda.matchbetapp.api;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.matchbetapp.api.model.Error;
import pl.sda.matchbetapp.api.model.Match;
import pl.sda.matchbetapp.api.model.MatchSearchParams;
import pl.sda.matchbetapp.exception.DateInPastException;
import pl.sda.matchbetapp.exception.MatchNotFoundException;
import pl.sda.matchbetapp.service.MatchService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchEndpoint {


    public final static Logger LOGGER = LoggerFactory.getLogger(MatchEndpoint.class);
    private final MatchService matchService;

    @PostMapping("/search")
    public List<Match> getBySearchParams(@RequestBody MatchSearchParams searchParams){
        return matchService.getBySearchParams(searchParams);
    }
    @GetMapping
    public List<Match> getAll() {
        return matchService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createMatch(@Valid @RequestBody Match match, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Error.builder()
                            .code(UUID.randomUUID().toString())
                            .timestamp(LocalDateTime.now().toString())
                            .message(bindingResult.getAllErrors().stream()
                                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                    .collect(Collectors.joining(". ")))
                            .build());
        }
        matchService.createMatch(match);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity updateMatch(@Valid @RequestBody Match match, BindingResult bindingResult) {
        matchService.update(match);
        return ResponseEntity.status(HttpStatus.OK).build();
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
    public ResponseEntity<Error> handleIllegalState(IllegalStateException ex) {
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

    @ExceptionHandler(value = {MatchNotFoundException.class})
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


