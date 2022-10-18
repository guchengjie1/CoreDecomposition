package org;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//将得到的结果存入result文件夹中
public class Main {
    public static void main(String[] args) throws IOException {

        String dataroot = "facebook.txt";//存放图的数据文件的路径
        String resultroot = "result/";
        int k=2;//这一轮要查找的k值
        DataReader dataReader = new DataReader(dataroot);
        Map<Integer, Set<Integer>> initialmap = dataReader.readGraph();
        System.out.println("原始图顶点数: " + initialmap.size());
        for (k = 2; k <= 115; k++) {
            FindKCore findKCore = new FindKCore(initialmap);
            findKCore.deleteNode(k);
            System.out.println("满足k条件的顶点数: " + findKCore.getPnbMap().size());
            HashMap<Integer, Integer> roundmap = findKCore.findClique();
            System.out.println("通过键值对的方式打印，键为顶点id，值为所属的团编号");
            System.out.println(roundmap);

            int frontround = 1;
            FileWriter fw = new FileWriter(new File(resultroot +"queryk_"+ k+"_round_"+frontround + ".txt"));

            for (Map.Entry<Integer, Integer> entry : roundmap.entrySet()) {
                int id = entry.getKey();
                int roundnum = entry.getValue();
                if (roundnum == frontround) {
                    fw.write(id + " ");
                } else {
                    frontround = roundnum;
                    fw.close();
                    fw = new FileWriter(new File(resultroot +"queryk_"+ k+"_round_"+frontround + ".txt"));
                    fw.write(id + " ");
                }

            }
            fw.close();
        }

    }
}
