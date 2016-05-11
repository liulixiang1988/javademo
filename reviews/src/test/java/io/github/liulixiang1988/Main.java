package io.github.liulixiang1988;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CSVPrinter printer = new CSVPrinter(System.out, CSVFormat.EXCEL);
            printer.printRecord("Liu", "Lixiang", 1988);
            printer.printRecord("L", "L", 1988);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
