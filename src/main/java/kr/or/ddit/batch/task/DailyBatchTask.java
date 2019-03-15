package kr.or.ddit.batch.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.or.ddit.batch.service.IBatchService;

@Component
public class DailyBatchTask {
	
	@Resource(name="batchService")
	private IBatchService batchService;
	
	@Scheduled(cron="* * 1 1 * *")	// 매달 1일 1시에 실행
	public void dailyBatch(){
		batchService.dailyBatchYm(new SimpleDateFormat("yyyyMM").format(new Date()));
		
	}
}
