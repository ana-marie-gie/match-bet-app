package pl.sda.matchbetapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.matchbetapp.api.model.BetDetails;
import pl.sda.matchbetapp.api.model.NewBet;
import pl.sda.matchbetapp.service.BetService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bet")
@RequiredArgsConstructor
public class BetEndPoint {

    private final BetService betService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBet(@Valid @RequestBody NewBet bet){
        betService.createBet(bet);
    }

    @DeleteMapping
    public void deleteBet(@RequestParam Long id){
        betService.deleteBet(id);
    }

    @GetMapping("/user/{userId}")
    public List<BetDetails> getForUser(@PathVariable Long userId){
        return betService.getAllForUser(userId);
    }
    @GetMapping("/match/{matchId}")
    public List<BetDetails> getAllForMatch(@PathVariable Long matchId){
        return betService.getAllForMatch(matchId);
    }
    
}
