package me.shiyan.survive;

import me.shiyan.survive.skills.SkillsType;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

//power = 力量
//physique = 体质
//esthesia = 感知
//MaxMaxStamina = 最大体力条
public class PlayerData {
    public static final String SkillsPath = ".技能";
    public static final String PowerPath = ".力量";
    public static final String PhysiquePath = ".体质";
    public static final String MaxStaminaPath = ".最大耐力值";
    public static final String AttributePointPath = ".属性点";
    public static final String SkillPointPath = ".技能点";
    public static final String PlayerExpPath = ".经验";
    public static final String PlayerLevelPath = ".等级";

    public static File file = new File(Main.getInstance().getDataFolder(), "PlayerData.yml");
    public static YamlConfiguration playerData = YamlConfiguration.loadConfiguration(file);

    public void loadData()
    {
        file = new File(Main.getInstance().getDataFolder(), "PlayerData.yml");
        playerData = YamlConfiguration.loadConfiguration(file);
    }
    public static void saveData() {
        try{
            playerData.save(file);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void reloadData() {
        playerData = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), "PlayerData.yml"));

    }

//power = 力量
//physique = 体质
//esthesia = 感知
//MaxStamina = 最大体力条

    public static void updateAttribute(Player p)
    {
        playerData.set(p.getName()+MaxStaminaPath,playerData.getInt(p.getName()+PhysiquePath)*2+100);
        try{
            p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(getPowerDamage(p));
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(getPhysiqueHealth(p));
            p.getAttribute(Attribute.GENERIC_ARMOR).addModifier(getPhysiqueArmor(p));
        }catch (IllegalArgumentException e)
        {
            p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(getPowerDamage(p));
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(getPhysiqueHealth(p));
            p.getAttribute(Attribute.GENERIC_ARMOR).removeModifier(getPhysiqueArmor(p));

            p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(getPowerDamage(p));
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(getPhysiqueHealth(p));
            p.getAttribute(Attribute.GENERIC_ARMOR).addModifier(getPhysiqueArmor(p));
        }

    }

    public static void addAttributePoint(String name,int value)
    {
        playerData.set(name+AttributePointPath,playerData.getInt(name+AttributePointPath)+value);
    }

    public static void addSkillPoint(String name,int value)
    {
        playerData.set(name+SkillPointPath,playerData.getInt(name+SkillPointPath)+value);
    }

    public static boolean hasAttributePoint(String name)
    {
        return playerData.getInt(name+AttributePointPath) >=1;
    }

    public static boolean hasSkillPoint(String name)
    {
        return playerData.getInt(name+SkillPointPath) >= 1;
    }

    public static void reduceAttributePoint(String name,int value)
    {
        playerData.set(name+AttributePointPath,playerData.getInt(name+AttributePointPath)-value);
    }

    public static void reduceSkillPoint(String name,int value)
    {
        playerData.set(name+SkillPointPath,playerData.getInt(name+SkillPointPath)-value);
    }

    public static Integer getSkillPoint(String name)
    {
        return playerData.getInt(name+SkillPointPath);
    }

    public static Integer getAttributePoint(String name)
    {
        return playerData.getInt(name+AttributePointPath);
    }
    //增加玩家的属性
    public static void addData(Player p, Boolean attribute,String Path,int value) {

        if(attribute)
        {
            if(playerData.getInt(Path)<Config.config.
                    getInt("Attributes.MaxAttributesLevel"))
            {
                playerData.set(Path,playerData.getInt(Path)+value);
                reduceAttributePoint(p.getName(),value);
            }
        }
        else
        {
            addSkillsLevel(Path,value);
            reduceSkillPoint(p.getName(),value);
        }

        saveData();
        updateAttribute(p);
    }

    public static void addExp(Player p,Boolean isExp, int value)
    {
        if(isExp)
        {
            playerData.set
                    (p.getName()+PlayerExpPath,playerData.getInt(p.getName()+PlayerExpPath)+value);
            saveData();
        }
        else
        {
            playerData.set
                    (p.getName()+PlayerLevelPath,playerData.getInt(p.getName()+PlayerLevelPath)+value);
            saveData();
        }
    }

    //0 = 基础 base
    public static void addSkillsLevel(String path, int value)
    {
        playerData.set(path,playerData.getInt(path)+value);
    }


    //获取增加的属性
    public static AttributeModifier getPowerDamage(Player p)
    {
        return new AttributeModifier(p.getUniqueId(),"Damage",
                playerData.getInt(p.getName()+PowerPath)*Config.config.getDouble("Attributes.EachPowerProvideDamage",0.35),
                AttributeModifier.Operation.ADD_NUMBER);
    }
    public static AttributeModifier getPhysiqueHealth(Player p)
    {
        return new AttributeModifier(p.getUniqueId(),"MaxHealth",
                playerData.getInt(p.getName()+PhysiquePath)*Config.config.getDouble("Attributes.EachPhysiqueProvideHealth",0.25),
                AttributeModifier.Operation.ADD_NUMBER);
    }
    public static AttributeModifier getPhysiqueArmor(Player p)
    {
        return new AttributeModifier(p.getUniqueId(),"Armor",
                playerData.getInt(p.getName()+PhysiquePath)*Config.config.getDouble("Attributes.EachPhysiqueProvideArmor",0.1),
                AttributeModifier.Operation.ADD_NUMBER);
    }
    public static void loadNewSkillData(String name, SkillsType type)
    {
        if(!playerData.contains(type.getSkillPath(name),false))
        {
            playerData.set(type.getSkillPath(name),0);
            saveData();
        }
    }

}

