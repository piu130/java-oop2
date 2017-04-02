package test.test3;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class FileManager {

    /**
     * Ask for a file with the given extension filter.
     * @param title FileChooser title
     * @param extensionFilters ExtensionFilter to filter files
     * @return File chosen
     */
    public static File askForFile(String title, final FileChooser.ExtensionFilter... extensionFilters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        for (FileChooser.ExtensionFilter filter : extensionFilters) {
            fileChooser.getExtensionFilters().add(filter);
        }
        fileChooser.setInitialDirectory(new File("."));
        return fileChooser.showOpenDialog(new Stage());
    }

    /**
     * Converts multiple files. Removes the old separator, adds the new separator. Adds new file ending to file end.
     * @param oldSeparator Separator to replace
     * @param newSeparator Separator replaced by
     * @param newFileEnding New file ending
     * @param files Files to convert
     */
    public static void convertFiles(char oldSeparator, char newSeparator, String newFileEnding, final File... files) {
        for (File file : files) convertFile(oldSeparator, newSeparator, newFileEnding, file);
    }

    /**
     * Converts a file. Removes the old separator, adds the new separator. Adds new file ending to file end.
     * @param oldSeparator Separator to replace
     * @param newSeparator Separator replaced by
     * @param newFileEnding New file ending
     * @param file File to convert
     */
    public static void convertFile(char oldSeparator, char newSeparator, String newFileEnding, final File file) {
        try (
                DataInputStream dis = new DataInputStream(new FileInputStream(file));
                PrintWriter pw = new PrintWriter(file.getName() + newFileEnding)
        ) {
            while (dis.available() > 0) {
                char character = dis.readChar();
                if (character == oldSeparator) {
                    pw.println(String.valueOf(newSeparator) + dis.readInt());
                } else {
                    pw.print(character);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
