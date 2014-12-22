package com.ctv.quizup.content.sql.raw;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class RawTopic {
	public String topicId;
	
	public String idCode;
	public String title;
	
	public String parent;
	
	public RawTopic() {
		this.topicId = "";
		this.idCode = "";
		this.title = "";
		this.parent = "";
	}
	

	@Override
	public String toString(){
		String result = "";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(this);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
}
