package by.bsuir.wt.lab.server.command.impl;

import by.bsuir.wt.lab.server.command.Command;
import by.bsuir.wt.lab.server.command.CommandResult;
import by.bsuir.wt.lab.server.command.exception.CommandException;
import by.bsuir.wt.lab.server.model.AuthType;
import by.bsuir.wt.lab.server.service.AuthorizationService;

public class AuthorizeCommand implements Command {

    @Override
    public CommandResult execute(Object caller, String request) throws CommandException {
        var arguments = request.split(" ");
        if (arguments.length != 2) throw new CommandException("AUTH command should contain 1 argument");
        AuthType authType;
        try {
            authType = AuthType.valueOf(arguments[1]);
        } catch (IllegalArgumentException e) {
            throw new CommandException("No such auth type");
        }

        AuthorizationService.getInstance().setAuthType(caller, authType);
        return new CommandResult("Success.");
    }

}
