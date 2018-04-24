package fr.bnjdpn.googlejavaformatfolder;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class Application {

    private static final String CURRENT_DIRECTORY = ".";
    private static final String JAVA_EXTENSION = ".java";
    private static final Formatter FORMATTER = new Formatter();

    public static void main(String[] args) throws FormatterException, IOException {
        formatAllJavaFiles(new File(CURRENT_DIRECTORY));
    }

    private static void formatAllJavaFiles(File currentDirectory) throws IOException, FormatterException {
        File[] files = currentDirectory.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                formatAllJavaFiles(file);
            } else if (isAJavaFile(file)) {
                formatFile(file);
            }
        }
    }

    private static boolean isAJavaFile(File file) {
        return file.isFile() && StringUtils.endsWith(file.getName(), JAVA_EXTENSION);
    }

    private static void formatFile(File file) throws IOException, FormatterException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(FORMATTER.formatSource(IOUtils
                .toString(FileUtils.openInputStream(file), Charset.defaultCharset())).getBytes());
        }
    }
}
