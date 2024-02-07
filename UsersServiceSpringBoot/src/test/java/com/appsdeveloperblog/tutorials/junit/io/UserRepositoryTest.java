package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    TestEntityManager testManager;

    @Autowired
    UsersRepository userRepo;

    private UserEntity userEntity;
    private UserEntity doubleIdUser;
    @BeforeEach
    void setup() {
        userEntity = new UserEntity();
        userEntity.setFirstName("Iliana");
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setLastName("Scalco");
        userEntity.setEmail("test@gmail.com");
        userEntity.setEncryptedPassword("12345678");
        testManager.persistAndFlush((userEntity));


        doubleIdUser = new UserEntity();
        doubleIdUser.setFirstName("Frog");
        doubleIdUser.setLastName("Mumford");
        doubleIdUser.setEmail("son@test.it");
        doubleIdUser.setEncryptedPassword("12345678");
        doubleIdUser.setUserId(UUID.randomUUID().toString());
        testManager.persistAndFlush(doubleIdUser);
    }
    @Test
    void testFindByEmail_whenGivenCorrectEmail_returnsUserEntity() {

        UserEntity storedUser = userRepo.findByEmail(userEntity.getEmail());

        Assertions.assertEquals(userEntity.getEmail(), storedUser.getEmail(),
                "The returned value does not match");
    }

    @Test
    void testFindByUserId_whenGivenExistingId_returnsCorrectUser() {

        UserEntity storedUser = userRepo.findByUserId(userEntity.getUserId());

        Assertions.assertEquals(userEntity.getUserId(), storedUser.getUserId(),
                "The returned value does not match");
    }

    @Test
    void testFindUserWithEmailEndsWith_whenGivenEmailDomain_returnsUsersWithGivenDomain() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Ili");
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setLastName("Scalco");
        userEntity.setEmail("test@gmail.com");
        userEntity.setEncryptedPassword("12345678");
        testManager.persistAndFlush((userEntity));

        String emailDomain = "@gmail.com";

        List<UserEntity> list = userRepo.findUserWithEmailEndingWith(emailDomain);

        Assertions.assertEquals(2, list.size(), "wrong length");
        Assertions.assertTrue(list.get(0).getEmail().endsWith(emailDomain));

    }


}
