package run.itlife.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;
import java.util.ArrayList;
import java.util.List;

//Интерфейс, отвечающий за логику создания пользователей, поиск пользователей
public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    List<User> findAll();
    List<User> findAllUsers();
    List<User> findActiveUsers();
    void create(User user);
    void createGoogleUser(User user);
    void update(UserDto userDto);
    void updateAdmin(UserDto userDto);
    void checkAuthority(long userId);
    List<User> findUsersOnly();
    ArrayList<UserDto> findUsersOnlyKey(String currentUsername);
    List<User> findUsers(String substring);
    List<User> findUsersForPermission(String substring);
    List<User> findGoogleUsers(String substring);
    int countSearchUsers(String substring);
    int countSearchGoogleUsers(String substring);
    void deleteProfile(String user);
    boolean isClosedProfile(String username);
    ArrayList<User> findUsersByDialogId(Long dialogId);
}