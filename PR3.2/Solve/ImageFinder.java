
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.RecursiveTask;

public class ImageFinder extends RecursiveTask<List<File>> {
    private final File directory;

    public ImageFinder(File directory) {
        this.directory = directory;
    }

    @Override
    protected List<File> compute() {
        List<File> images = new ArrayList<>();
        List<ImageFinder> subTasks = new ArrayList<>();

        File[] files = directory.listFiles();
        if (files == null)
            return images; 

        for (File file : files) {
            if (file.isDirectory()) {
                ImageFinder task = new ImageFinder(file);
                task.fork(); 
                subTasks.add(task);
            } else if (isImage(file)) {
                images.add(file);
            }
        }

        for (ImageFinder task : subTasks) {
            images.addAll(task.join());
        }
        

        return images;
    }

    private boolean isImage(File file) {
        String mimeType;
        try {
            mimeType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            return false; // Якщо не вдалося визначити тип файлу
        }
        return mimeType != null && mimeType.startsWith("image");
    }

   
    
}
