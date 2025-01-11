import java.util.ArrayList;
import java.util.PriorityQueue;

public class LiS {

    int lis(PriorityQueue<int[]> pq){
        var res = new ArrayList<Integer>();
        while(!pq.isEmpty()){
            int cur = pq.poll()[1];
            int l = 0, r = res.size();
            while(l < r){
                int m = l + (r - l)/2;
                if(cur <= res.get(m))
                    r = m;
                else
                    l = m + 1;
            }
            if(l == res.size())
                res.add(cur);
            else
                res.set(l, cur);
        }
        return res.size();
    }
}
