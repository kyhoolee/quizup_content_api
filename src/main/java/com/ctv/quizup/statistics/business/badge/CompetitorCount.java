package com.ctv.quizup.statistics.business.badge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.statistics.business.BadgeBusiness;
import com.ctv.quizup.user.model.UserBadgeAchiev;

public class CompetitorCount extends BaseCount {

	public CompetitorCount(BadgeBusiness business) {
		super(business);
	}

	@Override
	public void count() {
		
	}

	@Override
	public List<UserBadgeAchiev> countBadge() {
		return null;
	}

}
