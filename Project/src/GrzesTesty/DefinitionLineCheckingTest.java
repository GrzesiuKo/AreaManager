package GrzesTesty;

import FileData.FileChecker;

import java.util.LinkedList;
import java.util.Map;

public class DefinitionLineCheckingTest {
    static FileChecker fileChecker = new FileChecker();

    public static void main(String[] args) {
        LinkedList<Integer> kolej;
        Map<String, LinkedList<Integer>> argumentsOrders;
        String line = "3. ALA X double Y double FAS STRING";
        System.out.println(fileChecker.checkObjectDefinitionLine(line));
        argumentsOrders = fileChecker.getArgumentsOrders();
        kolej =argumentsOrders.get("ALA");
        System.out.println("Ala : ");
        String objectline = "wef ALA 45.6 fgdg.6 3245";
        System.out.println(fileChecker.checkObjectLine(objectline));

    }
}
