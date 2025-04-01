import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean doIt = true;

        System.out.println("Selecciona una opción");
        System.out.println("(1) Consultas predeterminadas");
        System.out.println("(2) Consultas personalizadas");

        do {
            int selection = sc.nextInt();

            switch (selection) {

                case 1:
                    //Ruta absoluta del archivo XML
                    String PathFile = sc.next();

                    System.out.println("(1) Mostrar el satélite con nombre Luna");
                    System.out.println("(2) Mostrar todos los satélites");
                    System.out.println("(3) Mostrar todos los planetas");
                    System.out.print("Escoge una opción: ");
                    int option = sc.nextInt();
                    

                    System.out.print(ReadXML(PathFile, option));

                    break;
                case 2:
                    PathFile = sc.next();
                    String xPathExpression = sc.next();
                    System.out.print(ReadXML(PathFile, xPathExpression));

                    break;
            }
        } while (true);

    }

    private static StringBuilder ReadXML (String pathFile, int option) throws Exception {
        StringBuilder XMLFile = new StringBuilder();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(new File(pathFile));

        // Preparación de xpath
        XPath xpath = XPathFactory.newInstance().newXPath();

        String xPathExpression = "";

        if (option == 1) {
            xPathExpression = "//satelite[@nombre='Luna']";
        } else if (option == 2) {
            xPathExpression = "//satelite";
        } else if (option == 3) {
            xPathExpression ="//planeta";
        } else {
            System.out.print("Selection una opción correcta");
        }
        // Consultas
        NodeList nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

        for (int i=0;i<nodos.getLength();i++){
            XMLFile.append(nodos.item(i).getNodeName()).append(" : ").append(nodos.item(i).getAttributes().getNamedItem("nombre"));
        }

        return XMLFile;
    }
    
    private static StringBuilder ReadXML (String PathFile, String xPathExpression) throws Exception {
        StringBuilder XMLFile = new StringBuilder();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(new File(PathFile));

        // Preparación de xpath
        XPath xpath = XPathFactory.newInstance().newXPath();


        // Consultas
        NodeList nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);


        for (int i = 0; i<nodos.getLength(); i++){
           XMLFile.append(nodos.item(i).getNodeName()).append(": ").append(nodos.item(i).getAttributes().getNamedItem("nombre"));
        }

        return XMLFile;
    }

}