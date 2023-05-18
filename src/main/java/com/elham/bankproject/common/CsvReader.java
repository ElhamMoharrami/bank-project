package com.elham.bankproject.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private boolean validDirectory = true;
    private static final Logger logger = LogManager.getLogger(CsvReader.class);

    public List<String> readFile(String fileLoc) {
        List<String> list = new ArrayList<>();
        Path path = Paths.get(fileLoc);
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String currentLine = null;
                int iteration = 0;
                while ((currentLine = reader.readLine()) != null) {
                    if (iteration == 0) {
                        iteration++;
                        continue;
                    }
                    list.add(currentLine);
                }
            } catch (IOException e) {
                logger.error("something went wrong,could not read the file");
            }
        } else {
            logger.error("No such directory");
            this.validDirectory = false;
        }
        return list;
    }

    public boolean isValidDirectory() {
        return validDirectory;
    }
}

