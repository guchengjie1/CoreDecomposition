package org;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//读入数据文件
public class DataReader {
    private String graphFile = null;
    public DataReader(String graphFile) {
        this.graphFile = graphFile;
    }

    public Map<Integer, Set<Integer>> readGraph() {
        Map<Integer, Set<Integer>> pmap = new HashMap<>();
        try {
            BufferedReader stdin = new BufferedReader(new FileReader(graphFile));

            String line = null;
            while ((line = stdin.readLine()) != null) {
                String s[] = line.split(" ");
                int vertexid = Integer.parseInt(s[0]);
                int neighbor = Integer.parseInt(s[1]);

                if (pmap.containsKey(vertexid)) {
                    Set<Integer> nbset = pmap.get(vertexid);
                    nbset.add(neighbor);
                    //pmap.put(vertexid, nbset);
                } else {
                    HashSet<Integer> nbset = new HashSet<>();
                    nbset.add(neighbor);
                    pmap.put(vertexid, nbset);
                }

                if (pmap.containsKey(neighbor)){
                    Set<Integer> nbset = pmap.get(neighbor);
                    nbset.add(vertexid);
                    //pmap.put(neighbor, nbset);
                } else {
                    HashSet<Integer> nbset = new HashSet<>();
                    nbset.add(vertexid);
                    pmap.put(neighbor, nbset);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pmap;
    }


}
