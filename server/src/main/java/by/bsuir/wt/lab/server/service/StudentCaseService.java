package by.bsuir.wt.lab.server.service;

import by.bsuir.wt.lab.server.dao.DaoFactory;
import by.bsuir.wt.lab.server.model.StudentCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class StudentCaseService {

    private static final StudentCaseService INSTANCE = new StudentCaseService();

    private StudentCaseService() {
    }

    public static StudentCaseService getInstance() {
        return INSTANCE;
    }

    public StudentCase createCase(NodeList nodes) {
        int id = 0;
        String first = "";
        String last = "";

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                String text = nodes.item(i).getTextContent();
                switch (nodes.item(i).getNodeName()) {
                    case "id" -> id = Integer.parseInt(text);
                    case "firstName" -> first = text;
                    case "lastName" -> last = text;
                    default -> throw new IllegalArgumentException("No such case exists");
                }
            }
        }

        return new StudentCase(id, first, last);
    }

    public Element createNode(Document doc, StudentCase studentCase) {
        Element e = doc.createElement("case");
        Element id = doc.createElement("id");
        Element first = doc.createElement("firstName");
        Element last = doc.createElement("lastName");
        id.appendChild(doc.createTextNode(String.valueOf(studentCase.getId())));
        first.appendChild(doc.createTextNode(studentCase.getFirstName()));
        last.appendChild(doc.createTextNode(studentCase.getLastName()));
        e.appendChild(id);
        e.appendChild(first);
        e.appendChild(last);
        return e;
    }

    public List<StudentCase> getAll() {
        return DaoFactory.getInstance().getCaseDao().getAll();
    }

    public boolean containsCase(int id) {
        return DaoFactory.getInstance().getCaseDao().contains(id);
    }

    public void editCase(int id, String firstName, String lastName) {
        DaoFactory.getInstance().getCaseDao().setById(id, new StudentCase(0, firstName, lastName));
    }

    public void addCase(String firstName, String lastName) {
        DaoFactory.getInstance().getCaseDao().add(new StudentCase(0, firstName, lastName));
    }

}