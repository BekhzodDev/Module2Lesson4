package module2.lesson4.task1.controller;

import module2.lesson4.task1.entity.Card;
import module2.lesson4.task1.payload.CardDto;
import module2.lesson4.task1.payload.Result;
import module2.lesson4.task1.service.CardServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardServise cardServise;

    @GetMapping
public List<Card> getCards(){
    return cardServise.getCards();
}
@PostMapping
public Result addCard(@RequestBody Card card){
    return cardServise.addCard(card);
}
@PostMapping("/{id}")
public Result editCard(@PathVariable Integer id, @RequestBody CardDto cardDto){
        return cardServise.editCard(cardDto, id);
}
@DeleteMapping("/{id}")
public Result deleteCard(@PathVariable Integer id){
        return cardServise.deleteCard(id);
}
}
