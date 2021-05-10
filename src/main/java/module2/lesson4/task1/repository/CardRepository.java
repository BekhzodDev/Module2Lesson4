package module2.lesson4.task1.repository;

import module2.lesson4.task1.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    boolean existsByNumber(String number);
    boolean existsByNumberAndUserName(String number, String userName);
    boolean existsByNumberAndIdNot(String number, Integer id);
    boolean existsByUserNameAndId(String userName, Integer id);
    List<Card> findByUserName(String userName);
    Card findByNumber(String number);

}
