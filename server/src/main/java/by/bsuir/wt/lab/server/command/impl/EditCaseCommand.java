package by.bsuir.wt.lab.server.command.impl;

import by.bsuir.wt.lab.server.command.Command;
import by.bsuir.wt.lab.server.command.CommandResult;
import by.bsuir.wt.lab.server.command.exception.CommandException;
import by.bsuir.wt.lab.server.model.AuthType;
import by.bsuir.wt.lab.server.service.AuthorizationService;
import by.bsuir.wt.lab.server.service.StudentCaseService;

public class EditCaseCommand implements Command {

    @Override
    public CommandResult execute(Object caller, String request) throws CommandException {
        var arguments = request.split(" ");
        if (arguments.length != 4) throw new CommandException("Invalid syntax EDIT");

        int id;
        try {
            id = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException ignored) {
            return new CommandResult("Invalid id");
        }

        if (!StudentCaseService.getInstance().containsCase(id))
            return new CommandResult("No such case");

        StudentCaseService.getInstance().editCase(id, arguments[2], arguments[3]);
        return new CommandResult("Success");
    }

}
