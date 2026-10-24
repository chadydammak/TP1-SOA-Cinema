

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        // Fonctionnalité 1 : validation XML/XSD
        validerXML();

        System.out.println();

        // Fonctionnalité 2 : interrogation XPath
        interrogerXPath();
    }

    public static void validerXML() {

        System.out.println("=== VALIDATION XML/XSD ===");

        try {

            File xmlFile = new File("cinema.xml");
            File xsdFile = new File("cinema.xsd");

            SchemaFactory factory =
                    SchemaFactory.newInstance(
                            XMLConstants.W3C_XML_SCHEMA_NS_URI
                    );

            Schema schema = factory.newSchema(xsdFile);

            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(xmlFile));

            System.out.println(
                    "Le document XML est valide !"
            );

        } catch (Exception e) {

            System.out.println(
                    "Erreur de validation : " + e.getMessage()
            );
        }
    }

    public static void interrogerXPath() {

        System.out.println("=== INTERROGATION XPATH ===");

        try {

            // Étape 1 : lire le document XML
            File xmlFile = new File("cinema.xml");

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document document = builder.parse(xmlFile);

            // Étape 2 : créer le moteur XPath
            XPathFactory xpathFactory =
                    XPathFactory.newInstance();

            XPath xpath = xpathFactory.newXPath();

            // Requête 1 : tous les titres
            XPathExpression expression1 =
                    xpath.compile("/cinema/film/titre");

            NodeList titres = (NodeList) expression1.evaluate(
                    document,
                    XPathConstants.NODESET
            );

            System.out.println("\nTous les titres :");

            for (int i = 0; i < titres.getLength(); i++) {

                System.out.println(
                        "- " + titres.item(i).getTextContent()
                );
            }

            // Requête 2 : films sortis après 2000
            XPathExpression expression2 =
                    xpath.compile(
                            "/cinema/film[annee > 2000]/titre"
                    );

            NodeList filmsRecents = (NodeList) expression2.evaluate(
                    document,
                    XPathConstants.NODESET
            );

            System.out.println(
                    "\nFilms sortis après 2000 :"
            );

            for (int i = 0; i < filmsRecents.getLength(); i++) {

                System.out.println(
                        "- " + filmsRecents.item(i).getTextContent()
                );
            }

            // Requête 3 : titre du film F1
            XPathExpression expression3 =
                    xpath.compile(
                            "/cinema/film[@id='F1']/titre"
                    );

            NodeList filmF1 = (NodeList) expression3.evaluate(
                    document,
                    XPathConstants.NODESET
            );

            System.out.println("\nFilm avec l'identifiant F1 :");

            for (int i = 0; i < filmF1.getLength(); i++) {

                System.out.println(
                        "- " + filmF1.item(i).getTextContent()
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Erreur XPath : " + e.getMessage()
            );
        }
    }
}