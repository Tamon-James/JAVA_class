public class work4 {
    public static void main(String[] args){

        Queue AtoProxy = new Queue(10);
        Queue BtoProxy = new Queue(10);
        Queue ProxyToBoth = new Queue(10);

        Proxy proxy = new Proxy(AtoProxy, BtoProxy, ProxyToBoth);

        Asan A = new Asan(AtoProxy, ProxyToBoth);
        Bsan B = new Bsan(BtoProxy, ProxyToBoth);

        Thread thA = new Thread(A);
        Thread thB = new Thread(B);
        Thread thProxy = new Thread(proxy);

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

    public synchronized void wrtMessage(String str) throws InterruptedException {
        while (messagein >= buf.length) {
            wait();
        }
        int end = (start + messagein) % buf.length;
        buf[end] = str;
        messagein++;
        notifyAll();
    }

    public synchronized String readMessage() throws InterruptedException {
        while(messagein == 0) {
            wait();
        }
        String mes = buf[start];
        start = (start + 1) % buf.length;
        messagein--;
        notifyAll();
        return mes;
    }
}

class Proxy implements Runnable {

    Queue AtoProxy;
    Queue BtoProxy;
    Queue PtoBoth;

    Proxy(Queue a, Queue b, Queue p){
        AtoProxy = a;
        BtoProxy = b;
        PtoBoth = p;
    }

    public void run() {
        try {
            String invite = AtoProxy.readMessage();
            PtoBoth.wrtMessage("TO_B: INVITE");
            PtoBoth.wrtMessage("TO_A: 100 Trying");
            String ringing = BtoProxy.readMessage();
            PtoBoth.wrtMessage("TO_A: 180 Ringing");
            String ok = BtoProxy.readMessage();
            PtoBoth.wrtMessage("TO_A: 200 OK");

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}

class Asan implements Runnable {
    Queue sendQ;
    Queue recvQ;

    Asan(Queue s, Queue r){
        sendQ = s;
        recvQ = r;
    }

    public void run() {
        try {
            sendQ.wrtMessage("INVITE");

            while(true){
                String msg = recvQ.readMessage();

                if(msg.startsWith("TO_A")){
                    System.out.println("[A 受信] " + msg.substring(5));
                    if(msg.contains("200 OK")) break;
                }
            }

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}

class Bsan implements Runnable {
    Queue sendQ;
    Queue recvQ;

    Bsan(Queue s, Queue r){
        sendQ = s;
        recvQ = r;
    }

    public void run() {
        try {
            while(true){
                String msg = recvQ.readMessage();

                if(msg.startsWith("TO_B")){
                    String body = msg.substring(5);
                    System.out.println("[B 受信] " + body);

                    sendQ.wrtMessage("180 Ringing");

                    Thread.sleep(800);

                    sendQ.wrtMessage("200 OK");

                    break;
                }
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
