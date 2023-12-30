package com.example.mywebapp;

import com.example.mywebapp.user.User;
import com.example.mywebapp.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("elvin@gmail.com");
        user.setPassword("12345");
        user.setFirstName("Elvin");
        user.setLastName("Huseynov");
        User saveUser = repository.save(user);

        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser.getId()).isGreaterThan(0);


    }

    @Test
    public void testAllList() {
        Iterable<User> users = repository.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<User> optionalUser = repository.findById(userId);
        User user = optionalUser.get();
        user.setPassword("12345");
        repository.save(user);

        User updatedUser = repository.findById(userId).get();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("12345");
    }

    @Test
    public void testGet() {
        Integer userId = 1;
        Optional<User> optionalUser = repository.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Integer userId = 2;
        repository.deleteById(userId);
        Optional<User> optionalUser = repository.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
