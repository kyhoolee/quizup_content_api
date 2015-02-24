package com.ctv.quizup.user.model;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class BadgeCountInfo {
	
	
	public static enum CountType {
		Match, // 1
		
		Match_Win, // 2
		Match_Tie, // 3
		Match_Lose, // 4
		
		Topic_Music, // 5 category âm nhạc
		Topic_Sport, // 6 category thể thao
		Topic_Education, // 7 category giáo dục
		Topic_Entertainment, // 8 category giải trí 
		Topic_Nature, // 9 category tự nhiên 
		Topic_LifeStyle, // 10 category phong cách sống 
		Topic_Science, // 11 category khoa học 
		
		Topic, // 12
		
		Match_Perfect, // 13 trả lời đúng tất cả các câu hỏi
		Match_Good, // 14 trả lời 5/7 câu hỏi
		Match_Counter, // 15 lật ngược bằng câu cuối
		Match_1_Answer, // 16  đúng duy nhất 1 câu
		Match_0_Answer, // 17 không đúng câu nào
		
		Topic_Poem, // 18 nhà thơ - chơi topic văn học
		Topic_Map, // 19 bản đồ - chơi topic địa lý
		Topic_Singer, // 20 ca sĩ - chơi topic âm nhạc
		Topic_Painter, // 21 họa sĩ - chơi topic hội họa
		Topic_Artist, // 22 nghệ sĩ - chơi topic giải trí 
		Topic_Fan, // 23 người hâm mộ - chơi topic thể thao
		Topic_Mathematican, // 24 nhà toán học - chơi topic toán học
		Topic_Physist, // 25 nhà vật lý - chơi topic vật lý
		Topic_Chemican, // 26 nhà hóa học - chơi topic hóa học 
		
		Topic_Naturist, // 27 nhà tự nhiên học - chơi 1 topic trong category tự nhiên  
		Topic_LifeStylist, // 28 nhịp sống trẻ - chọn 1 topic trong category phong cách sống
		
		Frequent_Win, // 29 siêu nhân - chiến thắng liên tiếp 
		Frequent_Answer, // 30 trình sam - lặp lại số câu trả lời giống hệt trận đấu trước 
		
		Compete_Location // 31 đối thủ - đối thủ ở các tỉnh khác nhau  
	}
	
	private String badgeId;
	private CountType countType;
	
	private int countScore;
	private int condition;// For Topic-Level-Condition
	
	public BadgeCountInfo() {
		this.condition = -1; // no condition
	}

	public BadgeCountInfo(String badgeId, CountType countType, int countScore, int condition) {
		this.badgeId = badgeId;
		this.countType = countType;
		this.countScore = countScore;
		this.condition = condition;
	}
	
	
	
	public String getBadgeId() {
		return badgeId;
	}


	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
	}


	public CountType getCountType() {
		return countType;
	}


	public void setCountType(CountType countType) {
		this.countType = countType;
	}


	public int getCountScore() {
		return countScore;
	}


	public void setCountScore(int countScore) {
		this.countScore = countScore;
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



	/**
	 * @return the condition
	 */
	public int getCondition() {
		return condition;
	}



	/**
	 * @param condition the condition to set
	 */
	public void setCondition(int condition) {
		this.condition = condition;
	}


	public static void main(String[] args) {
		System.out.println(CountType.Match);
	}
}
