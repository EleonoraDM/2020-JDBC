package spring.elldimi.bookshopadvquerying.utills;

import java.io.FileNotFoundException;

public interface FileUtil {
    String[] readFileContent(String filePath) throws FileNotFoundException;
}
