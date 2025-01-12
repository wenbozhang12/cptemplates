import java.util.ArrayDeque;
import java.util.Arrays;

public class AhoCorasick {

    class TrieNode {
        TrieNode[] next = new TrieNode[26];
        boolean word = false;
        TrieNode prev;
        TrieNode out;
        int length = 0;
    }

    TrieNode root = new TrieNode();

    void addWord(String word) {
        var cur = root;
        int curlen = 0;
        for(char c : word.toCharArray()) {
            if(cur.next[c - 'a'] == null)
                cur.next[c - 'a'] = new TrieNode();
            cur = cur.next[c - 'a'];
            cur.length = ++curlen;
        }
        cur.word = true;
    }

    TrieNode p = root;

    public AhoCorasick(String[] words) {
        for(var w : words)
            addWord(w);
        var dq = new ArrayDeque<TrieNode>();
        dq.add(root);
        while(!dq.isEmpty()) {
            var cur = dq.poll();
            for(int i = 0; i < 26; i++) {
                if(cur.next[i] == null)
                    continue;
                var prev = cur.prev;
                while(prev != null && prev.next[i] == null)
                    prev = prev.prev;
                if(prev != null && prev.next[i] != null)
                    cur.next[i].prev = prev.next[i];
                else
                    cur.next[i].prev = root;
                if(cur.next[i].prev.word)
                    cur.next[i].out = cur.next[i].prev;
                else
                    cur.next[i].out = cur.next[i].prev.out;
                dq.add(cur.next[i]);
            }
        }
    }

    TrieNode inc(char c){
        while(p != root && p.next[c - 'a'] == null)
            p = p.prev;
        if(p.next[c - 'a'] != null)
            p = p.next[c - 'a'];
        return p;
    }

}
