package kr.or.ddit.batch.hello;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
									// 문자열을 읽어서 반환해 줌
public class HelloReader implements ItemReader<String>{
	
	private Logger logger = LoggerFactory.getLogger(HelloReader.class);
	
	private List<String> rangers;
	private int index = 0;
	
	public HelloReader(){
		rangers = new ArrayList<String>();
		rangers.add("brown");
		rangers.add("cony");
		rangers.add("sally");
		rangers.add("james");
		rangers.add("moon");
	}

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(index < rangers.size()){
			String ranger = rangers.get(index);
			index++;
			logger.debug("read : {}", ranger);
			return ranger;
		}else{
			index = 0;
			return null;
		}
	}
}
