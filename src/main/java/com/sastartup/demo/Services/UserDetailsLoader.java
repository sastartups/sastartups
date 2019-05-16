package com.sastartup.demo.Services;

import com.sastartup.demo.models.User;
import com.sastartup.demo.models.UserWithRoles;
import com.sastartup.demo.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

    @Service
    public class UserDetailsLoader implements UserDetailsService {
        private final UserRepo users;

        public UserDetailsLoader(UserRepo users) {
            this.users = users;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = users.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("No user found for " + username);
            }

            return new UserWithRoles(user);
        }
    }

