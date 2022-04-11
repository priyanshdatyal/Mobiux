package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main{

    private static Data data;

    private static HashMap<String,Integer> hm = new HashMap<String,Integer>();
    private static HashMap<String,Integer> mostRevenues = new HashMap<String,Integer>();
    private static Map<String,Integer> mostPopular = new HashMap<String,Integer>();
    private static Map<String,Integer> mostPopularMonthWise = new HashMap<String,Integer>();
    private static List mostMonnthwise = new ArrayList();
    public static Map.Entry<String, Integer> mostSold = null;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        data = new Data();
        readData(data);
        System.out.println("\n1. Total sales : "+printTotal(data));
        System.out.println("\n2. Total sales monthwise :");
        getMonthWiseTotal(data);
        System.out.println("\n3. Most Sold Item monthwise :");
        mostSoldinMonth(data);
        System.out.println("\n4. Most Revenue Item :");
        mostRevenueItems(data);
        System.out.println("\n5. Min, Max & Average of popular item :");
        minMaxAvg(data);
    }


    private static void readData(Data data) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./data/dataset.txt"));
        try {
            String line = br.readLine();
//            line = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (line != null) {
                line = br.readLine();
                data.addDate(line.split(",")[0]);
                data.addSUK(line.split(",")[1]);
                mostPopularMonthWise.put(line.split(",")[1],0);
                mostRevenues.put(line.split(",")[1],0);
                mostPopular.put(line.split(",")[1],0);
                data.addUnitPrice(line.split(",")[2]);
                data.addQuantity(line.split(",")[3]);
                data.addTotal(line.split(",")[4]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {}
        finally {
            br.close();
        }
    }

    private static double printTotal(Data data){

        int sum = 0;
        for(Object d : data.total) {
            sum += Integer.parseInt(String.valueOf(d));
        }
        return sum;
    }

    private static void getMonthWiseTotal(Data data){
        List<Integer> sumMonths = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
        int count =0;
        for(Object d : data.date) {
            try{
                Date date=new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(d));
                sumMonths.set(Integer.parseInt(String.valueOf(date.getMonth())),(Integer.parseInt(String.valueOf(sumMonths.get(date.getMonth())))+Integer.parseInt(String.valueOf(data.total.get(count)))));
            }
            catch(Exception e){
                System.out.print(e);
            }
            count++;
        }
        System.out.println(sumMonths);
    }


    private static void mostRevenueItems(Data data) {
        int count =0;
        for(Object d : data.suk) {
            mostRevenues.put(String.valueOf(d),(Integer.parseInt(String.valueOf(data.total.get(count)))+Integer.parseInt(String.valueOf(mostRevenues.get(String.valueOf(d))))));
            count++;
        }
        System.out.println(mostRevenues);
    }

    public static void mostSoldinMonth(Data data){
        int count =-1;
        for(int i=0;i<12;i++){
            Map<String,Integer> mpmw = mostPopular;
            count=-1;
            try {
                for(Object d : data.suk) {
                    count++;
                    Date datee= new SimpleDateFormat("yyyy-MM-dd").parse((String) data.getDate(count));
                    if (i==Integer.parseInt(String.valueOf(datee.getMonth()))) {
                        mpmw.put(String.valueOf(d), Integer.parseInt(String.valueOf(mpmw.get(data.getSUK(count)))) + Integer.parseInt(String.valueOf(data.getQuantity(count))));
                    }
                }
                setMostSold(mpmw);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            resetmap((HashMap<String, Integer>) mpmw);
        }

        System.out.println(mostMonnthwise);
    }

    private static void setMostSold(Map<String, Integer> mpmw) {
        String maxName="No Sale";
        int maxValue=0;
        for (Map.Entry<String, Integer> entry : mpmw.entrySet()) {
            if (entry.getValue()>maxValue) {
                maxValue= entry.getValue();
                maxName= entry.getKey();
            }
        }
        mostMonnthwise.add(maxName);
    }
    public static void resetmap(HashMap<String, Integer> map){
        map.entrySet().forEach(entry->{
            map.put(entry.getKey(), 0);
        });
    }

    private static void minMaxAvg(Data data) {
        int count=0;
        for(int i=0;i<12;i++){
            System.out.println("For month "+(i+1)+" : ");
            int min=25000-0,max=0;
            float sum= 0.0F,sumCount= 1.0F;
            count=-1;
            for(Object d : data.suk) {
                try {
                    count++;
                    Date datee = new SimpleDateFormat("yyyy-MM-dd").parse((String) data.getDate(count));
                    if( ((String.valueOf(d)).equals(String.valueOf(mostMonnthwise.get(i)))) && ((Integer.parseInt(String.valueOf(datee.getMonth())))==i) ){
                        sum+=Integer.parseInt(String.valueOf(data.getQuantity(count)));
                        if(min>Integer.parseInt(String.valueOf(data.getQuantity(count)))){
                            min=Integer.parseInt(String.valueOf(data.getQuantity(count)));
                        }
                        if(max<Integer.parseInt(String.valueOf(data.getQuantity(count)))){
                            max=Integer.parseInt(String.valueOf(data.getQuantity(count)));
                        }
                        sumCount++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(sumCount!=1){sumCount--;}
            if(min==25000){min=0;}
            System.out.println("Minimum is : "+min+", Maximum is : "+max+", Average is : "+(Float.parseFloat(String.valueOf(sum/sumCount)))+"\n");
        }
    }
}
