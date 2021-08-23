package com.springsecurity.Service;

import com.springsecurity.Entity.Role;
import com.springsecurity.Entity.User;
import com.springsecurity.Repository.RoleRepository;
import com.springsecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserService() {
        super();
    }

    public User findUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName){
        return this.userRepository.findByUserName(userName);
    }

    public User saveUser(User user){
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole=this.roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return this.userRepository.save(user);
    }
}
