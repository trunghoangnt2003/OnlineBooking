package org.frog.utility;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeHelperTest {

    @Test
    void convertDateToString() {
        String dateStr = "2024-06-19";
        dateStr = DateTimeHelp.convertDateToString(LocalDate.now());
        assertEquals(LocalDate.now().toString(), dateStr);
    }

    @Test
    void convertToTimestamp() {
        String date = "2024-06-14";
        String time = "15:30:00";
        Timestamp expectedTimestamp = Timestamp.valueOf("2024-06-14 15:30:00");

        Timestamp actualTimestamp = DateTimeHelp.convertToTimestamp(date, time);

        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    void converDayIDtoStartDate() {
        String dayID = LocalDate.now().toString();
        Timestamp expectedTimestamp = Timestamp.valueOf("2024-06-19 00:00:00");

        Timestamp actualTimestamp = DateTimeHelp.converDayIDtoStartDate(dayID);

        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    void converDayIDtoEndDate() {
        String dayID = "2024-06-14";
        Timestamp expectedTimestamp = Timestamp.valueOf("2024-06-14 23:59:59");

        Timestamp actualTimestamp = DateTimeHelp.converDayIDtoEndDate(dayID);

        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void testConvertToTimestamp_EmptyInput() {
        String date = null;
        String time = null;

        Timestamp actualTimestamp = DateTimeHelp.convertToTimestamp(date, time);

        assertNull(actualTimestamp);
    }
}