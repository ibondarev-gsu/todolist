package com.todo.util;

import com.todo.model.Role;
import com.todo.model.User;
import com.todo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

//@Slf4j
//@Component
//@AllArgsConstructor
public class SetupDataLoader
//        implements ApplicationListener<ContextRefreshedEvent>
{

//    boolean alreadySetup = true;
//
//    private final UserRepository userRepository;
//
//    private final RoleRepository roleRepository;
//
//    private final PrivilegeRepository privilegeRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    public SetupDataLoader(UserRepository userRepository,
//                           RoleRepository roleRepository,
//                           PrivilegeRepository privilegeRepository,
//                           PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.privilegeRepository = privilegeRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
//    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {


//        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
//        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

//        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
//        List<Privilege> userPrivileges = Arrays.asList(readPrivilege);

//        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
//        Role userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);

//        Role adminRole = roleRepository.findByName("ROLE_ADMIN");


//        log.debug("{}", user);

//        userRepository.save(User.builder()
//                .username("Admin")
//                .password("Admin")
//                .email("vania.bondarev@yandex.ru")
//                .enabled(true)
//                .roles(Collections.singleton(Role.ADMIN))
//                .build());

//        alreadySetup = true;
    }

//    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
//        Role role = roleRepository.findByName(name);
//        if (role == null) {
//            role = new Role();
//            role.setName(name);
//            role.setPrivileges(privileges);
//            roleRepository.save(role);
//        }
//        return role;
//    }
//
//
//    private Privilege createPrivilegeIfNotFound(final String name) {
//        Privilege privilege = privilegeRepository.findByName(name);
//        if (privilege == null){
//            privilege = new Privilege();
//            privilege.setName(name);
//            privilegeRepository.save(privilege);
//        }
//        return privilege;
//    }

}
