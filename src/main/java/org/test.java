package org;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.CheckedOutputStream;

/*
* 用于测试你们自己的结果和我跑的结果是否一致，
*
* 存储格式：
* 将对应k下得到的结果按照团存储，一个团存一个文件，所有团都存在一个文件夹中
* 团的文件命名规则是queryk_k_round_round.txt"， round是团的编号，从1开始！！  （当然你们也可以自己改）
*
* 我的结果也不一定对嗷，还是要自己多思考滴
* */


//用于测试得到的结果是否一致
public class test {

    public static void main(String[] args) throws IOException {
        int k = 6;//自己选则要测的k
        int reround = 1;

        //读取结果文件的路径
        String result = "result/queryk_" + k + "_round_" + reround + ".txt";
        //将顶点集存储在resultIdMap中
        HashMap<Integer, HashSet<Integer>> resultIdMap = new HashMap<>();
        while ((new File(result).exists())) {
            BufferedReader stdin = new BufferedReader(new FileReader(result));
            String line = null;
            line = stdin.readLine();
            String s[] = line.split(" ");
            HashSet<Integer> resultid = new HashSet<>();
            for (int i = 0; i < s.length; i++) {
                resultid.add(Integer.valueOf(s[i]));
            }
            resultIdMap.put(reround, resultid);
            reround++;
            result = "result/queryk_" + k + "_round_" + reround + ".txt";
        }

        //自己的答案读取方式同理
        HashMap<Integer, HashSet<Integer>> yourResultIdMap = new HashMap<>();
        int yourround = 1;
        String yourResult = "D:/myproject/java/CoreDecomposition/queryk_" + k + "_round_" + yourround + ".txt";//输入自己的文件目录
        while ((new File(yourResult).exists())) {
            BufferedReader stdin2 = new BufferedReader(new FileReader(yourResult));
            String line2 = null;
            line2 = stdin2.readLine();
            String s2[] = line2.split(" ");
            HashSet<Integer> yourResultid = new HashSet<>();
            for (int i = 0; i < s2.length; i++) {
                yourResultid.add(Integer.valueOf(s2[i]));
            }
            yourResultIdMap.put(yourround, yourResultid);
            yourround++;
            yourResult = "D:/myproject/java/CoreDecomposition/queryk_" + k + "_round_" + yourround + ".txt";//同上修改自己的文件目录
        }
        yourround--;
        reround--;

        if (yourround != reround) {
            System.out.println("the number of clique is different");
        } else {
            int flag = compare(resultIdMap, yourResultIdMap, reround);
            if (flag == reround) {
                System.out.println("!!!!success!!!! Time for PLAY!!!!");
            } else {
                System.out.println("the vertex number in round is different");
            }
        }

        System.out.println("这是我在测试github的更新");


    }

    public static int compare(HashMap<Integer, HashSet<Integer>> resultIdMap, HashMap<Integer, HashSet<Integer>> yourResultIdMap, int round) {
        //统计有多少个文件内容是一样的
        int flag = 0;
        for (Map.Entry<Integer, HashSet<Integer>> entry :
                resultIdMap.entrySet())
            for (int i = 1; i <= round; i++) {
                {
                    HashSet<Integer> value = entry.getValue();
                    HashSet<Integer> yourvalue = yourResultIdMap.get(i);
                    if (value.containsAll(yourvalue) && yourvalue.containsAll(value)) {
                        flag++;
                    }
                }
            }
        return flag;
    }
}
