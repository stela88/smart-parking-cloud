package com.unipu.smart_parksystem.service.Transaction;

import com.unipu.smart_parksystem.constants.Constants;
import com.unipu.smart_parksystem.dto.TransactionDto;
import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.entity.Transaction;
import com.unipu.smart_parksystem.error.Transaction.TransactionNotFoundException;
import com.unipu.smart_parksystem.mapper.TransactionMapper;
import com.unipu.smart_parksystem.repository.Ticket.TicketRepository;
import com.unipu.smart_parksystem.repository.Transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.unipu.smart_parksystem.util.TicketingUtil.getAmountToPay;
import static com.unipu.smart_parksystem.util.TicketingUtil.hoursToPay;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TicketRepository ticketRepository;


    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TicketRepository ticketRepository) {
        this.transactionRepository = transactionRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        Ticket ticket = ticketRepository.findById(transactionDto.getTicket().getTicketId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Ticket doesn't exist")
                );


        Instant now = Instant.now();

        Instant exitTimeout = ticket.getExitTimeout();
        BigDecimal paidAmount = transactionDto.getAmount();
        //------------------------------------------ plaćanje
        long hoursToPay = hoursToPay(exitTimeout.minus(Constants.MINUTES_FOR_TIMEOUT, ChronoUnit.MINUTES), now);
        BigDecimal amountToPay = getAmountToPay(hoursToPay);
        //------------------------------------------

        if (paidAmount.compareTo(amountToPay) != 0) {
            throw new IllegalArgumentException("Invalid amount paid, you need to pay: " + amountToPay);
        }

        ticket.setExitTimeout(exitTimeout.plus(hoursToPay, ChronoUnit.HOURS));
        ticket = ticketRepository.saveAndFlush(ticket);

        Transaction transaction = Transaction.builder()
                .ticket(ticket)
                .amount(paidAmount)
                .createdTs(now)
                .build();
        transaction = transactionRepository.save(transaction);
        return TransactionMapper.convertEntityToDto(transaction);
    }





    @Override
    public List<TransactionDto> fetchTransactionList() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionDto transactionDto = TransactionMapper.convertEntityToDto(transaction);
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }


    @Override
    public TransactionDto fetchTransactionById(Long transactionId) throws TransactionNotFoundException {
        Optional<Transaction> transaction =
                transactionRepository.findById(transactionId);

        if (!transaction.isPresent()) {
            throw new TransactionNotFoundException("Transaction Not Available");
        }

        return TransactionMapper.convertEntityToDto(transaction.get());
    }

    @Override
    public void deleteTransactiontById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }


    @Override
    public TransactionDto updateTransaction(Long transactionId, Transaction transaction) throws TransactionNotFoundException {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (transactionOptional.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found");
        }

        Transaction transactionDB = transactionOptional.get();


        if (Objects.nonNull(transaction.getAmount())) {
            transactionDB.setAmount(transaction.getAmount());
        }

        if (Objects.nonNull(transaction.getTicket())) {
            transactionDB.setTicket(transaction.getTicket());
        }

        if (Objects.isNull(transaction.getCreatedTs())) {
            transactionDB.setCreatedTs(transaction.getCreatedTs());
        }

        if (Objects.isNull(transaction.getModifiedTs())) {
            transactionDB.setModifiedTs(transaction.getModifiedTs());
        }

        return TransactionMapper.convertEntityToDto(transactionRepository.save(transactionDB));
    }


}
