import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
*Project 5 CSVReader
*
*this class reades CSV file and also prints it
* @author a
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @version November 13, 2023
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
    }
}
