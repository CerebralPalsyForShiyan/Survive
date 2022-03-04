package me.shiyan.survive.events;

import me.shiyan.survive.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class BoneBreak implements Listener {
    @EventHandler//fracture的意思是 骨折
    public void Fracture(EntityDamageEvent entityDamageEvent)
    {
        if(entityDamageEvent.getEntityType().equals(EntityType.PLAYER) &&
                entityDamageEvent.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            int fall = Config.config.getInt("BoneBreakQuantity");
            Player player = (Player) entityDamageEvent.getEntity();
            float FallDistance = ((player.getFallDistance() -
                    (fall))*Float.parseFloat(Config.config.getString("BoneBreakProQuantity")));
            float chance = (new Random().nextFloat());
            if(player.getFallDistance()>=fall && (FallDistance+chance)>=1)
            {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,300,2));
                player.sendMessage(Config.PluginHead+"你骨折了");
            }
        }
    }
}
