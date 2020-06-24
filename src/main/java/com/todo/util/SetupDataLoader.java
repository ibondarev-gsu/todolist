package com.todo.util;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
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
//    public void onApplicationEvent(final ContextRefreshedEvent event) {
//        if (alreadySetup) return;
//
//        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
//        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
//
//        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
//        List<Privilege> userPrivileges = Arrays.asList(readPrivilege);
//
//        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
//        Role userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);
//
////        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
//
//        User user = new User();
//        user.setUsername("Admin");
//        user.setPassword("Admin");
//        user.setEmail("test@test.com");
//        user.setRoles(Arrays.asList(adminRole));
////        user.setEnabled(true);
//
//        log.debug("{}", user);
//
//        userRepository.save(user);
//
//        alreadySetup = true;
//    }
//
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
