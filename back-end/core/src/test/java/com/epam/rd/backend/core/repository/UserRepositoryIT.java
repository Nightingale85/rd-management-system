package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreConfig.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
@Transactional
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;
    private User user;
    private static final String EMAIL = "admin@mail.com";

    @Before
    public void init() {
        user = TempDataFactory.getUser(EMAIL, Collections.singleton(UserRole.MANAGER));
    }

    @Test
    public void checked_rows_count_in_users_table() {
        //GIVEN
        final long zeroRowsCount = 1L;
        long currentRowsCount;
        //WHEN
        userRepository.save(user);
        currentRowsCount = userRepository.count();
        //THEN
        assertEquals(zeroRowsCount, currentRowsCount);
    }

    @Test
    public void should_get_user_by_email() {
        //GIVEN
        userRepository.save(user);
        //WHEN
        final User currentUser = userRepository.findUserByEmail(EMAIL);
        //THEN
        assertTrue(currentUser.getRoles().size() == 1);
        assertEquals(currentUser.getName(), "Name");
    }
    @Test
    public void should_delete_user_by_email() {
        //GIVEN
        userRepository.save(user);
        //WHEN
        userRepository.deleteUserByEmail(EMAIL);
        //THEN
        User deletedUser = userRepository.findUserByEmail(EMAIL);
        assertNull(deletedUser);
    }
    @Test
    public void should_return_list_users_by_role() {
        //GIVEN
        final Set<UserRole> roles = Collections.singleton(UserRole.MANAGER);
        User user2 = TempDataFactory.getUser("marvin@mail.com", Collections.singleton(UserRole.MENTEE));
        userRepository.save(user);
        userRepository.save(user2);
        //WHEN
        final List<User> users = userRepository.findUsersByRoles(roles);
        //THEN
        assertTrue(users.size() == 1);
    }

}