package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.security.dto.GoogleUserData;
import com.softserve.academy.studhub.security.services.GoogleVerifierService;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleVerifierServiceImplTest {

    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder encoder;

    GoogleVerifierService verifierService;

    @Before
    public void initialize() {
        verifierService = new GoogleVerifierServiceImpl(
                "820600559774-rogh42iso9644ohsprs6g5grb828v20n.apps.googleusercontent.com",
                userService, roleService, encoder);
    }

    @After
    public void finalize() {
        verifierService = null;
    }

    /*//TODO: FIX
    @Test
    public void isValidTokenPositive() {
        Assert.assertTrue(verifierService.isValidToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IjExOGRmMjU0YjgzNzE4OWQxYmMyYmU5NjUwYTgyMTEyYzAwZGY1YTQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiODIwNjAwNTU5Nzc0LXJvZ2g0Mmlzbzk2NDRvaHNwcnM2ZzVncmI4Mjh2MjBuLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiODIwNjAwNTU5Nzc0LXJvZ2g0Mmlzbzk2NDRvaHNwcnM2ZzVncmI4Mjh2MjBuLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTAxMzI0NTYzNTk3MzE0MDM0NDI4IiwiZW1haWwiOiJhdmFzaGNoZW5vY2tAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJpNk9NMzVBSzFNNmJaSlpfQWRsMkd3IiwibmFtZSI6IkFuZHJldyBWYXNoY2hlbm9rIiwicGljdHVyZSI6Imh0dHBzOi8vbGg0Lmdvb2dsZXVzZXJjb250ZW50LmNvbS8tNlZZRDltY3NKYk0vQUFBQUFBQUFBQUkvQUFBQUFBQUFCSzAvTlpTeGxBYkRZLUUvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IkFuZHJldyIsImZhbWlseV9uYW1lIjoiVmFzaGNoZW5vayIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNTYyMDY4MDIzLCJleHAiOjE1NjIwNzE2MjMsImp0aSI6ImMwMTUzZWRkM2MwOTVkYTIxNWY0M2RiMWUxZjkzODNkNTgzODZjYWEifQ.C-2XAsvbTrVeuyVLWxiIjBMsyUwkxnKDvUM2GvDpHJfVy3JZxiq7SsfwJST335jmcC2-JjIURkcY92_2cm4DIswUJzzLwAim_GvKt47kl1ohWxqLSGxiP0lN9aF436v8jYC6hezswgwzrMHX4OGEkaSr-_dHMhw959rdov0XcfVWfGzcp_RZIW4Fl9y8wqKjKyV5pZ59i04y2gxnXeKa2EJV5KhXG_IQlfadYSoHL0c0J_9M-VcmQVzwVD_i4hm1MylYHr1nkqbrnQwAoLVciCEjL6YCyLjS3H6aUdd_h2tXop5yy2RcxjAGXTVU-LatkpT6t8PR_0BjNPvmynWd9A"));
    }*/

    @Test(expected = IllegalArgumentException.class)
    public void isValidTokenNegative() {
        Assert.assertFalse(verifierService.isValidToken("123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidTokenNull() {
        verifierService.isValidToken(null);
    }


    /*// TODO: FIX
    @Test
    public void authenticateUserPositive() {
        String email = "avashchenock@gmail.com";
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

        Assert.assertEquals(verifierService.authenticateUser(userData), loginForm);
    }*/

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