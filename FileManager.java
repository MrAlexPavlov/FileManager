/*
 * Задача 2: Файловый менеджер (ООП, исключения)
 * Создайте класс FileManager.
 * Этот класс должен иметь методы для выполнения основных операций с файлами:
 * чтение, запись и копирование.
 * Каждый из этих методов должен выбрасывать соответствующее исключение,
 * если в процессе выполнения операции произошла ошибка (например, FileNotFoundException, если файл не найден).
*/

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class FileManager {

    FileManager() {
    }

    private File fileExist(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не существует:\n" + path + "\n");
        }
        return file;
    }

    private boolean readFilePermission(File file) throws PermissionException {
        if (!file.canRead()) {
            throw new PermissionException("Нет прав доступа на чтение файла:\n" + file.getAbsolutePath() + "\n");
            // return false;
        }
        return true;
    }

    private boolean writeFilePermission(File file) throws PermissionException {
        if (!file.canWrite()) {
            throw new PermissionException("Нет прав доступа на запись файла:\n" + file.getAbsolutePath() + "\n");
            // return false;
        }
        return true;
    }

    public String readFile(String path) throws IOException, FileNotFoundException, PermissionException {
        StringBuilder file_content = new StringBuilder();

        try {
            path = fileExist(path).getAbsolutePath();
            readFilePermission(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return file_content.toString();
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
            return file_content.toString();
        }
        try {
            FileReader fr = new FileReader(path);
            Scanner fscan = new Scanner(fr);

            while (fscan.hasNextLine()) {
                file_content.append(fscan.nextLine());
            }
            fscan.close();
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return file_content.toString();
        }

        return file_content.toString();
    }

    public boolean saveFile(String path, String text, boolean append)
            throws IOException, FileNotFoundException, PermissionException {
        path = new File(path).getAbsolutePath();

        File file = new File(path);

        if (file.exists()) {
            try {
                writeFilePermission(new File(path));
            } catch (PermissionException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        try {
            FileWriter writer = new FileWriter(path, append);
            writer.write(text);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean copyFiles(String source, String destenation, boolean rewrite) throws IOException, FileNotFoundException {
        File src, dst;
        dst = new File(destenation);

        if (dst.isDirectory()) {
            System.out.println("Это папка! Нужен путь с именем файла.");
            return false;
        }

        if (dst.exists()){
            if ( rewrite == false ){
            System.out.println("Файл: "+destenation+" - существует, требуется разрешение на перезапись!");
            return false;
            }
            dst.delete();
            dst = new File(destenation);
        }

        try {
            src = fileExist(source);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        Files.copy(src.toPath(), dst.toPath());
        return true;
    }

}

class FileNotFoundException extends Exception {
    public FileNotFoundException(String message) {
        super(message);
    }
}

class PermissionException extends Exception {
    public PermissionException(String message) {
        super(message);
    }
}
