package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.UUID;

@DataJpaTest
public class UserEntityIntegrationTest {
    @Autowired
    private TestEntityManager testEntityManager;

    private UserEntity userEntity;
    private UserEntity doubleIdUser;

    @BeforeEach
    void setup() {
        userEntity = new UserEntity();
        userEntity.setLastName("Scalco");
        userEntity.setEmail("test@test.it");
        userEntity.setEncryptedPassword("12345678");
        doubleIdUser = new UserEntity();
        doubleIdUser.setFirstName("Frog");
        doubleIdUser.setLastName("Mumford");
        doubleIdUser.setEmail("son@son.com");
        doubleIdUser.setEncryptedPassword("12345678");
    }

    @Test
    void testUserEntity_whenValidUserDetailsProvided_shouldPersistUser() {
        userEntity.setFirstName("Iliana");
        userEntity.setUserId(UUID.randomUUID().toString());
        UserEntity storedUser = testEntityManager.persistAndFlush((userEntity));

        Assertions.assertTrue(storedUser.getId() > 0);
        Assertions.assertEquals(userEntity.getUserId(), storedUser.getUserId());
        Assertions.assertEquals(userEntity.getLastName(), storedUser.getLastName());
        Assertions.assertEquals(userEntity.getFirstName(), storedUser.getFirstName());
        Assertions.assertEquals(userEntity.getEmail(), storedUser.getEmail());
        Assertions.assertEquals(userEntity.getEncryptedPassword(), storedUser.getEncryptedPassword());

    }

    @Test
    void testUserEntity_whenFirstNameIsTooLong_throwsException() {
        userEntity.setFirstName("qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnm");
        userEntity.setUserId(UUID.randomUUID().toString());

        Assertions.assertThrows(PersistenceException.class, () -> {
            testEntityManager.persistAndFlush(userEntity);
        }, "should throw exception!");
    }

    @Test
    void testUserEntity_whenUserIdExists_throwsException() {
        userEntity.setFirstName("Glen");
        userEntity.setUserId("54e6ef4a-aded-489b-a38c-4f3273beea69");
        doubleIdUser.setUserId("54e6ef4a-aded-489b-a38c-4f3273beea69");

        UserEntity storedUser = testEntityManager.persistAndFlush((userEntity));

        Assertions.assertThrows(PersistenceException.class, () -> {
            testEntityManager.persistAndFlush(doubleIdUser);
        }, "should throw exception!");
    }

}
