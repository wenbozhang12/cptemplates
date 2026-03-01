import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class FreqTracker{
    HashMap<Integer, Integer> map = new HashMap<>();
    TreeMap<Integer, HashSet<Integer>> fq = new TreeMap<>();
    void add(int i){
        if(map.containsKey(i)){
            int curf = map.get(i);
            var hs = fq.get(curf);
            hs.remove(i);
            if(hs.isEmpty()){
                fq.remove(curf);
            }
        }
        int curf = map.merge(i, 1, Integer::sum);
        fq.computeIfAbsent(curf, hs -> new HashSet<>()).add(i);
    }

    void rem(int i){
        int curf = map.get(i);
        var hs = fq.get(curf);
        hs.remove(i);
        if(hs.isEmpty()){
            fq.remove(curf);
        }
        curf--;
        if(curf == 0){
            map.remove(i);
        }
        else{
            map.merge(i, -1, Integer::sum);
            fq.computeIfAbsent(curf, h -> new HashSet<>()).add(i);
        }
    }

    int minF(){
        return fq.firstKey();
    }

    int cnt(){
        return map.size();
    }
}