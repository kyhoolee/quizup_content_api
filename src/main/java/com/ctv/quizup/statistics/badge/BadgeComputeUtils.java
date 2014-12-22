package com.ctv.quizup.statistics.badge;

import com.ctv.quizup.statistics.badge.BadgeComputeContext.BadgeContext;
import com.ctv.quizup.statistics.badge.BadgeComputeContext.BadgeRequest;
import com.ctv.quizup.statistics.badge.BadgeComputeContext.BadgeVisitor;
import com.ctv.quizup.user.model.UserBadgeAchiev;

public class BadgeComputeUtils {
	public static final String BADGE_NEWMEMBER = "new_member";
	public static final String BADGE_TURTLE = "turtle";
	public static final String BADGE_TOTALBLACK = "total_black";
	public static final String BADGE_LOOP = "loop";
	public static final String BADGE_FLIGHT = "flight";
	public static final String BADGE_MASTER = "master";
	public static final String BADGE_STABLE = "stable";
	public static final String BADGE_POEM = "poem";
	public static final String BADGE_ATLAS = "atlas";
	public static final String BADGE_PROFESSOR = "professor";
	public static final String BADGE_ZEN = "zen";
	public static final String BADGE_OLDSOLDIER = "old_soldier";
	public static final String BADGE_GENIUS = "genius";
	public static final String BADGE_REPEAT = "repeat";
	public static final String BADGE_GOLDENGOAL = "golden_goal";
	public static final String BADGE_SUPER = "super";
	public static final String BADGE_HARD = "hard";
	public static final String BADGE_UNDEFEAT = "undefeat";
	public static final String BADGE_FIGHTER = "fighter";
	public static final String BADGE_SAGE = "sage";


	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU THÀNH VIÊN MỚI
	// ////////////////////////////////////////////////////////////////////////////
	
	public static UserBadgeAchiev updateNewMember(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 10% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrNewMember(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_NEWMEMBER,
				0.1);
		
		return badgeAchiev;
	}
	
	public static class VisitNewMember implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU RÙA
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateTurtle(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}

	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrTurtle(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_TURTLE,
				0.05);
		return badgeAchiev;
	}
	
	public static class VisitTurtle implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU ĐEN TOÀN TẬP
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateTotalBlack(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 10% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrTotalBlack(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_TOTALBLACK,
				0.1);
		return badgeAchiev;
	}
	public static class VisitTotalBlack implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU VÒNG LẶP 
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateLoop(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrLoop(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_LOOP,
				0.05);
		return badgeAchiev;
	}
	public static class VisitLoop implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU MÁY BAY CHIẾN ĐẤU
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateFlight(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 10% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrFlight(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_FLIGHT,
				0.1);
		return badgeAchiev;
	}
	public static class VisitFlight implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BẬC THẦY
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateMaster(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrMaster(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_MASTER,
				0.05);
		return badgeAchiev;
	}
	public static class VisitMaster implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BỀN VỮNG
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateStable(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrStable(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_STABLE,
				0.05);
		return badgeAchiev;
	}
	public static class VisitStable implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU NHÀ THƠ
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updatePoem(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrPoem(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_TOTALBLACK,
				0.05);
		return badgeAchiev;
	}
	public static class VisitPoem implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BẢN ĐỒ
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateAtlas(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrAtlas(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_TOTALBLACK,
				0.05);
		return badgeAchiev;
	}
	public static class VisitAtlas implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BÁC HỌC
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateProfessor(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrProfessor(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_PROFESSOR,
				1);
		return badgeAchiev;
	}
	public static class VisitProfessor implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU THIỀN SƯ
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateZen(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrZen(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_ZEN,
				1);
		return badgeAchiev;
	}
	public static class VisitZen implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU CỰU CHIẾN BINH
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateOldSoldier(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrOldSoldier(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_OLDSOLDIER,
				0.05);
		return badgeAchiev;
	}
	public static class VisitOldSoldier implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU THẦN ĐỒNG
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateGenius(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrGenius(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_GENIUS,
				0.05);
		return badgeAchiev;
	}
	public static class VisitGenius implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU QUỸ ĐẠO
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateRepeat(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrRepeat(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_REPEAT,
				0.05);
		return badgeAchiev;
	}
	public static class VisitRepeat implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BÀN THẮNG VÀNG
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateGoldenGoal(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrGoldenGoal(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_GOLDENGOAL,
				0.05);
		return badgeAchiev;
	}
	public static class VisitGoldenGoal implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU SIÊU NHÂN
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateSuper(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrSuper(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_SUPER,
				0.05);
		return badgeAchiev;
	}
	public static class VisitSuper implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU CHĂM CHỈ
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateHard(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrHard(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_HARD,
				0.05);
		return badgeAchiev;
	}
	public static class VisitHard implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BẤT BẠI
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateUndefeat(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrUndefeat(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_UNDEFEAT,
				0.05);
		return badgeAchiev;
	}
	public static class VisitUndefeat implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU CHIẾN BINH
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateFighter(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrFighter(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_FIGHTER,
				0.05);
		return badgeAchiev;
	}
	public static class VisitFighter implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU THÔNG THÁI
	// ////////////////////////////////////////////////////////////////////////////
	public static UserBadgeAchiev updateSage(UserBadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static UserBadgeAchiev updateIncrSage(UserBadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_SAGE,
				0.05);
		return badgeAchiev;
	}
	public static class VisitSage implements BadgeVisitor {

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public UserBadgeAchiev visitBadge(UserBadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
