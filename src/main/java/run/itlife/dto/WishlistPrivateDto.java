package run.itlife.dto;

import run.itlife.entity.User;
import run.itlife.entity.Wishlist;

public class WishlistPrivateDto {
    private Long privateIdWishlistPrivate;
    private Wishlist wishlistIdWishlistPrivate;
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
