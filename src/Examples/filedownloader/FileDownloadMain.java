package Examples.filedownloader;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class FileDownloadMain {

    private static final String initialUrlNamesFile = loadInitialFiles();

    private static String loadInitialFiles(){
        Properties properties = new Properties();

        try{
            InputStream inputStream = FileDownloader.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }

        return properties.getProperty("initial.file");

    }

    public static ArrayList<String> readUrlsFromFile(String fileName){
        ArrayList<String> urlList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) !=null){
                urlList.add(line.trim());
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return urlList;
    }
    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
        ArrayList<String> urlList = readUrlsFromFile(initialUrlNamesFile);

        System.out.println("Enter URL's (One per line, Type done to finish)");
//        while (true){
//            String url = input.nextLine();
//            if(url.equalsIgnoreCase("done")){
//                break;
//            }
//
//            urlList.add(url);
//        }


        ArrayList<DownloadThread> downloadThreads = new ArrayList<>();

        for(String url : urlList){
            FileDownloader fileDownloader = new FileDownloader(url);
            DownloadThread downloadThread = new DownloadThread(fileDownloader);
            downloadThreads.add(downloadThread);
            downloadThread.start();
        }

        for (DownloadThread downloadThread: downloadThreads){
            try{
                downloadThread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println("All downloads completed!");
    }
}
