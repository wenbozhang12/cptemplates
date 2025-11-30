import java.util.TreeMap;

public class MedianSumTracker {
    long val = 0;
    int sz = 0;
    TreeMap<Integer, Integer> high = new TreeMap<>();
    TreeMap<Integer, Integer> low = new TreeMap<>();

    void add(int i) {
        if (sz == 0) {
            high.put(i, 1);
            sz++;
            return;
        }
        if (sz == 1) {
            var fk = high.firstKey();
            if (i > fk) {
                low.put(fk, 1);
                high.remove(fk);
                high.put(i, 1);
                val = i - low.lastKey();
            } else {
                low.put(i, 1);
                val = fk - i;
            }
            sz++;
            return;
        }

        if (sz % 2 == 0) {
            var fk = high.firstKey();
            if (i < fk) {
                low.merge(i, 1, Integer::sum);
                var lk = low.lastKey();
                low.merge(lk, -1, Integer::sum);
                low.remove(lk, 0);
                high.merge(lk, 1, Integer::sum);
            } else {
                high.merge(i, 1, Integer::sum);
            }
            val += Math.abs(i - high.firstKey());
        } else {
            val += Math.abs(i - high.firstKey());
            var fk = high.firstKey();
            if (i < fk) {
                low.merge(i, 1, Integer::sum);
            } else {
                high.merge(i, 1, Integer::sum);
                fk = high.firstKey();
                high.merge(fk, -1, Integer::sum);
                high.remove(fk, 0);
                low.merge(fk, 1, Integer::sum);
            }
        }
        sz++;
    }

    void rem(int i) {
        if (sz == 1) {
            high.remove(i);
            sz = 0;
            val = 0;
            return;
        }
        if (sz == 2) {
            if (high.containsKey(i)) {
                high.remove(i);
                var lk = low.lastKey();
                high.merge(lk, 1, Integer::sum);
                low.remove(lk);
            } else {
                low.remove(i);
            }
            sz--;
            val = 0;
            return;
        }
        if (sz % 2 == 0) {
            if (high.containsKey(i)) {
                high.merge(i, -1, Integer::sum);
                high.remove(i, 0);
                var lk = low.lastKey();
                high.merge(lk, 1, Integer::sum);
                low.merge(lk, -1, Integer::sum);
                low.remove(lk, 0);
            } else {
                low.merge(i, -1, Integer::sum);
                low.remove(i, 0);
            }
            val -= Math.abs(i - high.firstKey());
        } else {
            val -= Math.abs(i - high.firstKey());
            if (high.containsKey(i)) {
                high.merge(i, -1, Integer::sum);
                high.remove(i, 0);
            } else {
                low.merge(i, -1, Integer::sum);
                low.remove(i, 0);
                var fk = high.firstKey();
                low.merge(fk, 1, Integer::sum);
                high.merge(fk, -1, Integer::sum);
                high.remove(fk, 0);
            }
        }
        sz--;
    }
}
