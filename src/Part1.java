import java.util.Comparator;

import modules.DynamicSet;


class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer a, Integer b) {
        if (a > b) {
            return 1;
        }
        else if (a.equals(b)) {
            return 0;
        }
        return -1;
    }
}


public class Part1 {
    public static void main(String[] args) throws Exception {
        DynamicSet<Integer> setA = new DynamicSet<>(new IntegerComparator());
        for (int i = 0; i < 10; i++) {
            setA.add(i);
        }
        System.out.println("{" + setA.toString() + "}");
    }
}
