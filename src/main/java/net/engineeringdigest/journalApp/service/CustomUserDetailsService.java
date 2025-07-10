package net.engineeringdigest.journalApp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import net.engineeringdigest.journalApp.repo.UserRepository;
import net.engineeringdigest.journalApp.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired  
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            builder.roles(user.getRoles().toArray(new String[0]));
            return builder.build();
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
