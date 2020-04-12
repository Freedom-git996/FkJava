package structs.tree.practice.redblack;

public class RedBlackTreeTest {

    public static void main(String[] args) {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        tree.put("1", 333);
        tree.put("12", 3331);
        tree.put("41", 3313);
        tree.put("21", 3133);
        tree.put("4", 33343);
        tree.put("33", 3353);

        tree.inorder(tree.getRoot());

        System.out.println("================");

        for(RedBlackNode node : tree) {
            System.out.println(node.getKey() + ":" + node.getValue());
        }

        System.out.println("================");

        System.out.println(tree.get("41"));

        System.out.println("================");
        tree.put("22", 2342);
        tree.put("1", 444);

        for(RedBlackNode node : tree) {
            System.out.println(node.getKey() + ":" + node.getValue());
        }
    }
}
