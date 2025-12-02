public class work3 {
    public static void main(String[] args){
        System.out.println("Start");
        Book book = new Book();

        BookUser user1 = new BookUser(book,"Taro");
        BookUser user2 = new BookUser(book,"Jiro");
        BookUser user3 = new BookUser(book,"Saburo");

        Thread th1 = new Thread(user1);
        Thread th2 = new Thread(user2);
        Thread th3 = new Thread(user3);

        th1.start();
        th2.start();
        th3.start();

        try{
            th1.join();
            th2.join();
            th3.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Thank you!!");
    }
}

class Book{
    private boolean isAvailable = true;
    private String borrowerName = "EMPTY";
    Util util = new Util();

    public synchronized boolean addBook(String user){
        util.rand_pause(1000);
        if(isAvailable){
            isAvailable = false;
            borrowerName = user;
            System.out.println(user + "さん、貸します。");
            return true;
        }else{
            System.out.println(user + "さん、貸し出し中です。");
            return false;
        }
    }
    public synchronized void retBook(String user){
        util.rand_pause(1000);
        if(!isAvailable && borrowerName.equals(user)){
            isAvailable = true;
            borrowerName = "EMPTY";
            System.out.println(user + "さん、返却しました。");
        }else if(isAvailable){
            System.out.println(user + "さん。返却する本がありません。");
        }
    }
}



class BookUser implements Runnable{
    private Book book;
    private String name;
    private Util util = new Util();

    public BookUser(Book book, String name){
        this.book = book;
        this.name = name;
    }

    @Override
    public void run(){
        for(int i=0; i<5; i++){
            if(book.addBook(name)){
                util.rand_pause(2000);
                book.retBook(name);
            }
            util.rand_pause(1000);
        }
    }
}

class Util {
    void rand_pause(int t){
        try{
            int n = (int)(Math.random()*t);
            Thread.sleep(n);
        }catch (InterruptedException e){
            System.err.println(e.getMessage());
        }
    }
}
