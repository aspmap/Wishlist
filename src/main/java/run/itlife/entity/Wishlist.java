package run.itlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Table
@Entity
public class Wishlist {
    @Id
    @Column(name="wishlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;
    @Column(name = "photo")
    private String photo;
    @Column(name = "link")
    private String link;
    @Column(name = "name_wish")
    private String nameWish;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private String price;
    @Column(name = "is_secret")
    private boolean isSecret;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "is_booking")
    private Boolean isBooking;
    @Column(name = "is_done")
    private Boolean isDone;
    @Column(name = "booking_user")
    private Long bookingUser;
    @OneToMany(mappedBy = "wishlistIdWishlistPrivate", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<WishlistPrivate> wishlistPrivate;

    public Set<WishlistPrivate> getWishlistPrivate() {
        return wishlistPrivate;
    }

    public void setWishlistPrivate(Set<WishlistPrivate> wishlistPrivate) {
        this.wishlistPrivate = wishlistPrivate;
    }

    public Boolean getBooking() {
        return isBooking;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public boolean isSecret() {
        return isSecret;
    }

    public void setSecret(boolean secret) {
        isSecret = secret;
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

    public String getNameWish() {
        return nameWish;
    }

    public void setNameWish(String nameWish) {
        this.nameWish = nameWish;
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