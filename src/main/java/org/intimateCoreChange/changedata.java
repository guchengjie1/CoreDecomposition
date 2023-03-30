package org.intimateCoreChange;

import java.io.*;
import java.util.ArrayList;

//对数据集附加权重，目前全部为1
public class changedata {
    public static void main(String[] args) throws IOException {
        String dataset = "pubmed";
        String logfile = "D:/myproject/java/IntimateCoreSample/trans_datasets/"+dataset+".txt";
        String resultfile = "D:/myproject/java/IntimateCoreSample/trans_datasets/"+dataset+"Change.txt";
        BufferedReader br = new BufferedReader(new FileReader(new File(logfile)));
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(resultfile)));
        String line = null;

        //读取每一行数据，对最后权重位赋值1
        while ((line = br.readLine()) != null) {
            String[] data = line.split(" ");
            bw.write(data[0]+","+data[1]+","+1);
            bw.newLine();
        }

        br.close();
        bw.close();
        System.out.println("change over");


    }
}
