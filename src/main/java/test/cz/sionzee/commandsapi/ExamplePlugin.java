package test.cz.sionzee.commandsapi;

import cz.sionzee.commandsapi.CommandsAPI;
import org.bukkit.plugin.java.JavaPlugin;
import test.cz.sionzee.commandsapi.rank.RankManagerCommand;
import test.cz.sionzee.commandsapi.rank.RankMapper;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        RankMapper mapper = new RankMapper();
        CommandsAPI.INSTANCE.registerCommand("rank", RankManagerCommand.class, mapper);
    }
}
