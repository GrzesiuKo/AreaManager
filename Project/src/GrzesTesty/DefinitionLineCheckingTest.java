package GrzesTesty;

import FileData.FileChecker;

import java.util.LinkedList;
import java.util.Map;

public class DefinitionLineCheckingTest {
    static FileChecker fileChecker = new FileChecker();

    public static void main(String[] args) {
        LinkedList<Integer> kolej;
        Map<String, LinkedList<Integer>> argumentsOrders;
        String line = "3. ALA X double Y double gdas tjrt";
        System.out.println(fileChecker.checkObjectDefinitionLine(line));
        argumentsOrders = fileChecker.getArgumentsOrders();
        kolej =argumentsOrders.get("ALA");
        System.out.println("Ala : ");
        System.out.println(kolej.removeFirst());
        System.out.println(kolej.removeFirst());
        System.out.println(kolej.removeFirst());

    }
}
