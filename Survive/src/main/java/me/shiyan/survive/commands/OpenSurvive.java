package me.shiyan.survive.commands;

import me.shiyan.survive.Config;
import me.shiyan.survive.PlayerData;
import me.shiyan.survive.skills.SkillsType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.shiyan.survive.PlayerData.PowerPath;

public class OpenSurvive implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
        {
            return false;
        }
        Player p = (Player) sender;
        Inventory gui = Bukkit.createInventory(null,5*9, "§aSurvive插件§e属性功能设置");
        //玩家头颅
        gui.setItem(8,getSkull(p));
        //栏
        gui.setItem(0,getGlass("属性栏"));
        gui.setItem(2,getGlass("被动技能栏"));
        //属性
        gui.setItem(9,getPower(p));
        gui.setItem(18,getPhysique(p));
        //技能
        gui.setItem(11,getSkills(p.getName(), SkillsType.THUMP));
        gui.setItem(20,getSkills(p.getName(),SkillsType.TOUGH_SKIN));
        p.openInventory(gui);
        PlayerData.updateAttribute(p);
        return true;
    }

    public static ItemStack getPower(Player p)
    {
        ItemStack power = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta powerMeta = power.getItemMeta();
        powerMeta.setDisplayName("§b力量§e属性：");
        List<String> forPower = new ArrayList<>();
        forPower.add(0,"§e您目前的属性：\n力量：§a "+
                PlayerData.playerData.getInt(p.getName()+ PowerPath) + "§e/§a"
                + Config.config.getInt("Attributes.MaxAttributesLevel"));
        forPower.add(1,"§e当前效果：§增加§a"+PlayerData.playerData.getInt
                (p.getName()+PowerPath)* (float)Config.config.getDouble
                ("Attributes.EachPowerProvideDamage",0.35)+"§e点伤害");
        powerMeta.setLore(forPower);
        power.setItemMeta(powerMeta);
        return power;
    }
    public static ItemStack getPhysique(Player p)
    {
        ItemStack physique = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta physiqueMeta = physique.getItemMeta();
        physiqueMeta.setDisplayName("§b体质§e属性：");
        List<String> forPhysique = new ArrayList<>();
        forPhysique.add(0,"§e您目前的属性：\n体质：§a "+
                PlayerData.playerData.getInt(p.getName()+PlayerData.PhysiquePath) + "§e/§a"
                + Config.config.getInt("Attributes.MaxAttributesLevel"));
        forPhysique.add(1,"§e当前效果①：§增加§a"+PlayerData.playerData.getInt
                (p.getName()+PlayerData.PhysiquePath) *
                (float) Config.config.getDouble("Attributes.EachPhysiqueProvideHealth",0.25)+"§e点生命值");
        forPhysique.add(2,"§e当前效果②：§增加§a"+PlayerData.playerData.getInt
                (p.getName()+PlayerData.PhysiquePath)*
                (float) Config.config.getDouble("Attributes.EachPhysiqueProvideArmor",0.1)+"§e点护甲");

        physiqueMeta.setLore(forPhysique);
        physique.setItemMeta(physiqueMeta);
        return physique;
    }
    public static ItemStack getGlass(String name)
    {
        ItemStack glass = new ItemStack((Material.THIN_GLASS));
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setDisplayName("§a"+name);
        glass.setItemMeta(glassMeta);
        return glass;
    }
    public static ItemStack getSkull(Player p)
    {
        boolean isNew = Arrays.stream(Material.values()).
                map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");
        Material type = Material.matchMaterial(isNew ? "PLAYER_HEAD" : "SKULL_ITEM");
        ItemStack item = new ItemStack(type,1,(short) 3);
        List<String> lore = new ArrayList<>();
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName("§a"+p.getName()+"§e的§b点数");
        meta.setOwningPlayer(p);
        lore.add(0,"§e当前技能点:§a "+PlayerData.getSkillPoint(p.getName()));
        lore.add(1,"§e当前属性点:§a "+PlayerData.getAttributePoint(p.getName()));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static void updateSkull(Player p,Inventory inv)
    {
        inv.setItem(8,getSkull(p));
    }

    public static ItemStack getSkills(String name,SkillsType type)
    {
        ItemStack Skill = new ItemStack(Material.STONE_SWORD);
        ItemMeta skillMeta = Skill.getItemMeta();
        List<String> lore = new ArrayList<>();
        switch (type.getSkillName())
        {
            case "重击":
            {
                skillMeta.setDisplayName("§c重击");
                lore.add(0,"§a当前等级:§e "+
                        PlayerData.playerData.getInt(type.getSkillPath(name),0)+
                        "§b/§e"+Config.config.getInt("Skills.BaseSkills.MaxBaseSkills"));
                lore.add(1,"§b效果:§e 造成伤害时将额外消耗§a"+
                        Config.config.getDouble("Skills.BaseSkills.Thump.NeedStaminaPercentage")*100
                        + "%"+"§e的§a耐力值\n§并额外造成§a"+
                        PlayerData.playerData.getInt(type.getSkillPath(name),1)*
                                Config.config.getDouble
                                        ("Skills.BaseSkills.Thump.EachLevelExtraDamage")*100+"%§e消耗的§a耐力值伤害");
                lore.add(2,"§e每级提升§a"+Config.config.getDouble
                        ("Skills.BaseSkills.Thump.EachLevelExtraDamage")*100+"%§e的额外伤害");
                skillMeta.setLore(lore);
                Skill.setItemMeta(skillMeta);
                return Skill;
            }
            case "坚韧皮肤":
            {
                skillMeta.setDisplayName("§c坚韧皮肤");
                lore.add(0,"§a当前等级:§e"+PlayerData.playerData.getInt(type.getSkillPath(name),0)+
                        "§b/§e"+Config.config.getInt("Skills.BaseSkills.MaxBaseSkills"));
                lore.add(1,"§b效果:§e 被攻击时减少§a"+
                        PlayerData.playerData.getInt(type.getSkillPath(name),1)*
                                Config.config.getDouble
                                        ("Skills.BaseSkills.ToughSkin.EachLevelReduceDamage")+"§e点伤害");
                lore.add(2,"§e每级提升§a"+Config.config.getDouble
                        ("Skills.BaseSkills.ToughSkin.EachLevelReduceDamage")+"§e点额外减伤");
                skillMeta.setLore(lore);
                Skill.setItemMeta(skillMeta);
                return Skill;
            }
        }
        return null;
    }
}
