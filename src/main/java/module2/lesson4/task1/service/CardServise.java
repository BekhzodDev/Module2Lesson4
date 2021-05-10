package module2.lesson4.task1.service;

import module2.lesson4.task1.entity.Card;
import module2.lesson4.task1.payload.CardDto;
import module2.lesson4.task1.payload.Result;
import module2.lesson4.task1.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServise {
    @Autowired
    CardRepository cardRepository;

    public List<Card> getCards() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cardRepository.findByUserName(user.getUsername());
    }

    public Result addCard(Card cardDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (cardRepository.existsByNumber(cardDto.getNumber()))
            return new Result("The card number allready exist", false);
        Card card = new Card();
        card.setNumber(cardDto.getNumber());
        card.setUserName(user.getUsername());
        card.setBalance(cardDto.getBalance());
        card.setExpireDate(cardDto.getExpireDate());
        cardRepository.save(card);
        return new Result("Card added", true);
    }

    public Result editCard(CardDto cardDto, Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent()) return new Result("Card not found", false);
        if (cardRepository.existsByUserNameAndId(user.getUsername(), id)) {
            if (cardRepository.existsByNumberAndIdNot(cardDto.getNumber(), id)) {
                Card card = optionalCard.get();
                card.setNumber(cardDto.getNumber());
                card.setBalance(cardDto.getBalance());
                card.setActive(cardDto.isActive());
                cardRepository.save(card);
                return new Result("Card edited", true);
            }
            return new Result("The card number is allready exist", false);

        }
        return new Result("This card doesn't belong to you", false);
    }

    public Result deleteCard(Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent()) return new Result("Card not found", false);
        if (cardRepository.existsByUserNameAndId(user.getUsername(), id)) {
            cardRepository.deleteById(id);
            return new Result("Card succesfully deleted", true);
        }
        return new Result("This card doesn't belong to you", false);
    }
}


