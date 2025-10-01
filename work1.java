public class work1 {
    public static void main(String[] args){
        String year[] = {"2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024"}; 
        int at_bat[] = {36,210,527,547,407,531,539,569,472,540,450,440,226,338,263,212}; 
        int hits[] = {10,49,125,131,124,143,142,142,102,143,109,105,40,91,67,46}; 
        int HomeRuns[] = {0,9,18,24,28,27,30,25,16,25,24,31,7,24,15,4}; 

        int ranking[] = new int[at_bat.length];
        for (int i=0; i<at_bat.length; i++){
            ranking[i] += i;
        }
        int at_bat_sort[] = at_bat.clone();
        for (int i=0; i<at_bat_sort.length-1; i++){
            for (int j=i+1; j<at_bat_sort.length; j++){
                if(at_bat_sort[i]<at_bat_sort[j]){
                    int temp = at_bat_sort[i];
                    at_bat_sort[i] = at_bat_sort[j];
                    at_bat_sort[j] = temp;

                    int temp_rank = ranking[i];
                    ranking[i] = ranking[j];
                    ranking[j] = temp_rank;
                }
            }
        }
        System.out.println("年別成績\n");
        for(int i=0; i<ranking.length; i++){
            System.out.println("年:"+year[ranking[i]]);
            System.out.println("打数:"+at_bat[ranking[i]]);
            System.out.println("安打数:"+hits[ranking[i]]);
            System.out.println("ホームラン数:"+HomeRuns[ranking[i]]);
            System.out.printf("打率:%.3f%n",(double)hits[ranking[i]]/at_bat[ranking[i]]);
            System.out.println("--------------");
        }
        
        //生涯成績
        int hits_total =0;
        int at_bat_total =0;
        int HomeRuns_total =0;

        for(int i=0; i<year.length; i++){
            
            hits_total += hits[i];
            at_bat_total += at_bat[i];
            HomeRuns_total += HomeRuns[i];
        }
        System.out.println("生涯成績\n");
        System.out.println("総打数:"+at_bat_total);
        System.out.println("総安打数:"+hits_total);
        System.out.println("総ホームラン数:"+HomeRuns_total);
        System.out.printf("生涯打率:%.3f%n",(double)hits_total/at_bat_total);
    }
}
