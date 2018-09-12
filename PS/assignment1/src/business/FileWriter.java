package business;

import model.EventEmployee;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileWriter {


    public FileWriter()  {
    }

    public static void writeReport(ArrayList<EventEmployee> list)  {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new java.io.FileWriter("report.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (EventEmployee eventEmployee : list){
            try {
                out.write(eventEmployee.toString());
                out.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
