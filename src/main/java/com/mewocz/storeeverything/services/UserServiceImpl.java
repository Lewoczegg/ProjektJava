package com.mewocz.storeeverything.services;

import com.mewocz.storeeverything.dto.UserRequest;
import com.mewocz.storeeverything.model.Role;
import com.mewocz.storeeverything.model.User;
import com.mewocz.storeeverything.repository.RoleRepository;
import com.mewocz.storeeverything.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setLogin(userRequest.getLogin());
        user.setAge(userRequest.getAge());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Role role = roleRepository.findByName("ROLE_FUSER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }


    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> findRoles() {
        return roleRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);

        User theUser;

        if (result.isPresent()) {
            theUser = result.get();
        }
        else {
            throw new RuntimeException("Did not find user id - " + id);
        }

        return theUser;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

}