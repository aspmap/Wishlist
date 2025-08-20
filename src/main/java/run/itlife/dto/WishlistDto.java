package run.itlife.dto;

import run.itlife.entity.User;

import java.time.LocalDateTime;

public class WishlistDto {
    private Long wishlistId;
    private String photo;
    private String link;
    private String nameWish;
    private String description;
    private String price;
    private Boolean isSecret;
    private LocalDateTime createdAt;
    private User user;
    private Boolean isBooking;
    private Long bookingUser;
    private Boolean isDone;

    public Boolean getBooking() {
        return isBooking;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Boolean isBooking() {
        return isBooking;
    }

    public void setBooking(Boolean booking) {
        isBooking = booking;
    }

    public Long getBookingUser() {
        return bookingUser;
    }

    public void setBookingUser(Long bookingUser) {
        this.bookingUser = bookingUser;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNameWish() {
        return nameWish;
    }

    public void setNameWish(String nameWish) {
        this.nameWish = nameWish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getSecret() {
        return isSecret;
    }

    public void setSecret(Boolean secret) {
        isSecret = secret;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}