package com.ctv.quizup.content.sql.raw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class RawQuestion {
	public String id;
	
	public String question;
	public String caseA;
	public String caseB;
	public String caseC;
	public String caseD;
	
	public String trueCase;
	
	public int level;
	public List<String> catList;
	
	public RawQuestion() {
		this.catList = new ArrayList<String>();
	}
	
	public void printContent() {
		
	}
	
	public void addCat(String[] cat) {
		for(int i = 0 ; i < cat.length ; i ++) {
			this.catList.add(cat[i]);
		}
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
