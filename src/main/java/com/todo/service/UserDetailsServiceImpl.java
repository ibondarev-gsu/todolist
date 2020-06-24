package com.todo.service;


import com.todo.model.Role;
import com.todo.model.User;
import com.todo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        final User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + email));

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return  org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(user.isEnabled())
                .accountExpired(accountNonExpired)
                .credentialsExpired(credentialsNonExpired)
                .accountLocked(accountNonLocked)
                .authorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_" + "USER")))
                .build();
    }

    private static List<GrantedAuthority> getAuthorities (Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }


//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            return new org.springframework.security.core.userdetails.User(
//                    " ", " ", getAuthorities(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
//        } else {
//            return new org.springframework.security.core.userdetails.User(
//                    user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
//        }
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
//        return getGrantedAuthorities(getPrivileges(roles));
//    }
//
//    private List<String> getPrivileges(Collection<Role> roles) {
//
//        List<String> privileges = new ArrayList<>();
//        List<Privilege> collection = new ArrayList<>();
//        for (Role role : roles) {
//            collection.addAll(role.getPrivileges());
//        }
//        for (Privilege item : collection) {
//            privileges.add(item.getName());
//        }
//        return privileges;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }
}
