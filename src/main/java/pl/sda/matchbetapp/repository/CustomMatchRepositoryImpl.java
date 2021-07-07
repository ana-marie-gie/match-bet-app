package pl.sda.matchbetapp.repository;

import org.springframework.stereotype.Repository;
import pl.sda.matchbetapp.api.model.MatchSearchParams;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomMatchRepositoryImpl implements CustomMatchRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<MatchEntity> searchByParams(MatchSearchParams searchParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MatchEntity> query = cb.createQuery(MatchEntity.class);
        Root<MatchEntity> root = query.from(MatchEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if(searchParams.getStartTimeFrom() !=null){
            predicates.add(cb.greaterThan(root.get("startTime"), searchParams.getStartTimeFrom()));
        }
        if(searchParams.getStartTimeTo() != null){
            predicates.add(cb.lessThan(root.get("startTime"),searchParams.getStartTimeTo()));
        }
        if(searchParams.getFirstTeam() != null && !searchParams.getFirstTeam().isEmpty()){
            if(searchParams.isSearchAnyCombination()){
                predicates.add(cb.or(
                cb.equal(root.get("firstTeam"), searchParams.getFirstTeam()),
                cb.equal(root.get("secondTeam"), searchParams.getFirstTeam())
                ));
            } else{
                predicates.add(cb.equal(root.get("firstTeam"), searchParams.getFirstTeam()));
            }
        }

        if(searchParams.getSecondTeam() != null && !searchParams.getSecondTeam().isEmpty()){
            if(searchParams.isSearchAnyCombination()){
                predicates.add(cb.or(
                        cb.equal(root.get("firstTeam"),searchParams.getSecondTeam()),
                        cb.equal(root.get("secondTeam"), searchParams.getSecondTeam())
                ));
            } else{
                predicates.add(cb.equal(root.get("secondTeam"), searchParams.getSecondTeam()));
            }
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();


    }
}
