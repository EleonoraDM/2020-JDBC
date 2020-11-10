package spring.elldimi.bookshopadvquerying.utills;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class FileUtilImpl implements FileUtil {

    @Override
    public String[] readFileContent(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        Set<String> fileContent = new LinkedHashSet<>();
        reader.lines().forEach(s -> {
            if (!s.isEmpty() || !s.isBlank()) {
                fileContent.add(s);
            }
        });
        return fileContent.toArray(String[]::new);
    }

}
