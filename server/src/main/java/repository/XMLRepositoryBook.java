package repository;

import domain.Book;
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

public class XMLRepositoryBook extends InMemoryRepository<Long, Book> {

    private String fileName;

    public XMLRepositoryBook(Validator<Book> validator, String fileName) throws Exception {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private static void saveBook(Book book1) throws Exception {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                //.parse("C:\\Faculty\\MPP\\LibraryApp\\book_library\\src\\main\\resources\\BookXML");
                .parse("C:\\Users\\Birhan\\Desktop\\Mpp proiecte\\Library_app\\src\\main\\resources\\BookXML");
        Element root = document.getDocumentElement();

        Element bookElement = document.createElement("book");
        bookElement.setAttribute("id", String.valueOf(book1.getId()));
        root.appendChild(bookElement);

        appendChildWithText(document, bookElement, "serialnumber", book1.getSerialNumber());
        appendChildWithText(document, bookElement, "name", book1.getName());
        appendChildWithText(document, bookElement, "author", book1.getAuthor());
        appendChildWithText(document, bookElement, "price",
                String.valueOf(book1.getPrice()));

        Transformer transformer =
                TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root),
                new StreamResult(new FileOutputStream(
                        //"C:\\Faculty\\MPP\\LibraryApp\\book_library\\src\\main\\resources\\BookXML")));
                        "C:\\Users\\Birhan\\Desktop\\Mpp proiecte\\Library_app\\src\\main\\resources\\BookXML")));
    }


    @Override
    public Optional<Book> save(Optional<Book> entity) throws ValidatorException {
        Optional<Book> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        try {
            saveBook(entity.get());
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

    private static Book createBook(Element bookNode) {
        String id = bookNode.getAttribute("id");
        Long newId = Long.parseLong(id);
        Book b = new Book();
        b.setId(newId);

        b.setSerialNumber(getTextFromTagName(bookNode, "serialnumber"));
        b.setName(getTextFromTagName(bookNode, "name"));
        b.setAuthor(getTextFromTagName(bookNode, "author"));
        b.setPrice(Integer.valueOf(getTextFromTagName(bookNode, "price")));
        return b;
    }


    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }


    public static List<Book> loadData() throws Exception {
        List<Book> listOfBooks = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder =
                documentBuilderFactory.newDocumentBuilder();
        //Document document = documentBuilder.parse("C:\\Faculty\\MPP\\LibraryApp\\book_library\\src\\main\\resources\\BookXML");
        Document document = documentBuilder.parse("C:\\Users\\Birhan\\Desktop\\AN 2 Sem 2\\MPP\\Mpp proiecte\\Library_app\\src\\main\\resources\\BookXML");
        Element root = document.getDocumentElement();

        NodeList nodes = root.getChildNodes();
        int len = nodes.getLength();
        for (int i = 0; i < len; i++) {
            Node bookNode = nodes.item(i);
            if (bookNode instanceof Element) {
                Book b = createBook((Element) bookNode);
                listOfBooks.add(b);
            }
        }
        return listOfBooks;
    }


    public Iterable<Book> findAll(){
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
        Document document = documentBuilder.parse("C:\\Users\\Birhan\\Desktop\\AN 2 Sem 2\\MPP\\Mpp proiecte\\Library_app\\src\\main\\resources\\BookXML");
        //Document document = documentBuilder.parse("C:\\Users\\Birhan\\Desktop\\Mpp proiecte\\Library_app\\src\\main\\resources\\BookXML");

        NodeList nodes = document.getElementsByTagName("book");
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
                        //"C:\\Faculty\\MPP\\LibraryApp\\book_library\\src\\main\\resources\\BookXML")));
                      "C:\\Users\\Birhan\\Desktop\\AN 2 Sem 2\\MPP\\Mpp proiecte\\Library_app\\src\\main\\resources\\BookXML")));
    }


    public Optional<Book> delete(Optional<Long> id){
        try {
            deleteXML(id.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
