package Examples.filedownloader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class FileDownloader {
    private String url;
    private static final String downloadFolderPath = loadDownloadFolder();

    public FileDownloader(String url){
        this.url = url;
    }

    public void download(){

        try{
            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();
            BufferedInputStream input = new BufferedInputStream(urlConnection.getInputStream());
            FileOutputStream fileOutput = new FileOutputStream(downloadFolderPath + getFileName(url1));

            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = input.read(buffer)) != -1){
                fileOutput.write(buffer, 0, byteRead);
            }

            fileOutput.close();
            input.close();
            System.out.println("Download is done: " + getFileName(url1));
//            System.out.println("System Path: "+System.getProperty("user.dir"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public String getFileName(URL url){
        String[] parts = url.getPath().split("/");
        return parts[parts.length-1];
    }

    private static String loadDownloadFolder(){
        Properties properties = new Properties();

        try{
            InputStream inputStream = FileDownloader.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }

        return properties.getProperty("download.folder");

    }


}
