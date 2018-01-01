package test.cz.sionzee.commandsapi.rank;

import org.bukkit.command.CommandSender;

public class Rank {

    private static Rank getFromDatabase(String rankName) {
        return null;
    }

    /**
     * Returns rank or null when not exists
     * @param text
     * @return
     */
    public static Rank from(String text) {
        return getFromDatabase(text);
    }

    public static boolean create(CommandSender sender, String rankName) {
        return createRank(sender, rankName);
    }

    /**
     * Returns true if rank was successfully created.
     * @param sender
     * @param rankName
     * @return
     */
    private static boolean createRank(CommandSender sender, String rankName) {
        return true;
    }
}
