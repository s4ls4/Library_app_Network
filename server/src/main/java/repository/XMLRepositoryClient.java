package repository;

import domain.Client;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Deprecated
public class XMLRepositoryClient extends InMemoryRepository<Long, Client> {

    private String fileName;

    public XMLRepositoryClient(Validator<Client> validator, String fileName) throws Exception {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private static void saveClient(Client Client1) throws Exception {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("C:\\Faculty\\MPP\\LibraryApp\\book_library\\src\\main\\resources\\ClientXML");
                //.parse("C:\\Users\\Birhan\\Desktop\\Mpp proiecte\\Library_app\\src\\main\\resources\\ClientXML");
        Element root = document.getDocumentElement();

        Element ClientElement = document.createElement("Client");
        ClientElement.setAttribute("id", String.valueOf(Client1.getId()));
        root.appendChild(ClientElement);

        appendChildWithText(document, ClientElement, "serialnumber", Client1.getSerialNumber());
        appendChildWithText(document, ClientElement, "name", Client1.getName());
        appendChildWithText(document, ClientElement, "spent",
                String.valueOf(Client1.getSpent()));

        Transformer transformer =
                TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root),
                new StreamResult(new FileOutputStream(
                        "C:\\Faculty\\MPP\\LibraryApp\\book_library\\src\\main\\resources\\ClientXML")));
                       // "C:\\Users\\Birhan\\Desktop\\Mpp proiecte\\Library_app\\src\\main\\resources\\ClientXML")));
    }


    @Override
    public Optional<Client> save(Optional<Client> entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        try {
            saveClient(entity.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }




    private static void appendChildWithText(Document document,
                                            Node parent, String tagName, String textContent) {

        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    private static Client createClient(Element ClientNode) {
        String id = ClientNode.getAttribute("id");
        Long newId = Long.parseLong(id);
        Client b = new Client();
        b.setId(newId);

        b.setSerialNumber(getTextFromTagName(ClientNode, "serialnumber"));
        b.setName(getTextFromTagName(ClientNode, "name"));
        b.setSpent(Integer.valueOf(getTextFromTagName(ClientNode, "spent")));
        return b;
    }


    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }


    public static List<Client> loadData() throws Exception {
        List<Client> listOfClients = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder =
                documentBuilderFactory.newDocumentBuilder();
        //Document document = documentBuilder.parse("C:\\Faculty\\MPP\\LibraryApp\\book_library\\src\\main\\resources\\ClientXML");
        Document document = documentBuilder.parse("C:\\Users\\Birhan\\Desktop\\AN 2 Sem 2\\MPP\\Mpp proiecte\\Library_app\\src\\main\\resources\\ClientXML");
        Element root = document.getDocumentElement();

        NodeList nodes = root.getChildNodes();
        int len = nodes.getLength();
        for (int i = 0; i < len; i++) {
            Node ClientNode = nodes.item(i);
            if (ClientNode instanceof Element) {
                Client b = createClient((Element) ClientNode);
                listOfClients.add(b);
            }
        }
        return listOfClients;
    }

    public Iterable<Client> findAll(){
        try {
            return loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void deleteXML(Long id) throws Exception{

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        //Document document = documentBuilder.parse("C:\\Faculty\\MPP\\LibraryApp\\book_library\\src\\main\\resources\\ClientXML");
        Document document = documentBuilder.parse("C:\\Users\\Birhan\\Desktop\\AN 2 Sem 2\\MPP\\Mpp proiecte\\Library_app\\src\\main\\resources\\ClientXML");

        NodeList nodes = document.getElementsByTagName("Client");
        for (int i = 0; i < nodes.getLength(); i++){
            Element element = (Element)nodes.item(i);
            Long auxID = Long.valueOf(element.getAttribute("id"));
            if (auxID.longValue() == id){
                element.getParentNode().removeChild(element);
            }
        }
        Element root = document.getDocumentElement();
        Transformer transformer =
                TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root),
                new StreamResult(new FileOutputStream(
                       // "C:\\Faculty\\MPP\\LibraryApp\\book_library\\src\\main\\resources\\ClientXML")));
                        "C:\\Users\\Birhan\\Desktop\\AN 2 Sem 2\\MPP\\Mpp proiecte\\Library_app\\src\\main\\resources\\ClientXML")));
    }


    public Optional<Client> delete(Optional<Long> id){
        try {
            deleteXML(id.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
