package module2.lesson4.task1.controller;

import module2.lesson4.task1.entity.Transfer;
import module2.lesson4.task1.payload.Result;
import module2.lesson4.task1.payload.TransferDto;
import module2.lesson4.task1.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    TransferService transferService;

    @GetMapping("/incomes")
    public List<Transfer> getIncomesForUser() {
        return transferService.getIncomesForUser();
    }

    @GetMapping("/{income}")
    public List<Transfer> getIncomesForCard(@PathVariable String income) {
        return transferService.getIncomesForCard(income);
    }

    @GetMapping("/income/{id}")
    public Transfer getIncome(@PathVariable Integer id) {
        return transferService.getIncome(id);
    }

    @GetMapping("/outcomes")
    public List<Transfer> getOutcomesForUser() {
        return transferService.getOutcomesForUser();
    }

    @GetMapping("/{outcome}")
    public List<Transfer> getOutcomesForCard(@PathVariable String outcome) {
        return transferService.getOutcomesForCard(outcome);
    }

    @GetMapping("/outcome/{id}")
    public Transfer getOutcome(@PathVariable Integer id) {
        return transferService.getOutcome(id);
    }

    @PostMapping
    public Result moneyTransfer(@RequestBody TransferDto transferDto) {
        return transferService.transferMoney(transferDto);
    }
}
