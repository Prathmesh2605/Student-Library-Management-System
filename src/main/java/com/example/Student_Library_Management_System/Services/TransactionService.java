package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import com.example.Student_Library_Management_System.Models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CardRepository cardRepository;

    public String issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception{
        int bookId = issueBookRequestDto.getBookId();
        int cardID = issueBookRequestDto.getCardId();

        //Get the book entity and card entity to set the transaction attributwa

        Book book = bookRepository.findById(bookId).get();
        Card card = cardRepository.findById(cardID).get();

        Transactions transaction = new Transactions();

        //setting the attributes
        transaction.setBook(book);
        transaction.setCard(card);

        //transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setIssueOperation(true);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        //validations
        if(book==null || book.isIssued()==true){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Book is not available");
        }

        if(card==null || (card.getCardStatus()!=CardStatus.ACTIVATED)){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("card is not valid");
        }

        //Success case
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        book.setIssued(true);

        List<Transactions> listOfTransactionForBook = book.getListOfTransactions();
        listOfTransactionForBook.add(transaction);

        List<Book> issuedBooksForCard= card.getBooksIssued();
        issuedBooksForCard.add(book);
        card.setBooksIssued(issuedBooksForCard);

        List<Transactions> transactionsListForCard = card.getTransactionsList();
        transactionsListForCard.add(transaction);
        card.setTransactionsList(transactionsListForCard);


        cardRepository.save(card);


        return "Book issued successfully";
    }
    public String getTransactions(int bookId,int cardId){

        List<Transactions> transactionsList = transactionRepository.getTransactionsForBookAndCard(bookId,cardId);

        String transactionId = transactionsList.get(0).getTransactionId();

        return transactionId;
    }
}
