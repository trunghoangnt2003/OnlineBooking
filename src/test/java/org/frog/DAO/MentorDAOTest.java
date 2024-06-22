package org.frog.DAO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MentorDAOTest {
    @DisplayName("Test Total Mentor ")
    @Test
    void testTotalMentor(){
        MentorDAO mentorDAO = new MentorDAO();
        assertEquals(9,mentorDAO.totalMentorAndPagingAndSearch(1,""));
    }

    @Test
    void selectAll() {
    }
}
