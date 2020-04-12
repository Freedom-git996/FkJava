package base.basic;

public class CommandTest {

    public static void main(String[] args) {
        ProcessArray ps = new ProcessArray();
        int[] target = {3, -4, 6, 4};
        ps.process(target, (int[] target1) -> {
            int sum = 0;
            for(int i : target1) {
                sum += i;
            }
            System.out.println(sum);
        });
    }
}
