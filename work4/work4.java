public class work4 {
    public static void main(String[] args){

        // A,B → Proxy（共通の受信キュー）
        Queue ToProxy = new Queue(10);

        // Proxy → A
        Queue ProxyToA = new Queue(10);

        // Proxy → B
        Queue ProxyToB = new Queue(10);

        // Proxyオブジェクト
        Proxy proxy = new Proxy(ToProxy, ProxyToA, ProxyToB);

        // Aさんオブジェクト
        Asan A = new Asan(ToProxy, ProxyToA);

        // Bさんオブジェクト
        Bsan B = new Bsan(ToProxy, ProxyToB);

        Thread thA = new Thread(A);
        Thread thB = new Thread(B);
        Thread thProxy = new Thread(proxy);

        // スレッド開始
        thA.start();
        thB.start();
        thProxy.start();
    }
}

class Queue {
    private String[] buf;
    int messagein = 0;
    int start = 0;

    public Queue(int size){
        buf = new String[size];
    }

    // メッセージの書き込み
    public synchronized void wrtMessage(String str) throws InterruptedException {
        while (messagein >= buf.length) { // キューが満杯なら待機
            wait();
        }
        int end = (start + messagein) % buf.length;
        buf[end] = str;
        messagein++;
        notifyAll();
    }

    // メッセージの読み込み
    public synchronized String readMessage() throws InterruptedException {
        while(messagein == 0) { // キューが空なら待機
            wait();
        }
        String mes = buf[start];
        start = (start + 1) % buf.length;
        messagein--;
        notifyAll();
        return mes;
    }
}

// Proxyクラス
class Proxy implements Runnable {

    Queue recvQ;   // A,B → Proxy
    Queue toA;     // Proxy → A
    Queue toB;     // Proxy → B

    Proxy(Queue r, Queue a, Queue b){
        recvQ = r;
        toA = a;
        toB = b;
    }

    public void run() {
        try {
            // A → Proxy : INVITE
            String invite = recvQ.readMessage();

            // Proxy → B : INVITE
            toB.wrtMessage("INVITE");

            // Proxy → A : 100 Trying
            toA.wrtMessage("100 Trying");

            // B → Proxy : 180 Ringing
            String ringing = recvQ.readMessage();

            // Proxy → A : 180 Ringing
            toA.wrtMessage("180 Ringing");

            // B → Proxy : 200 OK
            String ok = recvQ.readMessage();

            // Proxy → A : 200 OK
            toA.wrtMessage("200 OK");

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}

// Aさんクラス
class Asan implements Runnable {
    Queue sendQ; // A → Proxy
    Queue recvQ; // Proxy → A

    Asan(Queue s, Queue r){
        sendQ = s;
        recvQ = r;
    }

    public void run() {
        try {
            // A → Proxy : INVITE
            sendQ.wrtMessage("INVITE");

            while(true){
                // Proxy → A : メッセージ受信
                String msg = recvQ.readMessage();
                System.out.println("[A 受信] " + msg);

                if(msg.equals("200 OK")) break;
            }

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}

// Bさんクラス
class Bsan implements Runnable {
    Queue sendQ; // B → Proxy
    Queue recvQ; // Proxy → B

    Bsan(Queue s, Queue r){
        sendQ = s;
        recvQ = r;
    }

    public void run() {
        try {
            // Proxy → B : INVITE
            String msg = recvQ.readMessage();
            System.out.println("[B 受信] " + msg);

            // B → Proxy : 180 Ringing
            sendQ.wrtMessage("180 Ringing");

            Thread.sleep(800); // 保留時間

            // B → Proxy : 200 OK
            sendQ.wrtMessage("200 OK");

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
