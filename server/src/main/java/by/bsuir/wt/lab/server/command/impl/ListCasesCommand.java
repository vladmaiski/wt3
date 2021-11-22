package by.bsuir.wt.lab.server.command.impl;

import by.bsuir.wt.lab.server.command.Command;
import by.bsuir.wt.lab.server.command.CommandResult;
import by.bsuir.wt.lab.server.model.AuthType;
import by.bsuir.wt.lab.server.model.StudentCase;
import by.bsuir.wt.lab.server.service.AuthorizationService;
import by.bsuir.wt.lab.server.service.StudentCaseService;

import java.util.List;

public class ListCasesCommand implements Command {

    @Override
    public CommandResult execute(Object caller, String request) {
        if (AuthorizationService.getInstance().getAuthType(caller) == AuthType.UNAUTHORIZED)
            return new CommandResult("Should be authenticated");

        List<StudentCase> studentCases = StudentCaseService.getInstance().getAll();
        return toOutput(studentCases);
    }

    private CommandResult toOutput(List<StudentCase> studentCases) {
        var resultBuilder = new StringBuilder();
        resultBuilder.append("[\n");
        for (var currentCase : studentCases) {
            resultBuilder.append("\t").append(currentCase.toString()).append("\n");
        }
        resultBuilder.append("]");
        return new CommandResult(resultBuilder.toString());
    }

}
