package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Containers {


    String fileName1 = "Exchange Rate.txt";


    public List<String> readFile(String fileName) {
        String linia = null;
        List<String> rate = new ArrayList<String>();

        try {
            // Uworzenie obiektu FileReader
            FileReader fileReader = new FileReader(fileName);

            // Utworzenie obiektu bufferReader
            BufferedReader bufferReader = new BufferedReader(fileReader);

            while ((linia = bufferReader.readLine()) != null) {
                rate.add(linia);
                System.out.println(linia);
            }
            fileReader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rate;

    }


}
