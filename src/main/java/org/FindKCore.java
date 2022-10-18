package org;

import java.util.*;

//进行core分解的核心部分
public class FindKCore {
    private Map<Integer, Set<Integer>> pnbMap;
    private HashMap<Integer, Integer> roundMap = new HashMap<>();//每个顶点归属的团信息记录在roundmap中

    public Map<Integer, Set<Integer>> getPnbMap() {
        return pnbMap;
    }

    public FindKCore(Map<Integer, Set<Integer>> pnbMap) {
        this.pnbMap = pnbMap;
    }

    //在原图中删除不满足kcore条件的顶点
    public void deleteNode(int queryK) {
        Queue<Integer> queue = new LinkedList<Integer>();//simulate a queue

        //在第一轮中先找可以直接删除的顶点id
        Set<Integer> deleteSet = new HashSet<Integer>();
        for (Map.Entry<Integer, Set<Integer>> entry : pnbMap.entrySet()) {
            int curId = entry.getKey();
            Set<Integer> pnbSet = entry.getValue();
            if (pnbSet.size() < queryK) {
                queue.add(curId);
                deleteSet.add(curId);
            }
        }

        //受到第一轮的影响，逐个检查被删除顶点的邻居的度数
        while (queue.size() > 0) {
            int curId = queue.poll();//delete curId
            Set<Integer> pnbSet = pnbMap.get(curId);//找到curID对应的邻居
            for (int pnb : pnbSet) {//update curId's pnb
                if (!deleteSet.contains(pnb)) {
                    Set<Integer> tmpSet = pnbMap.get(pnb);
                    if (tmpSet != null) {
                        tmpSet.remove(curId);
                        if (tmpSet.size() < queryK) {
                            queue.add(pnb);
                            deleteSet.add(pnb);
                        }
                    }
                }
            }
            pnbMap.remove(curId);
        }

    }

    //将图中的顶点，根据连通性，分成不同的分量，并将分组信息记录
    public HashMap<Integer, Integer> findClique() {

        //初始化roundmap，未被访问的状态为0
        Set<Integer> vertexset = pnbMap.keySet();
        Iterator<Integer> it = vertexset.iterator();
        while (it.hasNext()) {
            int vertexid = it.next();
            roundMap.put(vertexid, 0);
            Set<Integer> neighbor = pnbMap.get(vertexid);
            Iterator<Integer> it2 = neighbor.iterator();
            while (it2.hasNext()) {
                roundMap.put(it2.next(), 0);
            }
        }

        int round = 1;

        while (roundMap.containsValue(0)) {
            BFS(round);
            round++;
        }

        return roundMap;

    }

    public void BFS(int round) {//round最小从1开始
        Queue<Integer> queue = new LinkedList<Integer>();

        //获取第一个未被访问的顶点id
        int firstvertexid = -1;
        Set<Integer> idlist = pnbMap.keySet();
        Iterator<Integer> it = idlist.iterator();
        while (it.hasNext()) {
            int curid = it.next();
            if (roundMap.get(curid) == 0) {
                firstvertexid = curid;
                break;
            }
        }

        roundMap.put(firstvertexid, round);//直接覆盖原有的记录
        queue.add(firstvertexid);

        //bfs本体，不断访问顶点
        while (queue.size() > 0) {
            int top = queue.poll();
            Set<Integer> neighbor = pnbMap.get(top);
            if (neighbor !=null){
                Iterator<Integer> it2 = neighbor.iterator();

                while (it2.hasNext()) {
                    int vertex = it2.next();
                    if (roundMap.get(vertex) == 0) {//当前vertex未被访问到
                        roundMap.put(vertex, round);
                        queue.add(vertex);
                    }
                }
            }
        }

    }


}
