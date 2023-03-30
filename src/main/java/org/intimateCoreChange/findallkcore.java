package org.intimateCoreChange;

import javafx.scene.input.ScrollEvent;
import org.DataReader;
import org.FindKCore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//对数据集进行core分解，得到一个数据集下所有的k，以及它对应的部分顶点
public class findallkcore {
    public static void main(String[] args) throws IOException {
        String dataname = "ppi";
        String root = "/home/lab401/gcj/IntimateCoreSample/coredecom/trans_datasets/";
        String logloc = root+dataname+".txt";

        DataReader dataReader = new DataReader(logloc);
        Map<Integer, Set<Integer>> initialMap = dataReader.readGraph();
        FileWriter fw = new FileWriter(new File(root+dataname+"Core.txt"));
        int k = 1;

        while (true){
            System.out.println("k = " + k);
            fw.write("kcore: "+k);
            FindKCore findKCore = new FindKCore(initialMap);
            findKCore.deleteNode(k);
            if (findKCore.getPnbMap().size() == 0) {
                fw.write("null\r\n");
                break;
            }
            int size = findKCore.getPnbMap().size();
            fw.write("  number:"+size+"\r\n");
            if (size<800 || (k>=147 && k<=153)){
                Set<Integer> vertex = findKCore.getPnbMap().keySet();
                Iterator<Integer> it = vertex.iterator();
                while (it.hasNext()){
                    fw.write(it.next()+",");
                }
            }
            fw.write("\r\n");

            k++;
        }

        fw.close();
        System.out.println("core decomposition is over");
    }
}
