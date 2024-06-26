package kr.or.ddit.batch.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class SimpleTask {
	private Logger logger = LoggerFactory.getLogger(SimpleTask.class);
	
	public void simpleTask() {
		logger.debug("simpleTask");
	}
	
	@Scheduled(cron="*/3 * * * * *")
	public void simpleTaskAnno() {
		logger.debug("simpleTask anno");
	}
	
	

}
