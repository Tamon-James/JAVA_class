import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    public static void main(String[] args) {

        byte[] buff = new byte[1024];
        ServerSocket servsock = null; // サーバソケット

        String[] replies = { // サーバの返信メッセージ
            "Hello!\n",
            "How are you?\n",
            "That's interesting.\n",
            "I see.\n",
            "Tell me more.\n",
            "Really?\n"
        };

        Random rand = new Random(); // 乱数生成器

        try {
            servsock = new ServerSocket(6000, 300); // ポート6000で待ち受け
            System.out.println("Server Start");

            //無限ループ
            while (true) {

                Socket sock = servsock.accept();
                System.out.println("Client connected");

                InputStream instream = sock.getInputStream();
                OutputStream outstream = sock.getOutputStream();

                while (true) { // クライアントとの通信ループ
                    int n = instream.read(buff);

                    // クライアント切断
                    if (n == -1) {
                        System.out.println("Client disconnected");
                        break;
                    }

                    String recv = new String(buff, 0, n, "UTF-8"); // 受信データを文字列に変換
                    if (recv.equals("bye")) { // 終了コマンド
                    System.out.println("Client > " + recv);
                        System.out.println("Fin");
                        break;
                    }

                    String reply = replies[rand.nextInt(replies.length)]; // ランダムに返信メッセージを選択
                    outstream.write(reply.getBytes("UTF-8")); // 返信を送信
                    outstream.flush();
                }
                instream.close();
                outstream.close();
                sock.close();
                
            }
        } catch (Exception e) {
            System.err.println("Error : Server error " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
