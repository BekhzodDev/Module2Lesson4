package module2.lesson4.task1.repository;

import module2.lesson4.task1.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<module2.lesson4.task1.entity.Transfer, Integer> {
    List<Transfer> findByReceivingCardUserName(String receivingCard_userName);

    List<Transfer> findByReceivingCardUserNameAndReceivingCardNumber(String receivingCard_userName, String receivingCard_number);

    boolean existsByReceivingCardUserNameAndReceivingCardNumber(String receivingCard_userName, String receivingCard_number);

    Optional<Transfer> findByReceivingCardUserNameAndId(String receivingCard_userName, Integer id);

    List<Transfer> findBySendingCardUserName(String sendingCard_userName);

    List<Transfer> findBySendingCardUserNameAndSendingCardNumber(String sendingCard_userName, String sendingCard_number);

    boolean existsBySendingCardUserNameAndSendingCardNumber(String sendingCard_userName, String sendingCard_number);

    Optional<Transfer> findBySendingCardUserNameAndId(String sendingCard_userName, Integer id);

}
