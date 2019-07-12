package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.exceptions.NotConfirmedException;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.exceptions.UserAlreadyExistsAuthenticationException;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceMockTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void init() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void addTest() {
        User newUser = new User();
        newUser.setFirstName("Danylo");
        newUser.setLastName("Lototskyi");
        newUser.setUsername("Danko");
        newUser.setPassword("123123");
        newUser.setEmail("lenel15@ukr.net");
        newUser.setEmailSubscription(true);
        newUser.setIsActivated(true);

        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstName("Danylo");
        expectedUser.setLastName("Lototskyi");
        expectedUser.setUsername("Danko");
        expectedUser.setPassword("123123");
        expectedUser.setEmail("lenel15@ukr.net");
        expectedUser.setEmailSubscription(true);
        expectedUser.setIsActivated(true);

        when(userRepository.save(newUser)).thenReturn(expectedUser);

        User resultUser = userService.add(newUser);

        assertNotEquals("New param2 should not be 0", Integer.valueOf(0), resultUser.getId());
        assertEquals("Names should be the same", resultUser.getUsername(), expectedUser.getUsername());

    }

    @Test(expected = UserAlreadyExistsAuthenticationException.class)
    public void addUsernameAlreadyExistsTest() {
        when(userRepository.existsByUsername(any())).thenReturn(true);
        userService.add(new User());
    }

    @Test(expected = UserAlreadyExistsAuthenticationException.class)
    public void addEmailAlreadyExistsTest() {
        when(userRepository.existsByEmail(any())).thenReturn(true);
        userService.add(new User());
    }

    @Test
    public void updateTest() {
        User newUser = new User();
        newUser.setId(1);
        newUser.setFirstName("Danylo");
        newUser.setLastName("Lototskyi");
        newUser.setUsername("Test");
        newUser.setPassword("123123");
        newUser.setEmail("test@gmail.com");
        newUser.setEmailSubscription(true);
        newUser.setIsActivated(true);

        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstName("Danylo");
        expectedUser.setLastName("Lototskyi");
        expectedUser.setUsername("Test");
        expectedUser.setPassword("123123");
        expectedUser.setEmail("test@gmail.com");
        expectedUser.setEmailSubscription(true);
        expectedUser.setIsActivated(true);

        when(userRepository.saveAndFlush(newUser)).thenReturn(expectedUser);
        when(userRepository.findByUsername("Test")).thenReturn(Optional.of(newUser));

        User resultUser = userService.update(newUser);

        assertNotEquals("New param2 should not be 0", Integer.valueOf(0), resultUser.getId());
        assertEquals("Names should be the same", resultUser.getUsername(), expectedUser.getUsername());

    }

    @Test
    public void findByIdTest() {
        Integer id = 1;

        User expectedUser = new User();
        expectedUser.setId(1);

        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

        User resultUser = userService.findById(id);

        assertEquals("IDs should be the same", id, resultUser.getId());
        assertNotNull("Username should be not null", resultUser.getId());
    }

    @Test(expected = NotFoundException.class)
    public void findByIdNotFoundTest() {
        when(userRepository.findById(any())).thenThrow(NotFoundException.class);
        userService.findById(1);
    }

    @Test
    public void findByUsernameTest() {
        String username = "Test";

        User expectedUser = new User();
        expectedUser.setUsername("Test");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        User resultUser = userService.findByUsername(username);

        assertEquals("Usernames should be the same", username, resultUser.getUsername());
        assertNotNull("Username should be not null", resultUser.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByUsernameNotFoundTest() {
        when(userRepository.findByUsername(any())).thenThrow(UsernameNotFoundException.class);
        userService.findByUsername("Test");
    }

    @Test
    public void existsByUsernameTest() {
        String username = "Test";

        User expectedUser = new User();
        expectedUser.setUsername("Test");

        when(userRepository.existsByUsername(username)).thenReturn(true);

        Boolean findResult = userService.existsByUsername(username);

        assertTrue("Result should be the same", findResult);
    }

    @Test
    public void existsByEmailTest() {
        String email = "test@gmail.com";

        User expectedUser = new User();
        expectedUser.setUsername("test@gmail.com");

        when(userRepository.existsByEmail(email)).thenReturn(true);

        Boolean findResult = userService.existsByEmail(email);

        assertTrue("Result should be the same", findResult);
    }

    @Test
    public void findByEmailTest() {
        String email = "test@gmail.com";

        User expectedUser = new User();
        expectedUser.setEmail("test@gmail.com");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        User resultUser = userService.findByEmail(email);

        assertEquals("Emails should be the same", email, resultUser.getEmail());
    }

    @Test(expected = NotFoundException.class)
    public void findByEmailNotFoundTest() {
        when(userRepository.findById(any())).thenThrow(NotFoundException.class);
        userService.findByEmail("test@gmail.com");
    }

    @Test
    public void isUserPrivilegedByRoleTest() {
        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);

        Integer id = 1;

        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setRoles(roleSet);

        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

        boolean result = userService.isUserPrivilegedByRole(id, RoleName.ROLE_USER);

        assertTrue("Result should be the same", result);

        result = userService.isUserPrivilegedByRole(id, RoleName.ROLE_MODERATOR);

        assertFalse("Result should be the same", result);
    }

    @Test
    public void isUserActivatedTest() {
        String username = "Test";

        User expectedUser = new User();
        expectedUser.setUsername("Test");
        expectedUser.setIsActivated(true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        boolean result = userService.isUserActivated(username);

        assertTrue("Result should be the same", result);
    }

    @Test(expected = NotConfirmedException.class)
    public void isUserActivatedNotConfirmedTest() {
        String username = "Test";

        User expectedUser = new User();
        expectedUser.setUsername("Test");
        expectedUser.setIsActivated(false);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        userService.isUserActivated(username);
    }
}
