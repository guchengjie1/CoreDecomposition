package org;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import javax.swing.plaf.ColorUIResource;
import java.io.*;
import java.util.*;

public class CoreDecomTest {
    @Test
    public void testfind() throws IOException {
        String root = "facebook.txt";//存放图的数据文件的路径
        int k = 6;//这一轮要查找的k值

        DataReader dataReader = new DataReader(root);
        Map<Integer, Set<Integer>> initialmap = dataReader.readGraph();
        System.out.println("原始图顶点数: " + initialmap.size());

        FindKCore findKCore = new FindKCore(initialmap);
        findKCore.deleteNode(k);
        System.out.println("满足k条件的顶点数: " + findKCore.getPnbMap().size());
        HashMap<Integer, Integer> roundmap = findKCore.findClique();
        System.out.println("通过键值对的方式打印，键为顶点id，值为所属的团编号");
        System.out.println(roundmap);

    }

    @Test
    public void test2() throws IOException {
        HashMap<Integer,Set<Integer>> mymap = new HashMap<>();
        HashSet<Integer> myset = new HashSet<>();
        myset.add(2);
        myset.add(3);
        mymap.put(1,myset);
        Set<Integer> newset = mymap.get(1);
        newset.add(5);
        System.out.println(mymap);

    }

    @Test
    public void studenttest() {

        int k = 5;//度数少于k的结点 不断 删除，最终得到结点 大于等于k的子图
        Map<Integer, Set<Integer>> map = new HashMap<>();//创建network
        Map<Integer, Integer> degree = new HashMap<>();//创建每一个结点的度数map

        //读取文件，初始化map和degree
        try {
            BufferedReader br = new BufferedReader(new FileReader("facebook.txt"));
            String str = null;
            while ((str = br.readLine()) != null) {
                String[] s = str.split(" ");
                int cur = Integer.parseInt(s[0]);
                int neighbor = Integer.parseInt(s[1]);
                if (map.containsKey(cur)) {
                    map.get(cur).add(neighbor);
                    degree.put(cur, degree.get(cur) + 1);
                } else {
                    Set<Integer> set = new HashSet<>();
                    set.add(neighbor);
                    degree.put(cur, 1);
                    map.put(cur, set);
                }
                if (map.containsKey(neighbor)) {
                    map.get(neighbor).add(cur);
                    degree.put(neighbor, degree.get(neighbor) + 1);
                } else {
                    Set<Integer> set = new HashSet<>();
                    set.add(cur);
                    degree.put(neighbor, 1);
                    map.put(neighbor, set);
                }
            }
            br.close();
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //k-core删除结点
        Queue<Integer> queue = new LinkedList<>();
        for (Integer a : degree.keySet()) {
            if (degree.get(a) < k) {
                queue.add(a);

            }
        }
        while (!queue.isEmpty()) {
            int cur = queue.poll();//移除当前的小于k的结点
            //遍历其邻接结点
            for (Integer a : map.get(cur)) {
                degree.put(a, degree.get(a) - 1);//将其所有邻接结点度数-1
                Set<Integer>  set1= map.get(a);
                set1.remove(cur);
                if (degree.get(a) < k) {
                    queue.add(a);
                }
            }
            //处理完成这个结点的所有邻接结点，删除这个结点
            map.remove(cur);
            //map.put(cur,new HashSet<Integer>());
        }

        System.out.println(map.size());

    }

    static Integer getKey(Map<Integer, Set<Integer>> map) {
        Integer[] keys = map.keySet().toArray(new Integer[0]);
        Random rand = new Random();
        Integer randomKey = keys[rand.nextInt(keys.length)];
        return randomKey;
    }
}

