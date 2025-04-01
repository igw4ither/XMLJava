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

        boolean selectionMenu = true;
        boolean booleanDefaultPath = false;
        String defaultPath = "";

        do {
            System.out.println("Selecciona una opción");
            System.out.println("(1) Consultas predeterminadas");
            System.out.println("(2) Consultas personalizadas");

            if (booleanDefaultPath == true) {
                System.out.println("(3) Ruta archivo XML predeterminada: Activado");
            } else {
                System.out.println("(3) Ruta archivo XML predeterminada: Desactivado");
            }

            int selection = sc.nextInt();

            switch (selection) {
                case 1:
                    //Ruta absoluta del archivo XML
                    String PathFile;
                    if (defaultPath.isEmpty()) {
                        System.out.print("Escribe la ruta del archivo XML: ");
                        PathFile = sc.next();
                    } else {
                        PathFile = defaultPath;
                    }
                    System.out.println("(1) Mostrar el satélite con nombre Luna");
                    System.out.println("(2) Mostrar todos los satélites");
                    System.out.println("(3) Mostrar todos los planetas");
                    System.out.print("Escoge una opción: ");

                    int option = sc.nextInt();

                    System.out.print(ReadXML(PathFile, option));

                    break;
                case 2:
                    System.out.print("Escribe la ruta del archivo XML: ");
                    PathFile = sc.next();
                    System.out.print("Escribe la expresión para buscar en el archivo XML: ");
                    String xPathExpression = sc.next();
                    System.out.print(ReadXML(PathFile, xPathExpression));

                    break;
                case 3:
                    booleanDefaultPath = !(booleanDefaultPath);

                    if (booleanDefaultPath == true) {
                        System.out.print("Escribe la ruta predeterminada: ");
                        defaultPath = sc.next();
                    } else {
                        defaultPath = "";
                    }
                    break;
                case 0:
                    System.out.print("Programa finalizado");
                    selectionMenu = false;
                    sc.close();
                default:
                    System.out.print("Selecciona una opción correcta");
            }
        } while (selectionMenu);
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