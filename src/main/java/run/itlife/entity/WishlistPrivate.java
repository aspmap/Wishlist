package run.itlife.entity;

import javax.persistence.*;

@Table(name = "wishlist_private")
@Entity
public class WishlistPrivate {
    @Id
    @Column(name="private_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privateIdWishlistPrivate;
    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlistIdWishlistPrivate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userWishlistPrivate;

    public Long getPrivateIdWishlistPrivate() {
        return privateIdWishlistPrivate;
    }

    public void setPrivateIdWishlistPrivate(Long privateIdWishlistPrivate) {
        this.privateIdWishlistPrivate = privateIdWishlistPrivate;
    }

    public Wishlist getWishlistIdWishlistPrivate() {
        return wishlistIdWishlistPrivate;
    }

    public void setWishlistIdWishlistPrivate(Wishlist wishlistIdWishlistPrivate) {
        this.wishlistIdWishlistPrivate = wishlistIdWishlistPrivate;
    }

    public User getUserWishlistPrivate() {
        return userWishlistPrivate;
    }

    public void setUserWishlistPrivate(User userWishlistPrivate) {
        this.userWishlistPrivate = userWishlistPrivate;
    }
}
