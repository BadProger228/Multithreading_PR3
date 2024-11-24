import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;

import java.awt.Desktop;

class Main {
    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a folder to search for images");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = chooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No folder selected.");
            return;
        }

        File directory = chooser.getSelectedFile();
        if (!directory.isDirectory()) {
            System.out.println("The provided path is not a directory.");
            return;
        }

        ImageFinder task = new ImageFinder(directory);
        List<File> images = task.invoke();

        System.out.println("Found images: " + images.size());
        if (!images.isEmpty()) {
            File lastImage = images.get(images.size() - 1);
            System.out.println("Opening file: " + lastImage.getAbsolutePath());
            openFile(lastImage);
        } else {
            System.out.println("No images found.");
        }
    }

    private static void openFile(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.println("Unable to open file: " + file.getAbsolutePath());
        }
    }
}
