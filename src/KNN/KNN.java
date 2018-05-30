package KNN;

import java.util.Collections;
import java.util.List;


public class KNN {
      
    //欧式距离
    private static double disCal(KNNData i, KNNData td) {
        return Math.sqrt((i.AP1 - td.AP1)*(i.AP1 - td.AP1)+(i.AP2 - td.AP2)*(i.AP2 - td.AP2)+
                (i.AP3 - td.AP3)*(i.AP3 - td.AP3)+(i.AP4 - td.AP4)*(i.AP4 - td.AP4)+(i.AP5 - td.AP5)*(i.AP5 - td.AP5));
    }
    
    public static KNNData knnCal(int k, KNNData i, List<KNNData> ts) {
        //保存距离
        for (KNNData td : ts) {
            td.distance = disCal(i, td);         
        }
        Collections.sort(ts);                //对ts进行排序
        return ts.get(0);
    }
}