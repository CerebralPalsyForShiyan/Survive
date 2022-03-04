package me.shiyan.survive.events;


import me.shiyan.survive.Config;
import me.shiyan.survive.Main;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoseBlood implements Listener {
    int blood = Config.config.getInt("LoseBloodDamage");
    protected Map<Player,Boolean> isBleed = new HashMap<>();
    protected Map<Integer,Boolean> MobIsBleed = new HashMap<>();

    @EventHandler
    public void Bleed(EntityDamageByEntityEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) &&
                event.getEntity().getType().equals(EntityType.PLAYER))
        {
            Player player = (Player) event.getEntity();

            int r = new Random().nextInt(100);
            if ((r < Config.config.getInt("LosePercentage") && !isBleed.containsKey(player)))
            {
                isBleed.put(player,true);
                new BukkitRunnable()
                {
                    int i = 5;
                    @Override
                    public void run()
                    {
                        i--;
                        player.damage(blood);
                        if (player.isDead() || i <= 0)
                        {
                            this.cancel();
                            isBleed.remove(player);
                            return;
                        }
                    }
                }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0, 40);
            }

        }

        if((event.getDamager().getType().equals(EntityType.PLAYER)) && !event.getEntity().isDead() &&
                !MobIsBleed.containsKey(event.getEntity().getEntityId()))
        {
            if(event.getEntity() instanceof Monster)
            {
                Monster e = (Monster) event.getEntity();
                MobIsBleed.put(e.getEntityId(),true);
                e.damage(blood);
                new BukkitRunnable()
                {
                    int i = 5;
                    @Override
                    public void run()
                    {
                        i--;
                        e.damage(blood);
                        if (e.isDead() || i <= 0)
                        {
                            this.cancel();
                            MobIsBleed.remove(e.getEntityId());
                            return;
                        }
                    }
                }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0, 40);
            }
            else if(event.getEntity() instanceof Creature)
            {
                Creature e = (Creature) event.getEntity();
                MobIsBleed.put(e.getEntityId(),true);
                e.damage(blood);
                new BukkitRunnable()
                {
                    int i = 5;
                    @Override
                    public void run()
                    {
                        i--;
                        e.damage(blood);
                        if (e.isDead() || i <= 0)
                        {
                            this.cancel();
                            MobIsBleed.remove(e.getEntityId());
                            return;
                        }
                    }
                }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0, 40);
            }
        }

    }
}
