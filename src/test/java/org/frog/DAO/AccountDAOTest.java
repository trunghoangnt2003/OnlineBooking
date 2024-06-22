package org.frog.DAO;

import org.frog.model.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AccountDAOTest {
    @DisplayName("Check exits id account")
    @Test
    void testCheckExitsIdAccount(){
        String id = "1525cfd4-fbb9-4667-9d00-2a54582a2f28";
        AccountDAO accountDAO = new AccountDAO();
        assertFalse(accountDAO.checkExitsId(id));

    }
    @DisplayName("Check login")
    @Test
    void testCheckLogin(){
        String username = "huynmhe";
        String password = "3zpvmsMTTTetmbyzvcUa3NjSGNU=";
        AccountDAO accountDAO = new AccountDAO();
        assertInstanceOf(Account.class,accountDAO.getLogin(username,password));
    }
}
