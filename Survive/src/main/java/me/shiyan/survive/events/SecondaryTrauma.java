package me.shiyan.survive.events;

import me.shiyan.survive.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


//SecondaryTrauma ： 二次创伤   wound ：创口
public class SecondaryTrauma implements Listener {
    protected Set<Integer> entityWound = new HashSet<>();
    protected Map<Integer,Integer> Mob = new HashMap<>();

    @EventHandler
    public void Wound(EntityDamageByEntityEvent event)
    {
        //判断被攻击的实体是否拥有创口
        if(event.getDamager().getType().equals(EntityType.PLAYER) &&
                !Mob.containsKey(event.getEntity().getEntityId()))
        {
            Player player = (Player) event.getDamager();
            LivingEntity entity = (LivingEntity) event.getEntity();
            Mob.put(entity.getEntityId(),1);
            return;
        }
        //判断被攻击的实体拥有创口并且攻击者是否为玩家
        if(Mob.containsKey(event.getEntity().getEntityId()) &&
                event.getDamager().getType().equals(EntityType.PLAYER))
        {   //
            if(Mob.get(event.getEntity().getEntityId()) < Config.config.getInt("加深创口最多次数",3))
            {
                Mob.replace(event.getEntity().getEntityId(), Mob.get(event.getEntity().getEntityId())+1);
            }
            Player p = (Player) event.getDamager();
            event.setDamage(Float.parseFloat(Config.config.getString("WoundDamage","1.2"))
                    * Mob.get(event.getEntity().getEntityId()) * 0.1 * event.getDamage()
                    + event.getDamage());//伤害加成
            if(event.getEntity().isDead())
            {
                Mob.remove(event.getEntity().getEntityId());

            }
        }

    }
}
