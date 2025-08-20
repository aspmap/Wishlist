package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.User;
import run.itlife.entity.Wishlist;

import java.util.ArrayList;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query(value = "select * from wishlist w " +
            "where (w.is_done = 'false' or w.is_done is null) and w.user_id = ? " +
            "order by w.created_at ", nativeQuery = true)
    ArrayList<Wishlist> findAllByUserOrderByCreatedAt(Long userId);

    ArrayList<Wishlist> findAllByBookingUser(Long bookingUserId);

    ArrayList<Wishlist> findAllByUserAndIsDoneTrue(User user);

    @Query(value = "select * from wishlist w " +
            "left join wishlist_private wp on w.wishlist_id = wp.wishlist_id " +
            "where (w.is_done = 'false' or w.is_done is null) and wp.user_id = ? and w.user_id = ? ", nativeQuery = true)
    ArrayList<Wishlist> findPrivateWishesByUser(Long userIdPrivate, Long userIdSub);

    @Query(value = "select count(w.wishlist_id) from wishlist w where w.is_booking = 'true' and w.user_id = ? and (w.is_done = 'false' or w.is_done is null) ", nativeQuery = true)
    Long countAllByUserAndIsBookingTrue(Long userId);

    @Query(value = "select count(w.wishlist_id) from wishlist w where w.user_id = ? and (w.is_done = 'false' or w.is_done is null) ", nativeQuery = true)
    Long countAllByUser(Long userId);

    @Query(value = "select * from wishlist w " +
            "where (w.is_secret = 'false' or w.is_secret is null) and (w.is_done = 'false' or w.is_done is null) and w.user_id = ? " +
            "order by w.created_at ", nativeQuery = true)
    ArrayList<Wishlist> findAllByUserAndSecretIsFalse(User user);

    @Query(value = "select * from wishlist w " +
            "left join wishlist_private wp on w.wishlist_id = wp.wishlist_id " +
            "where w.user_id = ? and w.is_secret = true ", nativeQuery = true)
    ArrayList<Wishlist> whoSeesSecretWishes(Long userId);

}
