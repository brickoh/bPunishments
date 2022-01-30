package me.ethan.bpunishments.feedback;

import me.ethan.bpunishments.bPunishments;

public class Feedback {


    /*
    Staff
     */
    public static String STAFF_PUNISHMENT_SENT = bPunishments.getInstance().getLangYML().getString("staff.punishment-sent");
    public static String STAFF_PUNISHMENT_REVOKED = bPunishments.getInstance().getLangYML().getString("staff.punishment-revoked");
    public static String STAFF_PUNISHMENT_JOIN_ATTEMPT = bPunishments.getInstance().getLangYML().getString("staff.punishment-join-attempt");

    /*
    Global
     */
    public static String GLOBAL_PUNISHMENT_SENT = bPunishments.getInstance().getLangYML().getString("global.punishment-sent");
    public static String GLOBAL_PUNISHMENT_REVOKED = bPunishments.getInstance().getLangYML().getString("global.punishment-revoked");

    /*
    Kick
     */
    public static String KICK_BAN = bPunishments.getInstance().getLangYML().getString("kick.ban");
}
