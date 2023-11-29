import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
*project 4 CSVReader
*
*this class reades CSV file and also prints it
*/
public class CSVReader {
    public static void readCSV(String s) {
        try (BufferedReader br = new BufferedReader(new FileReader(s))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
