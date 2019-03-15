package kr.or.ddit.yogult.batch;

import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import kr.or.ddit.yogult.model.CycleVo;
import kr.or.ddit.yogult.model.DailyVo;


public class YogultItemProcessor implements ItemProcessor<CycleVo, List<DailyVo>>{
	private Logger logger = LoggerFactory.getLogger(YogultItemProcessor.class);

	// spel을 통해 jobParameter를 주입 받기 위해서는 해당 bean에 스코프가 "step"이라 지정 돼 있어야 한다.
	// 형식은 정해져 있고 대괄호 안에 사용한 키를 넣는다.
	@Value("#{jobParameters[ym]}")	// spring el
	private String ym;
	
	@Override
	public List<DailyVo> process(CycleVo cycleVo) throws Exception {
		logger.debug("process ym : {} ", ym);
		
		//String ym = "201903";
		// 해당 년,월의 시작일자 : 1일
		// 해당 년,월의 종료일자 : 28, 29, 30, 31
		
		// 1번 고객이 100 제품을 2(월요일)요일에 3개를 먹는다
		// 1 100 2 3
		
		// 1 20190304 3
		// 1 20190311 3
		// 1 20190318 3
		// 1 20190325 3
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date stDate = sdf.parse(ym + "01");	// 시작일자
		Calendar cal = Calendar.getInstance();
		cal.setTime(stDate);
		
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);	// 해당 월의 마지막 일자
		Date edDate = sdf.parse(ym + lastDay);	// 해당 년,월 + 마지막 일
		
		// 시작일자, 종료일자
		List<DailyVo> dailyVoList = new ArrayList<DailyVo>();
		while(cal.getTimeInMillis() < edDate.getTime()){
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			
			// 애음추가 요일과 cal 날짜 요일이 같으면 일 실적으로 생성 할 대상일자
			if(dayOfWeek == cycleVo.getDay()){
				DailyVo dailyVo = new DailyVo();
				dailyVo.setCid(cycleVo.getCid());
				dailyVo.setPid(cycleVo.getPid());
				dailyVo.setDt(sdf.format(cal.getTime()));
				dailyVo.setCnt(cycleVo.getCnt());
				dailyVoList.add(dailyVo);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return dailyVoList;
	}

}
