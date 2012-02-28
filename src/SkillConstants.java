// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public class SkillConstants {

    public static int amt_skills = 25;
	
    public static String skill_names[] = {
        "attack", "defence", "strength", "hitpoints", "ranged", "prayer", "magic", "cooking", "woodcutting", "fletching", 
        "fishing", "firemaking", "crafting", "smithing", "mining", "herblore", "agility", "thieving", "slayer", "farming", 
        "runecraft", "-unused-", "-unused-", "-unused-", "-unused-"
    };
	
    public static boolean skill_active[] = {
        true, true, true, true, true, true, true, true, true, true, 
        true, true, true, true, true, true, true, true, true, false, 
        true, false, false, false, false
    };
}
