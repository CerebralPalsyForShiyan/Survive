package me.shiyan.survive.stamina;

import me.shiyan.survive.Config;
import me.shiyan.survive.PlayerData;
import me.shiyan.survive.skills.SkillsType;
import me.shiyan.survive.commands.OpenSurvive;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class PluginMenu implements Listener {


    @EventHandler
    public void MenuClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        if(e.getWhoClicked().getOpenInventory().getTitle().equals("§aSurvive插件§e属性功能设置"))
        {
            e.setCancelled(true);

        }
        if(e.getRawSlot()>e.getInventory().getSize()) {return;}
        if(e.getRawSlot()<0) {return;}
        switch (e.getRawSlot())
        {
            case 9:
            {
                if(PlayerData.hasAttributePoint(player.getName()) && e.isLeftClick())
                {
                    PlayerData.addData(player,true,
                            player.getName()+PlayerData.PowerPath, 1);
                    e.getClickedInventory().setItem(9, OpenSurvive.getPower(player));
                    player.sendMessage(Config.PluginHead+"加点成功");
                    OpenSurvive.updateSkull(player,e.getClickedInventory());
                    break;
                }
            }
            case 18:
            {
                if(PlayerData.hasAttributePoint(player.getName()) && e.isLeftClick())
                {
                    PlayerData.addData(player,true,
                            player.getName()+PlayerData.PhysiquePath,1);
                    e.getClickedInventory().setItem(18,OpenSurvive.getPhysique(player));
                    player.sendMessage(Config.PluginHead+"加点成功");
                    OpenSurvive.updateSkull(player,e.getClickedInventory());
                    break;
                }
            }
            case 11:
            {
                if(PlayerData.hasSkillPoint(player.getName()) && e.isLeftClick() &&
                        PlayerData.playerData.getInt(SkillsType.THUMP.getSkillPath(player.getName()))<
                                Config.config.getInt("Skills.BaseSkills.MaxBaseSkills"))
                {
                    PlayerData.addData(player,
                            false, SkillsType.THUMP.getSkillPath(player.getName()), 1);
                    e.getClickedInventory().setItem(11,
                            OpenSurvive.getSkills(player.getName(), SkillsType.THUMP));
                    player.sendMessage(Config.PluginHead+"加点成功");
                    OpenSurvive.updateSkull(player,e.getClickedInventory());
                    break;
                }
                else
                {
                    player.sendMessage(Config.PluginHead+"您没技能点了，或者技能已达上限");
                }
            }
            case 20:
                if(PlayerData.hasSkillPoint(player.getName()) && e.isLeftClick() &&
                        PlayerData.playerData.getInt(SkillsType.TOUGH_SKIN.getSkillPath(player.getName()))<
                                Config.config.getInt("Skills.BaseSkills.MaxBaseSkills"))
                {
                    PlayerData.addData
                            (player,false,SkillsType.TOUGH_SKIN.getSkillPath(player.getName()),1);
                    e.getClickedInventory().setItem(20,OpenSurvive.getSkills(player.getName(),SkillsType.TOUGH_SKIN));
                    player.sendMessage(Config.PluginHead+"加点成功");
                    OpenSurvive.updateSkull(player, e.getClickedInventory());
                    break;
                }
                else
                {
                    player.sendMessage(Config.PluginHead+"您没技能点了，或者技能已达上限");
                }
            default:
            {
                break;
            }
        }
    }

    @EventHandler
    public void MenuDrag(InventoryDragEvent e)
    {
        if(e.getWhoClicked().getOpenInventory().getTitle().equals("§aSurvive插件§e属性功能设置"))
        {
            e.setCancelled(true);
        }
    }
}
