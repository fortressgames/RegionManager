package net.fortressgames.regionmanager;

import net.fortressgames.fortressapi.Lang;
import org.bukkit.ChatColor;

public class RegionLang {

	public static final String UNKNOWN_REGION = Lang.RED + "Unknown region!";
	public static final String REGION_NOT_FOUND = Lang.RED + "No region found!";
	public static final String REGION_EXISTS = Lang.RED + "That is already a region!";
	public static final String REGION_NOT_FOUND_RUN_POS = Lang.RED + "No region found run /rg pos";
	public static final String REGION_ADDED = Lang.GREEN + "Region added!";
	public static final String REGION_NAME_UPDATE = Lang.GREEN + "Name updated!";
	public static final String REGION_FLAG_ADDED = Lang.GREEN + "Flag added!";
	public static final String REGION_FLAG_REMOVED = Lang.GREEN + "Flag removed!";
	public static final String REGION_POS_CLEAR = Lang.GREEN + "Pos's cleared!";
	public static final String REGION_REMOVE = Lang.GREEN + "Region removed!";
	public static final String REGION_SHOW = Lang.GREEN + "Showing region!";
	public static final String PVP_FALSE = Lang.RED + "PVP is disabled here!";
	public static final String COMBAT_TAG_ON = Lang.YELLOW + "You are in combat don't logout!!";
	public static final String COMBAT_TAG_OFF = Lang.YELLOW + "You are no longer in combat you are safe!";
	public static final String ENTRY = Lang.RED + "You cannot enter this region!";
	public static final String ENTRY_COMBAT = Lang.RED + "You are in combat, You cannot enter this region!";
	public static final String MEMBER_ADD = Lang.GREEN + "Member added to region!";
	public static final String MEMBER_REMOVE = Lang.GREEN + "Member removed from region!";
	public static final String ALREADY_MEMBER = Lang.RED + "Player is already a member of that region!";

	public static String combatLogged(String player) {
		return Lang.RED + player + " combat logged and died!";
	}
	public static String regionPos(String size) {
		return Lang.GREEN + "Pos added! " + ChatColor.RED + "#" + size;
	}
	public static String regionPri(String value) {
		return Lang.GREEN + "Pri set to " + value;
	}
}