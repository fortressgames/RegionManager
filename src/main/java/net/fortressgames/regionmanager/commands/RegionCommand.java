package net.fortressgames.regionmanager.commands;

import net.fortressgames.fortressapi.Lang;
import net.fortressgames.fortressapi.commands.CommandBase;
import net.fortressgames.fortressapi.players.PlayerModule;
import net.fortressgames.regionmanager.PermissionLang;
import net.fortressgames.regionmanager.RegionLang;
import net.fortressgames.regionmanager.RegionManager;
import net.fortressgames.regionmanager.commands.subcommands.*;
import net.fortressgames.regionmanager.regions.Region;
import net.fortressgames.regionmanager.regions.RegionModule;
import net.fortressgames.regionmanager.users.UserModule;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class RegionCommand extends CommandBase {

	public RegionCommand() {
		super("region", PermissionLang.REGION, "rg");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		Player player;
		Region region = null;

		if(sender instanceof Player target) {
			player = target;
		} else {
			sender.sendMessage(Lang.PLAYERS_ONLY);
			return;
		}

		if(args.length >= 2
				&& !args[0].equalsIgnoreCase("clear")
				&& !args[0].equalsIgnoreCase("create")
				&& !args[0].equalsIgnoreCase("list")
		) {

			if(RegionModule.getInstance().isRegion(args[1])) {
				region = RegionModule.getInstance().getRegion(args[1]);
			} else {
				sender.sendMessage(RegionLang.UNKNOWN_REGION);
				return;
			}
		}

		//
		// HELP
		//
		if(args.length == 0) {
			help(sender);
			return;
		}
		if(args[0].equalsIgnoreCase("help")) {
			help(sender);
		}

		//
		// CREATE
		//
		if(args[0].equalsIgnoreCase("create")) {
			RegionCreate.execute(player, region, args);
			return;
		}

		//
		// REMOVE
		//
		if(args[0].equalsIgnoreCase("remove")) {
			RegionRemove.execute(player, region, args);
			return;
		}

		//
		// LIST
		//
		if(args[0].equalsIgnoreCase("list")) {
			RegionList.execute(player, region, args);
			return;
		}

		//
		// REMOVE
		//
		if(args[0].equalsIgnoreCase("pri")) {
			RegionPriority.execute(player, region, args);
			return;
		}

		//
		// FLAG
		//
		if(args[0].equalsIgnoreCase("flag")) {
			RegionFlag.execute(player, region, args);
			return;
		}

		//
		// INFORMATION
		//
		if(args[0].equalsIgnoreCase("info") && args.length == 2) {
			RegionInformation.execute(player, region, args);
			return;
		}
		if(args[0].equalsIgnoreCase("info")) {

			if(UserModule.getInstance().getUser(player.getPlayer()).getRegions().isEmpty()) {
				sender.sendMessage(RegionLang.REGION_NOT_FOUND);
				return;
			}

			if(UserModule.getInstance().getUser(player.getPlayer()).getRegions().size() == 1) {
				player.getPlayer().performCommand("rg info " + UserModule.getInstance().getUser(player.getPlayer()).getRegions().get(0).getName());
				return;
			}

			player.sendMessage(Lang.LINE);
			player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Current regions:");

			for(Region rg : UserModule.getInstance().getUser(player.getPlayer()).getRegions()) {

				UserModule.getInstance().getUser(player).sendClickableMessage(ChatColor.GREEN + rg.getName() + ": ",
						ChatColor.WHITE + "[CLICK HERE]", "rg info " + rg.getName());
			}

			player.sendMessage(Lang.LINE);
			return;
		}

		//
		// DISPLAY NAME
		//
		if(args[0].equalsIgnoreCase("setdisplay")) {
			RegionDisplayName.execute(player, region, args);
			return;
		}

		//
		// POS
		//
		if(args[0].equalsIgnoreCase("pos")) {
			RegionPos.execute(player, region, args);
			return;
		}

		//
		// SHOW
		//
		if(args[0].equalsIgnoreCase("show")) {
			RegionShow.execute(player, region, args);
			return;
		}

		//
		// MEMBERS
		//
		if(args[0].equalsIgnoreCase("member")) {
			RegionMembers.execute(player, region, args);
			return;
		}

		//
		// RELOAD
		//
		if(args[0].equalsIgnoreCase("reload")) {
			RegionManager.getInstance().loadConfig();
			RegionModule.getInstance().loadRegions();

			UserModule.getInstance().getAllUsers().forEach(user -> {
				if(user.getCombatTask() != null)  user.getCombatTask().cancel();
			});
			UserModule.getInstance().clearUsers();
			PlayerModule.getInstance().getOnlinePlayers().forEach(pp -> UserModule.getInstance().addUser(pp));

			sender.sendMessage(RegionLang.RELOAD);
		}
	}

	private void help(CommandSender sender) {
		sender.sendMessage(Lang.LINE);
		sender.sendMessage(ChatColor.WHITE + "/region help " + ChatColor.GRAY + "- Display all commands");
		sender.sendMessage(ChatColor.WHITE + "/region create " + ChatColor.YELLOW + "[region] " + ChatColor.GRAY + "- Create new region");
		sender.sendMessage(ChatColor.WHITE + "/region remove " + ChatColor.YELLOW + "[region] " + ChatColor.GRAY + "- Remove region");
		sender.sendMessage(ChatColor.WHITE + "/region list " + ChatColor.GRAY + "- List all regions");
		sender.sendMessage(ChatColor.WHITE + "/region pri " + ChatColor.YELLOW + "[region] [level] " + ChatColor.GRAY + "- Set region priority");
		sender.sendMessage(ChatColor.WHITE + "/region flag " + ChatColor.YELLOW + "[region] " + ChatColor.WHITE + "add " + ChatColor.YELLOW + "[name] [values...] " + ChatColor.GRAY + "- Add a flag to a region");
		sender.sendMessage(ChatColor.WHITE + "/region flag " + ChatColor.YELLOW + "[region] " + ChatColor.WHITE + "remove " + ChatColor.YELLOW + "[name] " + ChatColor.GRAY + "- Remove a flag from a region");
		sender.sendMessage(ChatColor.WHITE + "/region info " + ChatColor.GRAY + "- List current region information");
		sender.sendMessage(ChatColor.WHITE + "/region info " + ChatColor.YELLOW + "[region] " + ChatColor.GRAY + "- List region information");
		sender.sendMessage(ChatColor.WHITE + "/region setdisplay " + ChatColor.YELLOW + "[region] [name] " + ChatColor.GRAY + "- Set region display name");
		sender.sendMessage(ChatColor.WHITE + "/region pos " + ChatColor.GRAY + "- Set a pos at your location");
		sender.sendMessage(ChatColor.WHITE + "/region pos clear " + ChatColor.GRAY + "- Clear all pos");
		sender.sendMessage(ChatColor.WHITE + "/region show " + ChatColor.YELLOW + "[region] " + ChatColor.GRAY + "- Show region edge");
		sender.sendMessage(ChatColor.WHITE + "/region member " + ChatColor.YELLOW + "[region] " + ChatColor.WHITE + "add " + ChatColor.YELLOW + "[name] " + ChatColor.GRAY + "- Adds members to the region");
		sender.sendMessage(ChatColor.WHITE + "/region member " + ChatColor.YELLOW + "[region] " + ChatColor.WHITE + "remove " + ChatColor.YELLOW + "[name] " + ChatColor.GRAY + "- Removes members from region");
		sender.sendMessage(ChatColor.WHITE + "/region reload " + ChatColor.GRAY + "Reload plugin!");
		sender.sendMessage(Lang.LINE);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {

		if(args.length == 1) {
			return Arrays.asList("help", "create", "remove", "list", "pri", "flag", "info", "setdisplay", "pos", "show", "member", "reload");
		}

		if(args.length == 2) {
			switch(args[0]) {
				case "remove", "pri", "flag", "info", "setdisplay", "show", "member" -> {
					return RegionModule.getInstance().getAllByName();
				}
			}
		}

		if(args[0].equalsIgnoreCase("flag") || args[0].equalsIgnoreCase("member")) {
			if(args.length == 3) {
				return Arrays.asList("add", "remove");
			}
		}

		if(args[0].equalsIgnoreCase("flag")) {
			if(args.length == 4) {
				return RegionModule.getInstance().getFlags();
			}
		}

		return super.tabComplete(sender, alias, args);
	}
}