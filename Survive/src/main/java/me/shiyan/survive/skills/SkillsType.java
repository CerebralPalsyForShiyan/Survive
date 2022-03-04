package me.shiyan.survive.skills;

import me.shiyan.survive.PlayerData;

//Thump 重击  Tough_Skin 坚韧皮肤
public enum SkillsType {
    THUMP{
        @Override
        public String getSkillName() {
            return "重击";
        }

        @Override
        public String getSkillPath(String name) {
            return name+ PlayerData.SkillsPath+".重击";
        }

    },
    TOUGH_SKIN{
        @Override
        public String getSkillName() {
            return "坚韧皮肤";
        }

        @Override
        public String getSkillPath(String name) {
            return name+PlayerData.SkillsPath+".坚韧皮肤";
        }
    };
    public abstract String getSkillName();
    public abstract String getSkillPath(String name);
}
