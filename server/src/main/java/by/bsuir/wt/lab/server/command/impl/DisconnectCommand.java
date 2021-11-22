package by.bsuir.wt.lab.server.command.impl;

import by.bsuir.wt.lab.server.command.Command;
import by.bsuir.wt.lab.server.command.CommandResult;
import by.bsuir.wt.lab.server.command.exception.CommandException;
import by.bsuir.wt.lab.server.model.AuthType;
import by.bsuir.wt.lab.server.service.AuthorizationService;

public class DisconnectCommand implements Command {

    @Override
    public CommandResult execute(Object caller, String request) {
        AuthorizationService.getInstance().setAuthType(caller, AuthType.UNAUTHORIZED);
        return new CommandResult("Good luck!");
    }

}
