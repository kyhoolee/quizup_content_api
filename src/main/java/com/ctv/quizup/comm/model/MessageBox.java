package com.ctv.quizup.comm.model;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class MessageBox {

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
