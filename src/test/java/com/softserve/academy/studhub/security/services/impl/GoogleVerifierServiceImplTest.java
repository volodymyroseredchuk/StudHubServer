package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.security.dto.GoogleUserData;
import com.softserve.academy.studhub.security.services.GoogleVerifierService;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class GoogleVerifierServiceImplTest {

    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    GoogleVerifierService verifierService = new GoogleVerifierServiceImpl(
            "820600559774-rogh42iso9644ohsprs6g5grb828v20n.apps.googleusercontent.com",
            userService, roleService, encoder);

    @After
    public void finalize() {
        verifierService = null;
    }

    // TODO: FIX
    @Test
    public void isValidTokenPositive() {
        Assert.assertTrue(verifierService.isValidToken("eyJhbGciOiJSUzI1NiIsImtpZCI6ImFmZGU4MGViMWVkZjlmM2JmNDQ4NmRkODc3YzM0YmE0NmFmYmJhMWYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiODIwNjAwNTU5Nzc0LXJvZ2g0Mmlzbzk2NDRvaHNwcnM2ZzVncmI4Mjh2MjBuLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiODIwNjAwNTU5Nzc0LXJvZ2g0Mmlzbzk2NDRvaHNwcnM2ZzVncmI4Mjh2MjBuLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTAxMzI0NTYzNTk3MzE0MDM0NDI4IiwiZW1haWwiOiJhdmFzaGNoZW5vY2tAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJFUXoyQnM2amlhRHFNZ3BScU5aa2xBIiwibmFtZSI6IkFuZHJldyBWYXNoY2hlbm9rIiwicGljdHVyZSI6Imh0dHBzOi8vbGg0Lmdvb2dsZXVzZXJjb250ZW50LmNvbS8tNlZZRDltY3NKYk0vQUFBQUFBQUFBQUkvQUFBQUFBQUFCSzAvTlpTeGxBYkRZLUUvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IkFuZHJldyIsImZhbWlseV9uYW1lIjoiVmFzaGNoZW5vayIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNTYyMDAxMDE1LCJleHAiOjE1NjIwMDQ2MTUsImp0aSI6IjY3OTVjNjNhMDdhNjQ0ODVlMmM2MGE1NjIwZGQxYWVmNGNiMTMwY2YifQ.KVVUSRhtHPGkT0argWwY-OxzOZ2jmrHnXLk5hd2oVHvhbzpukDuwEy2VsDlJR2gbRtqc0uGOVPQ1KWgFgSOs7sPbG7kMaA2EKvxIK54F5yRi4ffdu4bv2Iv3fS6XB-kScG2C4yKSkmYK1SdptxI70hKVXGiNePAT5ahz8AJUQH-1aYDp9M0jkZRb1aFPqZA-3TU8OvulrP9dedWnSaY2cfItJTXmgfPhEInXvGDVz3Zlqiky41mD62Q5uwRlHuBo4zHuV86nHn9Q8LVmJb-DsZFWHUj2nmwXRTV9ox5gpb7vHWL4g8OwJtYQlkOqUREgkJTOJsxD0nelMXM4FXb3Pw"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidTokenNegative() {
        Assert.assertFalse(verifierService.isValidToken("123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidTokenNull() {
        verifierService.isValidToken(null);
    }


    // TODO: FIX
    @Test
    public void authenticateUserPositive() {
        /*String email = "avashchenock@gmail.com";
        String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImFmZGU4MGViMWVkZjlmM2JmNDQ4NmRkODc3YzM0YmE0NmFmYmJhMWYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiODIwNjAwNTU5Nzc0LXJvZ2g0Mmlzbzk2NDRvaHNwcnM2ZzVncmI4Mjh2MjBuLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiODIwNjAwNTU5Nzc0LXJvZ2g0Mmlzbzk2NDRvaHNwcnM2ZzVncmI4Mjh2MjBuLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTAxMzI0NTYzNTk3MzE0MDM0NDI4IiwiZW1haWwiOiJhdmFzaGNoZW5vY2tAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJFUXoyQnM2amlhRHFNZ3BScU5aa2xBIiwibmFtZSI6IkFuZHJldyBWYXNoY2hlbm9rIiwicGljdHVyZSI6Imh0dHBzOi8vbGg0Lmdvb2dsZXVzZXJjb250ZW50LmNvbS8tNlZZRDltY3NKYk0vQUFBQUFBQUFBQUkvQUFBQUFBQUFCSzAvTlpTeGxBYkRZLUUvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IkFuZHJldyIsImZhbWlseV9uYW1lIjoiVmFzaGNoZW5vayIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNTYyMDAxMDE1LCJleHAiOjE1NjIwMDQ2MTUsImp0aSI6IjY3OTVjNjNhMDdhNjQ0ODVlMmM2MGE1NjIwZGQxYWVmNGNiMTMwY2YifQ.KVVUSRhtHPGkT0argWwY-OxzOZ2jmrHnXLk5hd2oVHvhbzpukDuwEy2VsDlJR2gbRtqc0uGOVPQ1KWgFgSOs7sPbG7kMaA2EKvxIK54F5yRi4ffdu4bv2Iv3fS6XB-kScG2C4yKSkmYK1SdptxI70hKVXGiNePAT5ahz8AJUQH-1aYDp9M0jkZRb1aFPqZA-3TU8OvulrP9dedWnSaY2cfItJTXmgfPhEInXvGDVz3Zlqiky41mD62Q5uwRlHuBo4zHuV86nHn9Q8LVmJb-DsZFWHUj2nmwXRTV9ox5gpb7vHWL4g8OwJtYQlkOqUREgkJTOJsxD0nelMXM4FXb3Pw";
        GoogleUserData userData = new GoogleUserData();
        userData.setIdToken(idToken);
        userData.setEmail(email);
        userData.setId("101324563597314034428");

        User user = new User();
        user.setGooglePassword("123");
        user.setUsername("Zap");

        when(userService.existsByEmail(email)).thenReturn(true);
        when(userService.findByEmail(email)).thenReturn(user);

        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("Zap");
        loginForm.setPassword("101324563597314034428");

        Assert.assertEquals(verifierService.authenticateUser(userData), loginForm);*/
    }

    @Test(expected = IllegalArgumentException.class)
    public void authenticateUserNegative() {
        String email = "avashchenock@gmail.com";
        String idToken = "23456789..KVVUSRhtHPGkT0argWwY--kScG2C4yKSkmYK1SdptxI70hKVXGiNePAT5ahz8AJUQH-1aYDp9M0jkZRb1aFPqZA-3TU8OvulrP9dedWnSaY2cfItJTXmgfPhEInXvGDVz3Zlqiky41mD62Q5uwRlHuBo4zHuV86nHn9Q8LVmJb-DsZFWHUj2nmwXRTV9ox5gpb7vHWL4g8OwJtYQlkOqUREgkJTOJsxD0nelMXM4FXb3Pw";
        GoogleUserData userData = new GoogleUserData();
        userData.setIdToken(idToken);
        userData.setEmail(email);
        userData.setId("101324563597314034428");

        verifierService.authenticateUser(userData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void authenticateUserNull() {
        verifierService.authenticateUser(null);
    }

}