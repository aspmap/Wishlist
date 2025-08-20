package run.itlife.service;

import run.itlife.dto.WishlistDto;
import run.itlife.dto.WishlistPrivateDto;
import run.itlife.entity.User;
import run.itlife.entity.Wishlist;
import run.itlife.entity.WishlistPrivate;

import java.util.ArrayList;
import java.util.Map;

public interface WishlistService {
    ArrayList<Wishlist> findAllByUserOrderByCreatedAt(Long userId);
    ArrayList<Wishlist> findAllByBookingUser(Long bookingUserId);
    ArrayList<Wishlist> findAllByUserAndIsDoneTrue(User user);
    Long createElementOfWishlist(WishlistDto wishlistDto);
    ArrayList<Wishlist> findAllByUserAndSecretIsFalse(User user);
    void createBookingWishlist(String user_sub, Long id);
    void createUnBookingWishlist(String user_sub, Long id);
    Long countAllByUserAndIsBookingTrue(Long userId);
    Long countAllByUser(Long userId);
    void deleteWish(Long id);
    void checkCompleteWish(Long wishId);
    ArrayList<Wishlist> findPrivateWishesByUser(Long userIdPrivate, Long userIdSub);
    Map<Long, ArrayList<WishlistPrivate>> whoSeesSecretWishes(Long userId);
    void addPermission(WishlistPrivateDto wishlistPrivateDto);
}