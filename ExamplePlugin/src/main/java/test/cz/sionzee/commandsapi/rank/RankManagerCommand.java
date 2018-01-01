package test.cz.sionzee.commandsapi.rank;

import cz.sionzee.commandsapi.Command;
import cz.sionzee.commandsapi.annotations.SubCommand;
import org.jetbrains.annotations.NotNull;

public class RankManagerCommand extends Command {
    @Override
    public boolean onCommand(@NotNull String[] args) {
        if(args.length <= 0) {
            list();
        }
        return false;
    }

    @SubCommand
    public void add(Rank rank) {
        if(rank != null) {
            sendMessage("Trying to create already exists rank. Please choose another name.");
            return;
        }

        if(Rank.create(getSender(), arg(0))) {
            sendMessage("Rank", arg(0), "was successfully created.");
        } else {
            sendMessage("Error when creating rank", arg(0), "try it later.");
        }
    }

    @SubCommand
    public void remove(Rank rank) {
        if(rank == null) {
            sendMessage("Trying to remove non-existent rank.");
            return;
        }

        //TODO: Remove...
    }

    @SubCommand
    public void list() {
        sendMessage("--- list of commands ---");
        getAvailableCommands().forEach(this::sendMessage);
    }

    @Override
    public void onInvalidSubcommand(@NotNull String subCommand, @NotNull String fullCommand) {
        list();
    }
}
