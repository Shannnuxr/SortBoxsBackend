package com.example.SortBoxs.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.SortBoxs.entitys.User;
import com.example.SortBoxs.models.UserDetailsImpl;
import com.example.SortBoxs.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository ;
    
    @Autowired
    private  PasswordEncoder passwordEncoder;

  
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        
//        log.info("loading user by username: {}", username);

        User user = userRepository
                .findByEmail(username) // find the user by email
                .orElseThrow(() -> new UsernameNotFoundException("user not found")); // if the user is not found, throw an exception
        return new UserDetailsImpl(user);
    }

   
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }


   
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("no user with email: " + email + " found"));
    }

   
    public User saveUser(User user) throws Exception {

            String password = user.getPassword(); // get the password that the user have provided
            user.setPassword(passwordEncoder.encode(password)); // encode the password

           /* Set<Role> roles = user.getRoles(); // get the roles that the user have provided

            user.setRoles(roles); // set the roles*/

                  User savedUser =  userRepository.save(user); // save the user
        
        return savedUser;
    }

    
    public String updatePassword(String email, String password) throws Exception {

        if (emailExists(email)) { // check if the email exists
            User user = findUserByEmail(email); // get the user

            if (password.equals(password)) { // check if the password and the confirmPassword matches.
                user.setPassword(passwordEncoder.encode(password)); // encode the new password
                userRepository.save(user); // save the user
                return "Password updated";
            } else { // if the password doesn't match, throw an exception
                throw new Exception();
            }
        } else { // if the email doesn't exist, throw an exception
            throw new UsernameNotFoundException("no user with email: " + email + " found");
        }
    }

   
    public User validateCredentials(String email, String password) {

        User user = userRepository
                .findByEmail(email) // get the user by email
                .orElseThrow(
                        // if the user doesn't exist, throw an exception
                        () -> new BadCredentialsException("Invalid credentials")
                );

        // check if the password matches
        if ( !passwordEncoder.matches(password, user.getPassword())){
        		
        	throw new BadCredentialsException("Invalid credentials");
        }
            

        return user;
    }
    
    public void deleteUser(Long id) {
        // Implement the logic to delete a user by ID
        userRepository.deleteById(id);
    }

    
    
    public void enableUser(String email) {
        // get the user by email
        User user = findUserByEmail(email);
        // enable the user
        user.setEnabled(true);
        // save the user
        userRepository.save(user);
    }

}
