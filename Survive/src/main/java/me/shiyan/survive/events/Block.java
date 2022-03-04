package me.shiyan.survive.events;

import me.shiyan.survive.Config;
import me.shiyan.survive.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.HashSet;
import java.util.Set;

public class Block implements Listener {
    protected Set<Player> playerBlock = new HashSet<>();


    @EventHandler
    public void block(PlayerInteractEntityEvent event)
    {

        Player player = event.getPlayer();
        if(event.getRightClicked() instanceof Monster && !playerBlock.contains(player))
        {
            PlayerInventory playerInventory = event.getPlayer().getInventory();
            if((player.getLocation().distance(event.getRightClicked().getLocation())<=3) &&
                    playerInventory.getItemInMainHand().getItemMeta().getDisplayName().contains(
                            Config.config.getString("格挡所需包含的名称")))
            {
                player.sendMessage("yes");
                playerBlock.add(player);
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        if (playerBlock.contains(player))
                        {
                            playerBlock.remove(player);
                        }
                    }
                }.runTaskLaterAsynchronously(Main.getPlugin(Main.class),15);
            }
        }

    }
    @EventHandler
    public void Counter(EntityDamageByEntityEvent e)
    {
        if(e.getEntity().getType().equals(EntityType.PLAYER) &&
                e.getDamager() instanceof Monster)
        {
            Monster monster = (Monster) e.getDamager();
            Player player = (Player) e.getEntity();
            if(playerBlock.contains(player))
            {
                monster.damage(1.5*e.getDamage(),player);
                e.setDamage(0);
                playerBlock.remove(player);
            }
        }
    }
}
