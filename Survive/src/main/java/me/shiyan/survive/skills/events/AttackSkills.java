package me.shiyan.survive.skills.events;

import me.shiyan.survive.Config;
import me.shiyan.survive.PlayerData;
import me.shiyan.survive.skills.SkillsType;
import me.shiyan.survive.stamina.Stamina;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackSkills implements Listener {

    @EventHandler
    public void Thump(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof Player)
        {
            Player p = (Player) e.getDamager();
            if(PlayerData.playerData.getInt(SkillsType.THUMP.getSkillPath(p.getName()))>0)
            {
                int max = PlayerData.playerData.getInt(p.getName()+PlayerData.MaxStaminaPath);
                int i = (int) (Config.config.getDouble("Skills.BaseSkills.Thump.NeedStaminaPercentage") * max);
                double Pro = PlayerData.playerData.getInt(SkillsType.THUMP.getSkillPath(p.getName()))
                        * Config.config.getDouble("Skills.BaseSkills.Thump.EachLevelExtraDamage");
                Stamina.InStamina(p,i);
                e.setDamage(e.getDamage()+i*Pro);
            }
        }
    }
}
