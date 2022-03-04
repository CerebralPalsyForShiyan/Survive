package me.shiyan.survive.stamina;

import me.shiyan.survive.Config;
import me.shiyan.survive.Main;
import me.shiyan.survive.PlayerData;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Stamina implements Listener {
    public static Map<Player,Integer> stamina = new HashMap<>();
    public static Set<Player> debuff = new HashSet<>();

    @EventHandler
    public void WhenMove(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        if(!stamina.containsKey(player))
        {

            stamina.put(player,PlayerData.playerData.getInt(e.getPlayer().getName()+
                    PlayerData.MaxStaminaPath));
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if(stamina.get(player)<30 && !debuff.contains(player))
                    {
                        debuff.add(player);
                        player.addPotionEffect(new PotionEffect(
                                PotionEffectType.SLOW,10,0));
                        player.sendTitle("",
                                Config.PluginHead+"你需要尽快停下来恢复体力",5,15,5);
                    }
                    if(!(player.getVelocity().getZ()==0 && player.getVelocity().getX()==0 &&
                            !(player.isSprinting())))
                    {

                        player.sendMessage(String.valueOf(player.getVelocity()));
                        InStamina(player,5);
                        if(stamina.getOrDefault(player,
                                PlayerData.playerData.getInt(e.getPlayer().getName()+
                                        PlayerData.MaxStaminaPath)) <=0)
                        {
                            setStamina(player,0);
                        }

                        ShowStamina(player);
                    }
                    else
                    {
                        addStamina(player,10);
                        if(stamina.getOrDefault(player, PlayerData.playerData.getInt
                                (e.getPlayer().getName()+PlayerData.MaxStaminaPath))>=
                                PlayerData.playerData.getInt(e.getPlayer().getName()+
                                        PlayerData.MaxStaminaPath))
                        {
                            setStamina(player,PlayerData.playerData.getInt
                                    (e.getPlayer().getName()+PlayerData.MaxStaminaPath));
                        }
                        if(getStamina(player)<30)
                        {
                            InStamina(player,4);
                        }

                        ShowStamina(player);
                    }
                    if((stamina.get(player) > 30 && debuff.contains(player)))
                    {
                        player.removePotionEffect(PotionEffectType.SLOW);
                        debuff.remove(player);
                    }
                }
            }.runTaskTimer(Main.getPlugin(Main.class),0,15);
        }
    }


    @EventHandler
    public void PlayerDamage(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof Player)
        {
            Player player = (Player) e.getDamager();
            try{
                InStamina(player,10);
            }catch (NullPointerException n)
            {
                if (!stamina.containsKey(player))
                {
                    stamina.put(player,PlayerData.playerData.getInt
                            (player.getName()+PlayerData.MaxStaminaPath));
                }
            }
            if(getStamina(player)<26)
            {
                e.setDamage(e.getDamage()*0.65);
            }

        }
    }
    public static Integer getStamina(Player player){return stamina.get(player);}
    public static void setStamina(Player player,int value)
    {
        stamina.replace(player,value);
    }
    public static void InStamina(Player player, int value)
    {
        stamina.replace(player,stamina.get(player)-value);
    }
    public static void addStamina(Player player, int value)
    {
        stamina.replace(player,stamina.get(player)+value);
    }
    public static void ShowStamina(Player player)
    {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                new TextComponent(Config.PluginHead+"耐力条"+stamina.get(player)+"/"+
                        PlayerData.playerData.getInt(player.getName()+PlayerData.MaxStaminaPath)));
    }

}
