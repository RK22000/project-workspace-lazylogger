package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import application.model.Log;
import application.model.Logger;
import application.model.PeerMentorLog;

class LoggerTest {

	@Test
	void testAdd() {
		Logger logger1 = new Logger();
		List<String> validJobs = PeerMentorLog.getValidActivities();
		Log log1 = PeerMentorLog.createLog(validJobs.get(0), 6, "comment 1");
		logger1.add(log1);
		
		assertEquals(1, logger1.size(), "Actual size = " + logger1.size());
		
		assertEquals("comment 1", logger1.get(0).getComment());
		assertEquals(6, logger1.get(0).getDuration());
		
		
		logger1.add(PeerMentorLog.createLog(validJobs.get(0), 9, "comment 2"));
		
		assertEquals(1, logger1.size(), "Actual size of logger1 = " + logger1.size());
		assertEquals("comment 1\ncomment 2", logger1.get(0).getComment());
		assertEquals(15, logger1.get(0).getDuration());
		
		logger1.add(PeerMentorLog.createLog(validJobs.get(2), 10, "comment 3"));
		
		assertEquals(2, logger1.size(), "Actual size of logger1 = " + logger1.size());
		assertEquals("comment 3", logger1.get(1).getComment());
		assertEquals(10, logger1.get(1).getDuration());
		
		System.out.println(logger1);
	}

}
