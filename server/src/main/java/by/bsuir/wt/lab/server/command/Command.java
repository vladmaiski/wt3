package by.bsuir.wt.lab.server.command;

import by.bsuir.wt.lab.server.command.exception.CommandException;

public interface Command {

    CommandResult execute(Object caller, String request) throws CommandException;

}
