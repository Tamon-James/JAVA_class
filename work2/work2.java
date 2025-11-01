
public class work2 {
    public static void main(String[] args){
        Yuru boy = new Yuru();
        Yuru girl = new Yuru();

        boy.setName("Kouga_kun");
        boy.setEnergy(10);
        boy.intro();
        boy.getEnergy();
        boy.getName();
        
        boy.fly(10);
    }
}

class Yuru{
    private int energy;
    private String name;

    Yuru(String nm,int en){
        energy = en;
        name = nm;
    }

    Yuru(String nm){
        energy = 1;
        name = nm;
    }

    Yuru(){
        energy = 15;
        name = "Gonbe";
    }

    void setEnergy(int en){
        energy = en;
    }

    int getEnergy(){
        return energy;
    }

    void setName(String nm){
        name = nm;
    }

    String getName(){
        return name;
    }

    void intro(){
        System.out.println("Yuru"+name+"です。こんにちは");
        energy--;
        if(energy <= 0){
            System.out.println(name+"は疲れちゃったみたいだよ。");
        }
    }

    void fly(int count){
        for(int i = count; i>0; i--){
            if(energy>1){
                System.out.println("どっひゃー!");
                energy = energy -2;
            }else{
                if(energy<=1){
                    System.out.println("もう飛べないっすわ");
                    break;
                }
            }
        }

        if(energy==0){
            System.out.println("ふー、疲れた");
        }


    }

}