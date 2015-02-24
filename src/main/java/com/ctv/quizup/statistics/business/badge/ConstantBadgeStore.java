package com.ctv.quizup.statistics.business.badge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.user.model.Badge;
import com.ctv.quizup.user.model.BadgeCountInfo;
import com.ctv.quizup.user.model.BadgeAchiev;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;


/**
 * Created by Huu on 12/24/2014.
 */
public class ConstantBadgeStore {
	private static List<BadgeCountInfo> matchBadgeCount = null;
	private static List<BadgeCountInfo> winBadgeCount = null;
	private static List<BadgeCountInfo> tieBadgeCount = null;
	private static List<BadgeCountInfo> loseBadgeCount = null;
	
	
    static {
        matchBadgeCount = new ArrayList<BadgeCountInfo>();
        winBadgeCount = new ArrayList<BadgeCountInfo>();
        tieBadgeCount = new ArrayList<BadgeCountInfo>();
        loseBadgeCount = new ArrayList<BadgeCountInfo>();
        
        matchBadgeCount.add(new BadgeCountInfo("1", CountType.Match, 1, -1));
        matchBadgeCount.add(new BadgeCountInfo("2", CountType.Match, 50, -1));
        matchBadgeCount.add(new BadgeCountInfo("3", CountType.Match, 100, -1));
        matchBadgeCount.add(new BadgeCountInfo("4", CountType.Match, 200, -1));
        matchBadgeCount.add(new BadgeCountInfo("5", CountType.Match, 500, -1));
        
        winBadgeCount.add(new BadgeCountInfo("11", CountType.Match_Win, 10, -1));
        winBadgeCount.add(new BadgeCountInfo("12", CountType.Match_Win, 50, -1));
        winBadgeCount.add(new BadgeCountInfo("13", CountType.Match_Win, 100, -1));
        winBadgeCount.add(new BadgeCountInfo("14", CountType.Match_Win, 300, -1));
        winBadgeCount.add(new BadgeCountInfo("15", CountType.Match_Win, 500, -1));
        
        tieBadgeCount.add(new BadgeCountInfo("16", CountType.Match_Tie, 10, -1));
        tieBadgeCount.add(new BadgeCountInfo("17", CountType.Match_Tie, 50, -1));
        tieBadgeCount.add(new BadgeCountInfo("18", CountType.Match_Tie, 100, -1));
        tieBadgeCount.add(new BadgeCountInfo("19", CountType.Match_Tie, 300, -1));
        tieBadgeCount.add(new BadgeCountInfo("20", CountType.Match_Tie, 500, -1));
        
        loseBadgeCount.add(new BadgeCountInfo("21", CountType.Match_Lose, 10, -1));
        loseBadgeCount.add(new BadgeCountInfo("22", CountType.Match_Lose, 50, -1));
        loseBadgeCount.add(new BadgeCountInfo("23", CountType.Match_Lose, 100, -1));
        loseBadgeCount.add(new BadgeCountInfo("24", CountType.Match_Lose, 300, -1));
        loseBadgeCount.add(new BadgeCountInfo("25", CountType.Match_Lose, 500, -1));
    }
    
    private static Map<String, Badge> badgeMap = null;//HashMap<String, Badge>();

    static {
        badgeMap = new HashMap<String, Badge>();
        
        

        // theo so tran dau
        badgeMap.put("1", new Badge("1", "", "", ""));
        badgeMap.put("2", new Badge("2", "", "", ""));
        badgeMap.put("3", new Badge("3", "", "", ""));
        badgeMap.put("4", new Badge("4", "", "", ""));
        badgeMap.put("5", new Badge("5", "", "", ""));

        // theo so tran thang
        badgeMap.put("11", new Badge("1", "", "", ""));
        badgeMap.put("12", new Badge("2", "", "", ""));
        badgeMap.put("13", new Badge("3", "", "", ""));
        badgeMap.put("14", new Badge("4", "", "", ""));
        badgeMap.put("15", new Badge("5", "", "", ""));
        
        // theo so tran hoa
        badgeMap.put("16", new Badge("1", "", "", ""));
        badgeMap.put("17", new Badge("2", "", "", ""));
        badgeMap.put("18", new Badge("3", "", "", ""));
        badgeMap.put("19", new Badge("4", "", "", ""));
        badgeMap.put("20", new Badge("5", "", "", ""));
        
        // theo so tran thua
        badgeMap.put("21", new Badge("1", "", "", ""));
        badgeMap.put("22", new Badge("2", "", "", ""));
        badgeMap.put("23", new Badge("3", "", "", ""));
        badgeMap.put("24", new Badge("4", "", "", ""));
        badgeMap.put("25", new Badge("5", "", "", ""));

    };
	
	public List<BadgeCountInfo> getBadgeCountListByType(CountType type) {
		if(type == CountType.Match) {
			return matchBadgeCount;
		} else if (type == CountType.Match_Win) {
			return winBadgeCount;
		} else if (type == CountType.Match_Tie) {
			return tieBadgeCount;
		} else if (type == CountType.Match_Lose) {
			return loseBadgeCount;
		}
		return new ArrayList<BadgeCountInfo>();
	}
	
	public Badge getBadgeById(String badgeId) {
		return badgeMap.get(badgeId);
	}
	
	

}
