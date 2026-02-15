class XorTrie{
    class Node{
        Node no;
        Node nz;
        int cnt = 0;
    }

    Node root = new Node();

    void add(int k){
        var cur = root;
        for(int i = 15; i >= 0; i--){
            if(((k >> i) & 1) == 0){
                if(cur.nz == null)
                    cur.nz = new Node();
                cur.nz.cnt++;
                cur = cur.nz;
            }
            else{
                if(cur.no == null)
                    cur.no = new Node();
                cur.no.cnt++;
                cur = cur.no;
            }
        }
    }

    void rem(int k){
        var cur = root;
        for(int i = 15; i >= 0; i--){
            if(((k >> i) & 1) == 0){
                cur.nz.cnt--;
                cur = cur.nz;
            }
            else{
                cur.no.cnt--;
                cur = cur.no;
            }
        }
    }

    int max(int k){
        var cur = root;
        int res = 0;
        for(int i = 15; i >= 0; i--){
            if(((k >> i) & 1) == 0){
                if(cur.no != null && cur.no.cnt > 0){
                    res ^= 1 << i;
                    cur = cur.no;
                }
                else{
                    cur = cur.nz;
                }
            }
            else{
                if(cur.nz != null && cur.nz.cnt > 0){
                    res ^= 1 << i;
                    cur = cur.nz;
                }
                else{
                    cur = cur.no;
                }
            }
        }
        return res;
    }
}