package com.informix.bookstore.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class BookPostActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookPostId;

    @ManyToOne
    @JoinColumn(name= "postedById", referencedColumnName = "userId")
    private Users postedById;
    @Transient
    private boolean isActive;
    @Transient
    private boolean isSaved;

    @Length(max = 10000)
    private String descriptionOfBook;
    private String genre;
    @Column(nullable=true, length = 64)
    private String bookImage;
    private int price;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date postedDate;
    private String bookTitle;
    @Transient
    public String getBookImagePath(){
        if (bookImage == null || bookPostId == null) return null;
        return "/photos/book/" + bookPostId + "/" + bookImage;
    }

    public BookPostActivity() {
    }

    public BookPostActivity(Integer bookPostId, Users postedById, boolean isActive, boolean isSaved, String descriptionOfBook, String genre, String bookImage, int price, Date postedDate, String bookTitle) {
        this.bookPostId = bookPostId;
        this.postedById = postedById;
        this.isActive = isActive;
        this.isSaved = isSaved;
        this.descriptionOfBook = descriptionOfBook;
        this.genre = genre;
        this.bookImage = bookImage;
        this.price = price;
        this.postedDate = postedDate;
        this.bookTitle = bookTitle;
    }

    public Integer getBookPostId() {
        return bookPostId;
    }

    public void setBookPostId(Integer bookPostId) {
        this.bookPostId = bookPostId;
    }

    public Users getPostedById() {
        return postedById;
    }

    public void setPostedById(Users postedById) {
        this.postedById = postedById;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public String getDescriptionOfBook() {
        return descriptionOfBook;
    }

    public void setDescriptionOfBook(String descriptionOfBook) {
        this.descriptionOfBook = descriptionOfBook;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Override
    public String toString() {
        return "BookPostActivity{" +
                "bookPostId=" + bookPostId +
                ", postedById=" + postedById +
                ", isActive=" + isActive +
                ", isSaved=" + isSaved +
                ", descriptionOfBook='" + descriptionOfBook + '\'' +
                ", genre='" + genre + '\'' +
                ", bookImage='" + bookImage + '\'' +
                ", price=" + price +
                ", postedDate=" + postedDate +
                ", bookTitle='" + bookTitle + '\'' +
                '}';
    }
}
