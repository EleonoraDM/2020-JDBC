package com.elldimi.springdataex.utills;

import java.io.FileNotFoundException;

public interface FileUtil {
    String[] readFileContent(String filePath) throws FileNotFoundException;
}
