public class Manager {
    public static void main(String[] args) {
        FileManager fManager = new FileManager();
        String path = "new.txt";

        try {
            fManager.saveFile(path, "123456", true);    
            fManager.copyFiles(path, "copy_new.txt",true);
            String text = fManager.readFile(path);
            System.out.println(text);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
