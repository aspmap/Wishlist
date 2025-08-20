package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.entity.Subscriptions;
import run.itlife.repository.SubscriptionsRepository;

import java.util.List;

@Service
@Transactional
public class SubscriptionsServiceImpl implements SubscriptionsService {
    private final SubscriptionsRepository subscriptionsRepository;
    private final UserService userService;

    @Autowired
    public SubscriptionsServiceImpl(SubscriptionsRepository subscriptionsRepository, UserService userService) {
        this.subscriptionsRepository = subscriptionsRepository;
        this.userService = userService;
    }

    @Override
    public void createSub(String userSub) {
        Subscriptions subscriptions = new Subscriptions();
        subscriptions.setCurrentUserId(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        //subscriptions.setCurrentUserId(userService.findByUsername(getCurrentUserDetails().getUsername()));
        subscriptions.setSubUserId(userService.findByUsername(userSub));
        subscriptionsRepository.save(subscriptions);
    }

    @Override
    public void deleteSubscribeLong(long currentUserId, long subUserId) {
        subscriptionsRepository.deleteSubscribeLong(currentUserId, subUserId);
    }

    @Override
    public List<Subscriptions> findSubscribes(String username) {
        return subscriptionsRepository.findSubscribes(username);
    }

    @Override
    public List<Subscriptions> findSubscribers(String username) {
        List<Subscriptions> subscriptions = subscriptionsRepository.findSubscribers(username);
        for (Subscriptions s : subscriptions) {
            s.getCurrentUserId().getFirstname().length();
        }
        return subscriptions;
    }

    @Override
    public int isSubscribe(String currentUsername, String subUsername) {
        return subscriptionsRepository.isSubscribe(currentUsername, subUsername);
    }

    @Override
    public int countSubscribe(String currentUsername) {
        return subscriptionsRepository.countSubscribe(currentUsername);
    }

    @Override
    public int countSubscribers(String currentUsername) {
        return subscriptionsRepository.countSubscribers(currentUsername);
    }
}
