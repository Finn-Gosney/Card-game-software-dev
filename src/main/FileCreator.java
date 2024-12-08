import java.io.File;
import java.io.IOException;

public class FileCreator {
    public static File createFile(int number, boolean isDeck) {
        File outputFile;
        if(isDeck)
        {
            outputFile = new File("Deck " + number + " Output.txt");
        try {
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        }      
        else
        {
            outputFile = new File("Player " + number + " Output.txt");
        try {
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        } 

        return outputFile;
    }
}
