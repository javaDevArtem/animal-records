package com.reg.proc.users;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, User> userMap;
    private final AtomicLong idCounter;

    public UserService() {
        this.userMap = new ConcurrentHashMap<>();
        this.idCounter = new AtomicLong();
    }

    public User createUser(User user) {
        if (user.id() != null) {
            throw new IllegalArgumentException("Id for user should not be provided");
        }
        if (user.pets() != null && !user.pets().isEmpty()) {
            throw new IllegalArgumentException("User pets must be empty");
        }

        var id = idCounter.incrementAndGet();
        var savedUser = new User(
                id,
                user.name(),
                user.email(),
                user.age(),
                new ArrayList<>()
        );
        userMap.put(id, savedUser);
        return savedUser;
    }

    public void deleteUser(Long id) {
        userMap.remove(id);
    }

    public User updateUser(User user) {
        if (user.id() == null) {
            throw new IllegalArgumentException("No user id passed");
        }
        if (!userMap.containsKey(user.id())) {
            throw new NoSuchElementException("No such user with id=%s".formatted(user.id()));
        }
        if (userMap.containsKey(user.id())) {
            userMap.put(user.id(), user);
        }
        return user;
    }

    public User getUser(Long id) {
        if (!userMap.containsKey(id)) {
            throw new NoSuchElementException("No such user with id=%s".formatted(id));
        }
        return userMap.get(id);
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }
}
