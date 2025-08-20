package run.itlife.service;

public interface WishlistPrivateService {
    void deletePermissions(Long id);
    Integer findAlreadyPermissions(Long id, Long userId);
}
