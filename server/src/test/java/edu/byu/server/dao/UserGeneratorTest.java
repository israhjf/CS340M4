package edu.byu.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import edu.byu.model.domain.User;

import static org.junit.jupiter.api.Assertions.*;

class UserGeneratorTest {

    @Test
    void testGenerateUsers_count() {

        List<User> users = UserGenerator.getInstance().generateUsers(10);
        Assertions.assertEquals(10, users.size());

        users = UserGenerator.getInstance().generateUsers(2);
        Assertions.assertEquals(2, users.size());
    }
}