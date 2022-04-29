package currencyExchange;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    String numCode = null;
    String charCode = null;
    String nominal = null;
    String name = null;
    String value = null;

    public static float num1;
    public static float num2;
    public static float result;

    List<CurrencyData> currencyDataList = new ArrayList<>();


    public void Parse() throws ParserConfigurationException, IOException, SAXException {
        URL url = new URL("https://cbr.ru/scripts/XML_daily.asp");
        URLConnection conn = url.openConnection();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(conn.getInputStream());
        doc.getDocumentElement().normalize();

        NodeList valute = doc.getElementsByTagName("Valute");
        for (int i = 0; i < valute.getLength(); i++) {
            NodeList valuteElement = (NodeList) valute.item(i);
            for (int j = 0; j < valuteElement.getLength(); j++) {
                Node detail = valuteElement.item(j);
                /*System.out.println("    " + detail.getTextContent() +";");*/

                switch (detail.getNodeName()) {
                    case "NumCode": {
                        numCode = detail.getTextContent();

                    }
                    case "CharCode": {
                        charCode = detail.getTextContent();

                    }
                    case "Nominal": {
                        nominal = detail.getTextContent();

                    }
                    case "Name": {
                        name = detail.getTextContent();
                    }
                    case "Value": {
                        value = detail.getTextContent();
                    }
                }

            }
            CurrencyData currencyData = new CurrencyData(numCode, charCode, nominal, name, value);
            currencyDataList.add(currencyData);


        }

    }

    public void service() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите валюту 1 ");
        String userEntryFirst = sc.next();
        System.out.println("Введите валюту 2");
        String userEntrySecond = sc.next();
        System.out.println("Введите количество");
        int amount = sc.nextInt();

        Collator coll = Collator.getInstance();


        for (int y = 0; y < currencyDataList.size(); y++) {


            boolean i = coll.equals(userEntryFirst, currencyDataList.get(y).getCharCode());
            boolean j = coll.equals(userEntrySecond, currencyDataList.get(y).getCharCode());
            if (i) {

                System.out.println(currencyDataList.get(y).getName());
                String val1 = currencyDataList.get(y).getValue();
                val1 = val1.replace(",", ".");
                System.out.println(val1);
                num1 = Float.parseFloat(val1);
            }
            if (j) {
                System.out.println(currencyDataList.get(y).getName());
                String val2 = currencyDataList.get(y).getValue();
                val2 = val2.replace(",", ".");
                System.out.println(val2);
                num2 = Float.parseFloat(val2);

            }


        }
        result = (num1 / num2) * amount;
        System.out.println(result);


    }


}
