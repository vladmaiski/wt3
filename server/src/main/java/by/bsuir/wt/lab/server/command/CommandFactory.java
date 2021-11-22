package by.bsuir.wt.lab.server.command;

import by.bsuir.wt.lab.server.command.constant.CommandName;
import by.bsuir.wt.lab.server.command.exception.CommandException;
import by.bsuir.wt.lab.server.command.impl.*;

public class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory();

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    public Command createCommand(String request) throws CommandException {
        var commandName = request.split(" ")[0];
        return switch (commandName) {
            case CommandName.AUTH -> new AuthorizeCommand();
            case CommandName.DISC -> new DisconnectCommand();
            case CommandName.EDIT -> new EditCaseCommand();
            case CommandName.LIST -> new ListCasesCommand();
            case CommandName.CREATE -> new CreateCaseCommand();
            default -> throw new CommandException("No such command");
        };
    }

}
