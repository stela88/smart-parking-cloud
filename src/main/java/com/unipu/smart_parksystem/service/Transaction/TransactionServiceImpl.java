package com.unipu.smart_parksystem.service.Transaction;

import com.unipu.smart_parksystem.dto.TransactionDto;
import com.unipu.smart_parksystem.entity.Ticket;
import com.unipu.smart_parksystem.entity.Transaction;
import com.unipu.smart_parksystem.error.Transaction.TransactionNotFoundException;
import com.unipu.smart_parksystem.mapper.TicketMapper;
import com.unipu.smart_parksystem.mapper.TransactionMapper;
import com.unipu.smart_parksystem.repository.Ticket.TicketRepository;
import com.unipu.smart_parksystem.repository.Transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TicketRepository ticketRepository;


    @Override
    @Transactional
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(transactionDto.getTicket().getTicketId());

        // Check if the ticket exists
        if (ticketOptional.isPresent()) {
            Transaction transaction = new Transaction();
            transaction.setAmount(transactionDto.getAmount());
            transaction.setTicket(ticketOptional.get());
            Instant now = Instant.now();
            transaction.setCreatedTs(now);
            transaction.setModifiedTs(now);

            // Save the transaction
            Transaction savedTransaction = transactionRepository.save(transaction);

            return TransactionMapper.convertEntityToDto(savedTransaction);
        }

        return null; 
    }

    @Override
    public List<TransactionDto> fetchTransactionList(){
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for(Transaction transaction:transactions){
            TransactionDto transactionDto = TransactionMapper.convertEntityToDto(transaction);
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }


    @Override
    public TransactionDto fetchTransactionById(Long transactionId) throws TransactionNotFoundException {
        Optional<Transaction> transaction =
                transactionRepository.findById(transactionId);

        if(!transaction.isPresent()) {
            throw new TransactionNotFoundException("Transaction Not Available");
        }

        return  TransactionMapper.convertEntityToDto(transaction.get());
    }

    @Override
    public void deleteTransactiontById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }


    @Override
    public TransactionDto updateTransaction(Long transactionId, Transaction transaction) throws TransactionNotFoundException {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if(transactionOptional.isEmpty()){
            throw new TransactionNotFoundException("Transaction not found");
        }

        Transaction transactionDB = transactionOptional.get();


        if(Objects.nonNull(transaction.getAmount())){
            transactionDB.setAmount(transaction.getAmount());
        }

        if(Objects.nonNull(transaction.getTicket())){
            transactionDB.setTicket(transaction.getTicket());
        }

        if(Objects.isNull(transaction.getCreatedTs())){
            transactionDB.setCreatedTs(transaction.getCreatedTs());
        }

        if(Objects.isNull(transaction.getModifiedTs())){
            transactionDB.setModifiedTs(transaction.getModifiedTs());
        }

        return TransactionMapper.convertEntityToDto(transactionRepository.save(transactionDB));
    }


    @Transactional
    public Transaction create(/*String registration*/) {
        Instant now = Instant.now();
        Transaction transaction = Transaction.builder()
                .amount(null)
                .createdTs(null)
                .modifiedTs(null)
                .ticket(null)
                .build();
        return transactionRepository.save(transaction);
    }
}
