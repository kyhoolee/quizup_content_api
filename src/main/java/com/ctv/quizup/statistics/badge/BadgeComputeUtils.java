package com.ctv.quizup.statistics.badge;

import com.ctv.quizup.statistics.badge.BadgeComputeContext.BadgeContext;
import com.ctv.quizup.statistics.badge.BadgeComputeContext.BadgeRequest;
import com.ctv.quizup.statistics.badge.BadgeComputeContext.BadgeVisitor;
import com.ctv.quizup.user.model.BadgeAchiev;

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
	
	public static BadgeAchiev updateNewMember(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 10% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrNewMember(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_NEWMEMBER,
				0.1);
		
		return badgeAchiev;
	}
	
	public static class VisitNewMember implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU RÙA
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateTurtle(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}

	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrTurtle(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_TURTLE,
				0.05);
		return badgeAchiev;
	}
	
	public static class VisitTurtle implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU ĐEN TOÀN TẬP
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateTotalBlack(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 10% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrTotalBlack(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_TOTALBLACK,
				0.1);
		return badgeAchiev;
	}
	public static class VisitTotalBlack implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU VÒNG LẶP 
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateLoop(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrLoop(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_LOOP,
				0.05);
		return badgeAchiev;
	}
	public static class VisitLoop implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU MÁY BAY CHIẾN ĐẤU
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateFlight(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 10% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrFlight(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_FLIGHT,
				0.1);
		return badgeAchiev;
	}
	public static class VisitFlight implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BẬC THẦY
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateMaster(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrMaster(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_MASTER,
				0.05);
		return badgeAchiev;
	}
	public static class VisitMaster implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BỀN VỮNG
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateStable(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrStable(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_STABLE,
				0.05);
		return badgeAchiev;
	}
	public static class VisitStable implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU NHÀ THƠ
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updatePoem(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrPoem(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_TOTALBLACK,
				0.05);
		return badgeAchiev;
	}
	public static class VisitPoem implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BẢN ĐỒ
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateAtlas(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrAtlas(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_TOTALBLACK,
				0.05);
		return badgeAchiev;
	}
	public static class VisitAtlas implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BÁC HỌC
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateProfessor(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrProfessor(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_PROFESSOR,
				1);
		return badgeAchiev;
	}
	public static class VisitProfessor implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU THIỀN SƯ
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateZen(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrZen(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_ZEN,
				1);
		return badgeAchiev;
	}
	public static class VisitZen implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU CỰU CHIẾN BINH
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateOldSoldier(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrOldSoldier(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_OLDSOLDIER,
				0.05);
		return badgeAchiev;
	}
	public static class VisitOldSoldier implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU THẦN ĐỒNG
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateGenius(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrGenius(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_GENIUS,
				0.05);
		return badgeAchiev;
	}
	public static class VisitGenius implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU QUỸ ĐẠO
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateRepeat(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrRepeat(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_REPEAT,
				0.05);
		return badgeAchiev;
	}
	public static class VisitRepeat implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BÀN THẮNG VÀNG
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateGoldenGoal(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrGoldenGoal(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_GOLDENGOAL,
				0.05);
		return badgeAchiev;
	}
	public static class VisitGoldenGoal implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU SIÊU NHÂN
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateSuper(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrSuper(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_SUPER,
				0.05);
		return badgeAchiev;
	}
	public static class VisitSuper implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU CHĂM CHỈ
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateHard(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrHard(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_HARD,
				0.05);
		return badgeAchiev;
	}
	public static class VisitHard implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU BẤT BẠI
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateUndefeat(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrUndefeat(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_UNDEFEAT,
				0.05);
		return badgeAchiev;
	}
	public static class VisitUndefeat implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU CHIẾN BINH
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateFighter(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrFighter(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_FIGHTER,
				0.05);
		return badgeAchiev;
	}
	public static class VisitFighter implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	// ////////////////////////////////////////////////////////////////////////////
	// HUY HIỆU THÔNG THÁI
	// ////////////////////////////////////////////////////////////////////////////
	public static BadgeAchiev updateSage(BadgeAchiev badgeAchiev) {

		return badgeAchiev;
	}
	/**
	 * Increase 5% in progress
	 * @param badgeAchiev
	 * @return
	 */
	public static BadgeAchiev updateIncrSage(BadgeAchiev badgeAchiev) {
		badgeAchiev = BadgeComputeContext.checkIncrBadgeProgress(
				badgeAchiev,
				BadgeComputeUtils.BADGE_SAGE,
				0.05);
		return badgeAchiev;
	}
	public static class VisitSage implements BadgeVisitor {

		public BadgeAchiev visitBadge(BadgeAchiev achiev) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		public BadgeAchiev visitBadge(BadgeAchiev achiev,
				BadgeContext context, BadgeRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
