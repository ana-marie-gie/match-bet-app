package pl.sda.matchbetapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.matchbetapp.api.model.Bet;
import pl.sda.matchbetapp.service.BetService;

import javax.validation.Valid;

@RestController
@RequestMapping("/bet")
@RequiredArgsConstructor
public class BetEndPoint {
    private final BetService betService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBet(@Valid @RequestBody Bet bet){
        betService.createBet(bet);
    }

}
