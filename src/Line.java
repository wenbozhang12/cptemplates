public class Line {

    double xintercept(long[] l1, long[] l2){
        return (double) (l2[1] - l1[1])/(l1[0] - l2[0]);
    }

    long eval(long[] li, long x){
        return li[0] * x + li[1];
    }
}
