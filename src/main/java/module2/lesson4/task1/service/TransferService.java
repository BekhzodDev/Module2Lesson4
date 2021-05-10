package module2.lesson4.task1.service;

import module2.lesson4.task1.entity.Card;
import module2.lesson4.task1.entity.Transfer;
import module2.lesson4.task1.payload.Result;
import module2.lesson4.task1.payload.TransferDto;
import module2.lesson4.task1.repository.CardRepository;
import module2.lesson4.task1.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransferRepository transferRepository;



    //Userning barcha kartalaridagi tushumlar ro'yhati
    public List<Transfer> getIncomesForUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return transferRepository.findByReceivingCardUserName(user.getUsername());
    }

    //Userning bitta kartasining tushumlari
    public List<Transfer> getIncomesForCard(String cardNumber) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (transferRepository.existsByReceivingCardUserNameAndReceivingCardNumber(user.getUsername(), cardNumber)) {
            return transferRepository.findByReceivingCardUserNameAndReceivingCardNumber(user.getUsername(), cardNumber);
        }
        return null;
    }

    //Aynan qaysidir bir tushumni ko'rish
    public Transfer getIncome(Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Transfer> optionalInCome = transferRepository.findByReceivingCardUserNameAndId(user.getUsername(), id);
        return optionalInCome.orElse(null);
    }

    //Userning barcha kartalaridagi chiqimlar ro'yhati
    public List<Transfer> getOutcomesForUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return transferRepository.findBySendingCardUserName(user.getUsername());
    }

    //Userning bitta kartasining chiqimlari
    public List<Transfer> getOutcomesForCard(String cardNumber) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (transferRepository.existsBySendingCardUserNameAndSendingCardNumber(user.getUsername(), cardNumber))
            return transferRepository.findBySendingCardUserNameAndSendingCardNumber(user.getUsername(), cardNumber);
        return null;
    }

    //Aynan qaysidir bir chiqimni ko'rish
    public Transfer getOutcome(Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Transfer> optionalTransfer = transferRepository.findBySendingCardUserNameAndId(user.getUsername(), id);
        return optionalTransfer.orElse(null);
    }

    public Result transferMoney(TransferDto transferDto){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!cardRepository.existsByNumber(transferDto.getSenderCard()))
            return new Result("Sender card not found", false);
        if (!cardRepository.existsByNumberAndUserName(transferDto.getSenderCard(), user.getUsername()))
            return new Result("This card does not belong to you", false);
        if (!cardRepository.existsByNumber(transferDto.getRecieverCard()))
            return new Result("Receiver card not found", false);
        Card senderCard= cardRepository.findByNumber(transferDto.getSenderCard());
        Card receiverCard = cardRepository.findByNumber(transferDto.getRecieverCard());
        double balance=senderCard.getBalance();
        if (balance < transferDto.getAmount()*1.01)
            return new Result("Not enough money in your card", false);
        senderCard.setBalance(balance-transferDto.getAmount()*1.01);
        receiverCard.setBalance(receiverCard.getBalance()+ transferDto.getAmount());
        cardRepository.save(senderCard);
        cardRepository.save(receiverCard);
        Transfer transfer = new Transfer();
        transfer.setSendingCard(senderCard);
        transfer.setReceivingCard(receiverCard);
        transfer.setAmount(transferDto.getAmount());
        transfer.setDate(new Date(System.currentTimeMillis()));
        transferRepository.save(transfer);
        return new Result("Money transfered successfully", true);
    }



}
