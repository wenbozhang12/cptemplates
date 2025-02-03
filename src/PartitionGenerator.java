import java.util.ArrayList;
import java.util.List;

public class PartitionGenerator {

    List<List<List<Integer>>> partitions(List<Integer> arr) {
        List<List<List<Integer>>> res = new ArrayList<>();
        int n = arr.size();
        if (n == 0) {
            List<List<Integer>> empty = new ArrayList<>();
            res.add(empty);
            return res;
        }
        int limit = 1 << (n - 1);
        for (int j = 0; j < limit; ++j) {
            List<List<Integer>> parts = new ArrayList<>();
            List<Integer> part1 = new ArrayList<>();
            List<Integer> part2 = new ArrayList<>();
            parts.add(part1);
            parts.add(part2);
            int i = j;
            for (int item : arr) {
                parts.get(i&1).add(item);
                i >>= 1;
            }
            for (List<List<Integer>> b : partitions(part2)) {
                List<List<Integer>> holder = new ArrayList<>();
                holder.add(part1);
                holder.addAll(b);
                res.add(holder);
            }
        }
        return res;
    }
}
