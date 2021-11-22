package by.bsuir.wt.lab.server.dao;

import by.bsuir.wt.lab.server.model.StudentCase;
import by.bsuir.wt.lab.server.service.StudentCaseService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StudentCaseDao {

    private static final StudentCaseDao INSTANCE = new StudentCaseDao();
    private static final String CASES_PATH = "./server/src/main/resources/cases.xml";

    private final ReadWriteLock lock;
    private final Map<Integer, StudentCase> studentCases;

    private StudentCaseDao() {
        lock = new ReentrantReadWriteLock();
        studentCases = new HashMap<>();
        init();
    }

    private void init() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(CASES_PATH));
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    StudentCase studentcase = StudentCaseService.getInstance().createCase(node.getChildNodes());
                    studentCases.put(studentcase.getId(), studentcase);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ignored) {
        }
    }

    public static StudentCaseDao getInstance() {
        return INSTANCE;
    }

    public boolean contains(int id) {
        return studentCases.containsKey(id);
    }

    public List<StudentCase> getAll() {
        try {
            lock.readLock().lock();
            return new ArrayList<>(studentCases.values());
        } finally {
            lock.readLock().unlock();
        }
    }

    public void add(StudentCase newStudentCase) {
        try {
            lock.writeLock().lock();
            newStudentCase.setId(studentCases.keySet().stream().max(Comparator.comparingInt(a -> a)).get() + 1);
            studentCases.put(newStudentCase.getId(), newStudentCase);
            update();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void setById(int id, StudentCase newStudentCase) {
        try {
            lock.writeLock().lock();
            newStudentCase.setId(id);
            studentCases.put(id, newStudentCase);
            update();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void update() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element rootEle = document.createElement("studentCases");
            for(StudentCase studentCase : getAll()) {
                Element caseElement = StudentCaseService.getInstance().createNode(document, studentCase);
                rootEle.appendChild(caseElement);
            }

            document.appendChild(rootEle);

            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(CASES_PATH)));

            } catch (IOException | TransformerException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}