package me.shiyan.survive.events;

import me.shiyan.survive.Config;
import me.shiyan.survive.Main;
import me.shiyan.survive.PlayerData;
import me.shiyan.survive.skills.SkillsType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;


public class PlayerJoin implements Listener {

    @EventHandler
    public void WhenPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        String name = player.getName();
        if(!PlayerData.playerData.contains(player.getName()))
        {
            PlayerData.playerData.set(name+PlayerData.PowerPath,1);//力量
            PlayerData.playerData.set(name+PlayerData.PhysiquePath,1);
            PlayerData.playerData.set(name+PlayerData.MaxStaminaPath,100);
            PlayerData.playerData.set(name+PlayerData.AttributePointPath,0);
            PlayerData.playerData.set(name+PlayerData.SkillPointPath,0);
            PlayerData.playerData.set(name+PlayerData.PlayerExpPath,0);
            PlayerData.playerData.set(name+PlayerData.PlayerLevelPath,0);
            PlayerData.playerData.set(name+PlayerData.SkillsPath+".重击",0);
            PlayerData.loadNewSkillData(name, SkillsType.TOUGH_SKIN);
            PlayerData.saveData();
        }
        PlayerData.loadNewSkillData(name, SkillsType.TOUGH_SKIN);
        PlayerData.updateAttribute(player);
        PlayerData.saveData();
    }

}
