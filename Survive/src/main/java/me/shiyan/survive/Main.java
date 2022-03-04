package me.shiyan.survive;


import me.shiyan.survive.commands.*;
import me.shiyan.survive.events.*;

import me.shiyan.survive.skills.events.AttackSkills;
import me.shiyan.survive.skills.events.Backstab;
import me.shiyan.survive.skills.events.GetExp;
import me.shiyan.survive.skills.events.ToughSkin;
import me.shiyan.survive.stamina.PluginMenu;
import me.shiyan.survive.stamina.Stamina;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.saveResource("PlayerData.yml",false);
        new Config().loadConfig();
        new PlayerData().loadData();
        // Plugin startup logic
        getLogger().info(Config.PluginHead+"插件已成功加载");
        getLogger().info(Config.PluginHead+"插件作者qq:3273027266");
        getLogger().info(Config.PluginHead+"耐力值只在玩家进入服务器时载入,如果/reload将失效,需要重新加入");
        getCommand("reloadsur").setExecutor(new Reload());
        getCommand("openAttributeGui").setExecutor(new OpenSurvive());
        getCommand("survive").setExecutor(new TheCommands());
        //事件区域
        getServer().getPluginManager().registerEvents(new GetExp(),this);
        getServer().getPluginManager().registerEvents(new ShowDamage(),this);
        
        if(Config.config.getBoolean("ShowDamage"))
        {
            getServer().getPluginManager().registerEvents(new PlayerJoin(),this);
        }
        else
        {
            getLogger().info(Config.PluginHead+"关闭伤害显示");
        }
        if(Config.config.getBoolean("LoseBlood",true))
        {
            getServer().getPluginManager().registerEvents(new LoseBlood(),this);
        }
        else
        {
            getLogger().info(Config.PluginHead+"关闭流血成功");
        }
        //判断是否开启骨折
        if(Config.config.getBoolean("BoneBreak",true))
        {
            getServer().getPluginManager().registerEvents(new BoneBreak(),this);
        }
        //是否开启创口
        if(Config.config.getBoolean("Wound",true))
        {
            getServer().getPluginManager().registerEvents(new SecondaryTrauma(),this);
        }
        else
        {
            getLogger().info(Config.PluginHead+"关闭多次创伤成功");
        }
        //是否开启格挡反击
        if(Config.config.getBoolean("是否开启格挡反击",true))
        {
            getServer().getPluginManager().registerEvents(new Block(),this);
        }
        else
        {
            getLogger().info(Config.PluginHead+"关闭格挡反击成功");
        }
        getServer().getPluginManager().registerEvents(new Stamina(),this);
        getServer().getPluginManager().registerEvents(new PluginMenu(),this);
        getServer().getPluginManager().registerEvents(new AttackSkills(),this);
        getServer().getPluginManager().registerEvents(new ToughSkin(),this);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static Main getInstance()
    {
        return instance;
    }
}
