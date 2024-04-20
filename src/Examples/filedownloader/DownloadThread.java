package Examples.filedownloader;

public class DownloadThread extends Thread {
    private FileDownloader fileDownloader;

    public DownloadThread(FileDownloader fileDownloader){
        this.fileDownloader = fileDownloader;
    }

    @Override
    public void run(){
        fileDownloader.download();
    }
}
