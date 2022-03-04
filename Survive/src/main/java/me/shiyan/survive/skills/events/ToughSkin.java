package me.shiyan.survive.skills.events;

import me.shiyan.survive.Config;
import me.shiyan.survive.PlayerData;
import me.shiyan.survive.skills.SkillsType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ToughSkin implements Listener {

    @EventHandler
    public void ReduceDamage(EntityDamageByEntityEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            if(PlayerData.playerData.getInt(SkillsType.TOUGH_SKIN.getSkillPath(p.getName()))>0)
            {
                int level = PlayerData.playerData.getInt(SkillsType.TOUGH_SKIN.getSkillPath(p.getName()));
                double reduceDamage = level* Config.config.getDouble("Skills.BaseSkills.ToughSkin.EachLevelReduceDamage");
                e.setDamage(e.getDamage()-reduceDamage);
            }
        }
    }
}
