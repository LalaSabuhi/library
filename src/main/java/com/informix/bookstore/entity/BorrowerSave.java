package com.informix.bookstore.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "book"})
})
public class BorrowerSave implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "user_account_id")
    private BorrowerProfile userId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="book", referencedColumnName = "bookPostId")
    private BookPostActivity book;

    public BorrowerSave() {
    }

    public BorrowerSave(Integer id, BorrowerProfile userId, BookPostActivity book) {
        this.id = id;
        this.userId = userId;
        this.book = book;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BorrowerProfile getUserId() {
        return userId;
    }

    public void setUserId(BorrowerProfile userId) {
        this.userId = userId;
    }

    public BookPostActivity getBook() {
        return book;
    }

    public void setBook(BookPostActivity book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BorrowerSave{" +
                "id=" + id +
                ", userId=" + userId +
                ", book=" + book +
                '}';
    }
}
