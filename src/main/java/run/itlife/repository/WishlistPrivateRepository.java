package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.WishlistPrivate;

import javax.transaction.Transactional;

public interface WishlistPrivateRepository extends JpaRepository<WishlistPrivate, Long> {
    @Query(value = "select count(*) from wishlist_private wp " +
            "where wp.wishlist_id = ? and wp.user_id = ? ", nativeQuery = true)
    Integer findAlreadyPermissions(Long id, Long userId);

    @Modifying
    @Transactional
    @Query(value = "update wishlist " +
            "set is_booking = false, booking_user = null " +
            "where wishlist.wishlist_id = (SELECT wishlist_id FROM wishlist_private WHERE wishlist.wishlist_id = wishlist_private.wishlist_id " +
            "and wishlist_private.private_id = ? and wishlist.booking_user = wishlist_private.user_id) ", nativeQuery = true)
    Integer deleteBooking(Long id);
}