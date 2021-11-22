package by.bsuir.wt.lab.server.controller.filter.impl;

import by.bsuir.wt.lab.server.command.constant.CommandName;
import by.bsuir.wt.lab.server.command.exception.CommandException;
import by.bsuir.wt.lab.server.controller.filter.Filter;
import by.bsuir.wt.lab.server.model.AuthType;
import by.bsuir.wt.lab.server.service.AuthorizationService;

public class AccessFilter implements Filter {

    @Override
    public void doFilter(String request, Object caller) throws Exception {
        if (request == null) throw new CommandException("There is no command to do.");
        var commandName = request.split(" ")[0];
        var role = AuthorizationService.getInstance().getAuthType(caller);
        if (!isAccessAllowed(commandName, role)) {
            throw new IllegalArgumentException("You dont have permissions");
        }
    }

    private boolean isAccessAllowed(String commandName, AuthType role) {
        if (commandName == null) {
            return true;
        }
        return switch (commandName) {
            case CommandName.DISC, CommandName.LIST -> role.equals(AuthType.USER) || role.equals(AuthType.ADMIN);
            case CommandName.CREATE, CommandName.EDIT -> role.equals(AuthType.ADMIN);
            default -> true;
        };
    }

}
