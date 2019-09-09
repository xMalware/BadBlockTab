package fr.badblock.tab;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.events.api.PlayerLoadedEvent;
import fr.badblock.gameapi.players.BadblockPlayer;

public class BadTab extends JavaPlugin implements Listener
{

	private Map<String, Long> lastJoin = new HashMap<>();

	@Override
	public void onEnable()
	{
		this.getServer().getPluginManager().registerEvents(this, this);

		this.getServer().getPluginManager().registerEvents(new TabGroupListener(this), this);
	}
	
	@EventHandler
	public void onLoaded(PlayerLoadedEvent event)
	{
		BadblockPlayer player = event.getPlayer();
		if (lastJoin.containsKey(player.getName()) && lastJoin.get(player.getName()) > System.currentTimeMillis())
		{
			return;
		}

		lastJoin.put(player.getName(), System.currentTimeMillis() + 2000);

		if (!player.hasPermission("essentials.silentjoin"))
		{
			Bukkit.broadcastMessage("§f[§a+§f] " + player.getName());
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		event.setDeathMessage("");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		event.setJoinMessage("");
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		if (event.getPlayer().hasPermission("essentials.silentquit"))
		{
			event.setQuitMessage("");
			return;
		}
		event.setQuitMessage("§f[§c-§f] " + event.getPlayer().getName());
	}

}