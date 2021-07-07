package pl.sda.matchbetapp.repository;

import pl.sda.matchbetapp.api.model.MatchSearchParams;

import java.util.List;

public interface CustomMatchRepository {
    List<MatchEntity> searchByParams(MatchSearchParams searchParams);
}
