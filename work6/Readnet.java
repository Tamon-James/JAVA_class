import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Readnet {
    public static void main (String[] args) {
        byte[] buff = new byte[1024];
        Socket sock = null;
        Scanner sc = new Scanner(System.in);
        boolean cont = true;
        InputStream instream = null;
        OutputStream outstream = null;
        
        try {
            System.out.println("Begin");
            sock = new Socket("127.0.0.1", 6000); //サーバへ接続

            instream = sock.getInputStream();
            outstream = sock.getOutputStream();
        
            while(true) { //無限ループ
                System.out.print("You > ");
                //キーボード入力
                String line = sc.nextLine().trim();
                if(line.length() == 0) {
                    continue;
                }   
                //サーバへ送信
                byte[] sendbuf = line.getBytes("UTF-8");
                outstream.write(sendbuf);
                outstream.flush();

                //サーバからの受信
                try {
                    int n = instream.read(buff);
                    if(n == -1) {
                        System.err.println("Error:Server disconnected");
                        break;
                    }
                    
                    System.out.print("Server > "); //受信データ表示
                    System.out.write(buff,0,n);
                }
                catch(Exception e) { //受信エラー
                    System.err.println("Error:Receive error" + e.getMessage());
                    System.exit(1);
                }

                //bye : 終了
                if(line.equals("bye")) {
                    System.out.println("Fin");
                    break;
                }
            } 
            instream.close();
            outstream.close();
            sock.close();  
        }
        catch(Exception e) { //接続エラー
            System.err.println("Error:Client error" + e.getMessage());
            System.exit(1);
        }
    }  
}