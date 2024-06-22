package org.frog.utility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA1Test {
    @DisplayName("SHA1")
    @Test
    void testToSHA1() {
        String password = "123456789";
        assertEquals("3zpvmsMTTTetmbyzvcUa3NjSGNU=",SHA1.toSHA1(password));
    }
}