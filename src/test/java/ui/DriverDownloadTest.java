package ui;

import com.codeborne.selenide.Selenide;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class DriverDownloadTest {
    static Controller controller = new Controller();
    private static MainPage mainPage = new MainPage();
    private static String downloadsFolder = "";
    private static String userName = RandomStringUtils.randomAlphabetic(10);
    private static String userEmail;
    private static String password = "123456789a";

    @BeforeClass
    public static void userCreation() throws InterruptedException, MessagingException, IOException {

        controller.createRegistrationData();
        userEmail = controller.userEmail;
        controller.fullShortRegistrationForm(
                userName,
                userEmail,
                controller.countryNumber,
                1,
                password,
                password
        );
        controller.activateAccount();
        downloadsFolder = controller.getDownloadsFolderPath();
    }

    @AfterClass
    public static void userDeletion() throws InterruptedException {
        Selenide.open("https://stage.rcgtiming.com/");
        controller.userLogOut();
        controller.deleteUser();
    }

    @Test
    public void fullPackageZipShouldContainsConfigFolderPhpFolderDebugFileDriverFileStartFile() throws InterruptedException, IOException {

        downloadPackage("", "", "", "Full Package");

        HashMap<String, Boolean> fullPackage = new HashMap<>();
            fullPackage.put("debug.bat", true);
            fullPackage.put("rcgt-driver.php", true);
            fullPackage.put("start.bat", true);
            fullPackage.put("configFolder", true);
            fullPackage.put("phpFolder", true);

        Assert.assertEquals(fullPackage, archiveContains());
    }
/*
    @Test
    public void configurationUpdateZipShouldContainsConfigFolder() throws InterruptedException, IOException {

        downloadPackage("", "", "", "Configuration Update");

        HashMap<String, Boolean> configurationUpdate = new HashMap<>();
            configurationUpdate.put("debug.bat", false);
            configurationUpdate.put("rcgt-driver.php", false);
            configurationUpdate.put("start.bat", false);
            configurationUpdate.put("configFolder", true);
            configurationUpdate.put("phpFolder", false);

        Assert.assertEquals(configurationUpdate, archiveContains());
    }

    @Test
    public void driverUpdateZipShouldContainsDebugFileDriverFileStartFile() throws InterruptedException, IOException {

        downloadPackage("", "", "", "Driver Update");

        HashMap<String, Boolean> driverUpdate = new HashMap<>();
            driverUpdate.put("debug.bat", true);
            driverUpdate.put("rcgt-driver.php", true);
            driverUpdate.put("start.bat", true);
            driverUpdate.put("configFolder", false);
            driverUpdate.put("phpFolder", false);

        Assert.assertEquals(driverUpdate, archiveContains());
    }

    @Test
    public void configFileShouldContainsDecoderTypeAMBIfSelectedDefaultDecoderAndPressedFullPackage() throws Exception {
        downloadPackage("", "", "", "Full Package");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("amb", configData.get("decoderType"));
    }

    @Test
    public void configFileShouldContainsDecoderTypeAMBIfSelectedDefaultDecoderAndPressedConfigurationUpdate() throws Exception {
        downloadPackage("", "", "", "Configuration Update");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("amb", configData.get("decoderType"));
    }

    @Test
    public void configFileShouldContainsTokenNotEmptyInMainConfFileForFullPackage() throws Exception {
        downloadPackage("", "", "", "Full Package");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertTrue(!configData.get("token").equals(""));
    }

    @Test
    public void configFileShouldContainsTokenNotEmptyInMainConfFileForConfigurationUpdate() throws Exception {
        downloadPackage("", "", "", "Configuration Update");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertTrue(!configData.get("token").equals(""));
    }

    @Test
    public void configFileShouldContainsProtocolETHIfSelectedEthernetTCPIPAndPressedFullPackage() throws Exception {
        downloadPackage("Ethernet, TCP/IP", "", "", "Full Package");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("eth", configData.get("protocol"));
    }

    @Test
    public void configFileShouldContainsProtocolETHIfSelectedEthernetTCPIPAndPressedConfigurationUpdate() throws Exception {
        downloadPackage("Ethernet, TCP/IP", "", "", "Configuration Update");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("eth", configData.get("protocol"));
    }

    @Test
    public void configFileShouldContainsProtocolSerialIfSelectedUSBCOMSerialPortAndPressedFullPackage() throws Exception {
        downloadPackage("USB/COM, Serial Port", "", "", "Full Package");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("serial", configData.get("protocol"));
    }

    @Test
    public void configFileShouldContainsProtocolSerialIfSelectedUSBCOMSerialPortAndPressedConfigurationUpdate() throws Exception {
        downloadPackage("USB/COM, Serial Port", "", "", "Configuration Update");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("serial", configData.get("protocol"));
    }

    @Test
    public void configFileShouldContainsIP111222333444IfSelectedEthernetTCPIPEnterd111222333444ToFieldIPAndPressedFullPackage() throws Exception {
        downloadPackage("Ethernet, TCP/IP", "111.222.333.444", "", "Full Package");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("111.222.333.444", configData.get("ip"));
    }

    @Test
    public void configFileShouldContainsIP111222333444IfSelectedEthernetTCPIPEnterd111222333444ToFieldIPAndPressedConfigurationUpdate() throws Exception {
        downloadPackage("Ethernet, TCP/IP", "111.222.333.444", "", "Configuration Update");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("111.222.333.444", configData.get("ip"));
    }

    @Test
    public void configFileShouldContainsPortXXX5IfSelectedUSBCOMSerialPortEnterdXXX5ToFieldPortAndPressedFullPackage() throws Exception {
        downloadPackage("USB/COM, Serial Port", "", "XXX5", "Full Package");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("XXX5", configData.get("port"));
    }

    @Test
    public void configFileShouldContainsPortXXX5IfSelectedUSBCOMSerialPortEnterdXXX5ToFieldPortAndPressedConfigurationUpdate() throws Exception {
        downloadPackage("USB/COM, Serial Port", "", "XXX5", "Configuration Update");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFolderConfigFromArchive(archiveFolderPath + "//rcgtiming.zip");
        HashMap<String, String> configData = getConfigData(archiveFolderPath + "//config//main.conf");
        deleteDownloadedFolder(new File(archiveFolderPath));
        Assert.assertEquals("XXX5", configData.get("port"));
    }
// для проведения следующих двух тест-кейсов необходимо запустить эмулятор
    @Test
    public void fileShouldExecuted() throws Exception {
        Assert.assertEquals("Internet connection OK. Working online.", fileExecute(true));
    }

    @Test
    public void fileShouldNotExecutedIfWrongToken() throws Exception {
        Assert.assertEquals("Bad access credentials. Please contact RCGTiming support.", fileExecute(false));
    }*/

    public String fileExecute(boolean tokenCorrect) throws Exception {
        String url = controller.getUrl();
        String ip = controller.getTestIPForConnection();
        downloadPackage("", ip, "", "Full Package");
        String archiveFolderPath = downloadsFolder + "//" + getArchiveFolderName();
        getFilesFromArchive(archiveFolderPath + "//rcgtiming.zip");
        Integer count = countLinesInFile(archiveFolderPath + "//config//main.conf");
        String[] linesFromFile = getLinesFromFile(archiveFolderPath + "//config//main.conf");

        for(int i=0; i<count; i++) {
            if (linesFromFile[i].startsWith("url = ")) {
                StringBuffer urlWithPass = new StringBuffer(linesFromFile[i]);
                linesFromFile[i] = String.valueOf(urlWithPass.delete(6, 33).insert(6, url).delete(10, 11));
            }
            if (linesFromFile[i].startsWith("token") & !tokenCorrect) {
                linesFromFile[i] = linesFromFile[i]
                        .replace(linesFromFile[i].substring(9, 19), "1111111111");
            }
        }
        writeLinesToFile(linesFromFile, archiveFolderPath + "//config//main.conf");
        String data = stringWhichStartsWithPointBackSlashPHPFromFile(archiveFolderPath + "\\start.bat");
        String path = archiveFolderPath + "\\php\\";
        Process proc = Runtime.getRuntime().exec(path + data.substring(2, 37),
                new String[0],
                new File(path));
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        String actual = "";
        for(int i = 1; i < 11; i++) {
            actual = stdInput.readLine();
        }
        StringBuffer answer = new StringBuffer(actual);
        actual = String.valueOf(answer.delete(0, 21));
        if (tokenCorrect) {
            proc.destroy();
        }
        deleteDownloadedFolder(new File(archiveFolderPath));
        return actual;
    }

    public void downloadPackage(String interf, String ip, String port, String packageType) throws InterruptedException {
        mainPage.getDownloadButtonInMainPage().click();
        if (!interf.equals("")) {
            switch (interf) {
                case "Ethernet, TCP/IP":
                    System.out.println("Ethernet, TCP/IP");
                    mainPage.getSelectProtocol().selectOption(0);
                    break;
                case "USB/COM, Serial Port":
                    System.out.println("USB/COM, Serial Port");
                    mainPage.getSelectProtocol().selectOption(1);
                    break;
            }
        }
        if (!ip.equals("")) {
            mainPage.getInputIP().setValue(ip);
        }
        if (!port.equals("")) {
            mainPage.getInputPort().setValue(port);
        }
        switch (packageType) {
            case "Full Package":
                mainPage.getFullPackageButton().click();
                break;
            case "Configuration Update":
                mainPage.getConfigurationUpdateButton().click();
                break;
            case "Driver Update":
                mainPage.getDriverUpdateButton().click();
                break;
        }

        mainPage.getDownloadButtonInModalWindow().click();
        Thread.sleep(8000);
    }

    public HashMap archiveContains() throws IOException {
        HashMap<String, Boolean> zipContent = new HashMap<>();
            zipContent.put("debug.bat", false);
            zipContent.put("rcgt-driver.php", false);
            zipContent.put("start.bat", false);
            zipContent.put("configFolder", false);
            zipContent.put("phpFolder", false);

        File storage = new File(downloadsFolder);
        String pathToZip = "";
        if (controller.headless) {
            pathToZip = downloadsFolder;
        } else {
            String archiveFolderName = "";
            if (storage.isDirectory()) {
                // получаем все вложенные объекты в каталоге
                for (File item : storage.listFiles()) {
                    archiveFolderName = item.getName();
                }
            }

            pathToZip = downloadsFolder + "//" + archiveFolderName;
        }

        File archiveFolder = new File(pathToZip);

        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToZip + "//rcgtiming.zip")))
        {
            ZipEntry entry;
            String name;

            while((entry = zin.getNextEntry()) != null){
                name = entry.getName(); // получим название файла
                if (name.equals("debug.bat")) {
                    zipContent.put("debug.bat", true);
                }
                if (name.equals("rcgt-driver.php")) {
                    zipContent.put("rcgt-driver.php", true);
                }
                if (name.equals("start.bat")) {
                    zipContent.put("start.bat", true);
                }
                if (name.startsWith("config/")) {
                    zipContent.put("configFolder", true);
                }
                if (name.startsWith("php/")) {
                    zipContent.put("phpFolder", true);
                }
                zin.closeEntry();
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        deleteDownloadedFolder(archiveFolder);

        return zipContent;
    }

    public String getArchiveFolderName() {
        File storage = new File(downloadsFolder);
        String archiveFolderName = "";
        if(storage.isDirectory())
        {
            // получаем все вложенные объекты в каталоге
            for(File item : storage.listFiles()){
                archiveFolderName = item.getName();
            }
        }
        return archiveFolderName;
    }

    public HashMap<String, String> getConfigData(String path) throws Exception {

        FileReader fr = new FileReader(path);
        Scanner scan = new Scanner(fr);

        HashMap<String, String> configData = new HashMap<>();

        while (scan.hasNextLine()) {
            String nextLine = scan.nextLine();
            if (nextLine.startsWith("decoderType")) {
                configData.put("decoderType", nextLine.replace("decoderType = ", "").replace("\"", ""));
            } else
                if (nextLine.startsWith("protocol")) {
                    configData.put("protocol", nextLine.replace("protocol = ", "").replace("\"", ""));
                } else
                    if (nextLine.startsWith("port") & (configData.get("port") == null)) {
                        configData.put("port", nextLine.replace("port = ", "").replace("\"", ""));
                    } else
                        if (nextLine.startsWith("ip")) {
                            configData.put("ip", nextLine.replace("ip = ", "").replace("\"", ""));
                        } else
                            if (nextLine.startsWith("token")) {
                                configData.put("token", nextLine.replace("token = ", "").replace("\"", ""));
                            }
        }

        fr.close();

        return configData;
    }

    public void getFolderConfigFromArchive(String path) {
        try (var file = new ZipFile(path)) {
            var entries = file.entries();
            var uncompressedDirectory = new File(file.getName()).getParent() + File.separator;
            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();
                if (entry.getName().equals("config/")) {
                    processDirectory(uncompressedDirectory, entry);
                }
                if (entry.getName().equals("config/main.conf") | entry.getName().equals("start.bat")) {
                    processFile(file, uncompressedDirectory, entry);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getFilesFromArchive(String path) {
        try (var file = new ZipFile(path)) {
            var entries = file.entries();
            var uncompressedDirectory = new File(file.getName()).getParent() + File.separator;
            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();
                if (!entry.isDirectory()) {
                    processFile(file, uncompressedDirectory, entry);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processDirectory(String uncompressedDirectory, ZipEntry entry) {
        var newDirectory = uncompressedDirectory + entry.getName();
        var directory = new File(newDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private static void processFile(ZipFile file, String uncompressedDirectory, ZipEntry entry) throws IOException {
        try (
                var is = file.getInputStream(entry);
                var bis = new BufferedInputStream(is)
        ) {
            String uncompressedFileName = uncompressedDirectory + entry.getName();
            File x = new File(uncompressedFileName);

            File dir = new File(x.getParent());

            dir.mkdirs();

            try (
                    var os = new FileOutputStream(uncompressedFileName);
                    var bos = new BufferedOutputStream(os)
            ) {
                while (bis.available() > 0) {
                    bos.write(bis.read());
                }
            }
        }
    }

    public void deleteDownloadedFolder(File archiveFolder) throws IOException {
        FileUtils.deleteDirectory(archiveFolder);
    }

    private static String stringWhichStartsWithPointBackSlashPHPFromFile(String path) throws Exception {

        FileReader fr = new FileReader(path);
        Scanner scan = new Scanner(fr);

        String data = "";
        while (scan.hasNextLine()) {
            data = scan.nextLine();;
            if (data.startsWith(".\\php")) {
                fr.close();
                return data;
            }
        }

        fr.close();
        return "";
    }

    public static int countLinesInFile(String filename) throws IOException {

        int count = 0;
        FileReader fr = null;
        int symbol;

        try {
            fr = new FileReader(filename);
            do {
                symbol = fr.read();
                if ((char)symbol == '\n')
                    count++;
            } while (fr.ready());
        }
        catch (IOException e)
        {
            System.out.println("I/O error: " + e);
        }
        finally {
            try {
                if (fr!=null) {
                    fr.close();
                }
            }
            catch (IOException e) {
                System.out.println("File close error.");
            }
        }
        return count;
    }

    public static String[] getLinesFromFile(String filename) throws IOException {

        int count; // количество строк в файле
        String lines[] = null; // массив строк - результат
        FileReader fr = null;
        String s; // дополнительная переменная - строка
        int symbol;
        int i;

        count = countLinesInFile(filename);
        if (count<=0) return null;
        lines = new String[count];
        try {
            fr = new FileReader(filename);
            s = "";
            i = 0;
            do {
                symbol = fr.read();
                if (((char)symbol == '\n')) {
                    s = s.substring(0, s.length()-1);
                    lines[i] = s;
                    s = "";
                    i++;
                }
                else {
                    s = s + (char)symbol;
                }
            } while (fr.ready());
        }
        catch (IOException e)
        {
            System.out.println("I/O error: " + e);
        }
        finally {
            try {
                if (fr!=null) {
                    fr.close();
                }
            }
            catch (IOException e) {
                System.out.println("File close error.");
            }
        }
        return lines;
    }

    public static void writeLinesToFile(String lines[], String filename) throws IOException {

        FileOutputStream fs = null;
        PrintStream ps = null;

        try {
            fs = new FileOutputStream(filename);
            ps = new PrintStream(fs);

            for (int i=0; i<lines.length; i++)
                ps.println(lines[i]);
        }
        catch (IOException e) {
            System.out.println("I/O error: " + e);
        }
        finally {
            if (fs!=null) {
                try {
                    fs.close();
                }
                catch (IOException e2) {
                    System.out.println("Error closing " + filename);
                }
            }

            if (ps!=null) {
                ps.close();
            }
        }
    }
}
