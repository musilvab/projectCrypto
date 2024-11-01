package com.crypto.Project.Crypto.service;

import com.crypto.Project.Crypto.model.User;
import com.crypto.Project.Crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(long id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    public User exists(User user){
        List<User> users = userRepository.findAll();
        User found = null;
        for (User u: users){
            if (Objects.equals(u.getUsername(), user.getUsername()) && Objects.equals(u.getPassword(), user.getPassword())){
                found = u;
            }
        }
        return found;
    }
}
