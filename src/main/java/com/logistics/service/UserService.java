package com.logistics.service;

import com.logistics.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private User currentUser;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Transactional
    public User CreateAndSaveUser(String name, String surname)
    {
        var user = new User(name, surname);

        var InitFinances = new Finances();
        user.setFinances(InitFinances);

        return userRepository.save(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public void saveProgress()
    {
        if(currentUser != null) userRepository.save(currentUser);
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public Finances getCurrentUserFinances()
    {
        if(currentUser == null) throw new IllegalStateException("The user doesn't exist!");
        else return currentUser.getFinances();
    }

    public List<Vehicle> getCurrentUserFleet()
    {
        if(currentUser == null) throw new IllegalStateException("The user doesn't exist!");
        else return currentUser.getFleet();
    }

    public List<Cargo> getCurrentUserWarehouse()
    {
        if(currentUser == null) throw new IllegalStateException("The user doesn't exist!");
        else return  currentUser.getWarehouse();
    }
}
