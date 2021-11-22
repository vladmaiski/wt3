package by.bsuir.wt.lab.server.command.impl;

import by.bsuir.wt.lab.server.command.Command;
import by.bsuir.wt.lab.server.command.CommandResult;
import by.bsuir.wt.lab.server.command.exception.CommandException;
import by.bsuir.wt.lab.server.service.StudentCaseService;

public class CreateCaseCommand implements Command {

    @Override
    public CommandResult execute(Object caller, String request) throws CommandException {
        var arguments = request.split(" ");
        if (arguments.length != 3) throw new CommandException("CREATE invalid syntax");

        StudentCaseService.getInstance().addCase(arguments[1], arguments[2]);
        return new CommandResult("Success");
    }

}
