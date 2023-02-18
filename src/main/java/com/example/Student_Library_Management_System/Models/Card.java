package com.example.Student_Library_Management_System.Models;
import java.util.*;

import com.example.Student_Library_Management_System.Enums.CardStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name="card")
public class Card {

    //Plan is to save this card in Db.
    //Before saving I have to set its attributes : Rule 1

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  //Its auto generated

    @CreationTimestamp //Auto timestamp the time when an entry is created
    private Date createdOn; //Its auto generated

    @UpdateTimestamp //Sets time when an entry is updated.
    private Date updatedOn; //Its auto generated

    @Enumerated(value = EnumType.STRING)
    private CardStatus cardStatus; //SEt this attribute


    @OneToOne
    @JoinColumn
    private Student studentVariableName; //This varible is used in the parent class.
    // while doing the bidirectional mapping


    public Student getStudentVariableName() {
        return studentVariableName;
    }

    //car is parent wrt book
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    List<Book> bookIssued = new ArrayList<>();

    //Card is parent wrt to Book
    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL)
    List<Book> booksIssued = new ArrayList<>();


    //Connecting the card class to the transaction
    //Bidirectional Mapping
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Transactions> transactionsList = new ArrayList<>();

    public List<Book> getBookIssued() {
        return bookIssued;
    }

    public void setBookIssued(List<Book> bookIssued) {
        this.bookIssued = bookIssued;
    }

    public List<Book> getBooksIssued() {
        return booksIssued;
    }

    public void setBooksIssued(List<Book> booksIssued) {
        this.booksIssued = booksIssued;
    }

    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public void setStudentVariableName(Student studentVariableName) {
        this.studentVariableName = studentVariableName;
    }

    public Card() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }
}
