package by.bsuir.wt.lab.server.controller.filter;

import by.bsuir.wt.lab.server.command.exception.CommandException;

public interface Filter {

    void doFilter(String request, Object caller) throws Exception;

}
