package pl.sda.matchbetapp.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.sda.matchbetapp.api.model.Error;

import java.time.LocalDateTime;
import java.util.UUID;

public class GlobalErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler (value = {IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleIllegalArgument(IllegalArgumentException ex){
        return handleError(ex);
    }

    private Error handleError(RuntimeException ex) {
        String code = UUID.randomUUID().toString();
        LOGGER.error("Error occurred" + code, ex);
        return  Error.builder()
                .code(code)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

}
