package module2.lesson4.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<User> users = new ArrayList<>(Arrays.asList(
                new User("user1", passwordEncoder.encode("password1"), new ArrayList<>()),
                new User("user2", passwordEncoder.encode("password2"), new ArrayList<>()),
                new User("user3", passwordEncoder.encode("password3"), new ArrayList<>()),
                new User("user4", passwordEncoder.encode("password4"), new ArrayList<>())
                ));
        for (User user : users) {
            if (user.getUsername().equals(userName))
                return user;
        }
        throw new UsernameNotFoundException("User not found");
    }
}
