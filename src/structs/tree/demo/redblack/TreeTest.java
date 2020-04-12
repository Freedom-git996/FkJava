package structs.tree.demo.redblack;

public class TreeTest {

    public static void main(String[] args) {
        Tree<String, Integer> tree = new Tree<>();
        tree.put("1", 333);
        tree.put("12", 3331);
        tree.put("41", 3313);
        tree.put("21", 3133);
        tree.put("4", 33343);
        tree.put("33", 3353);

        tree.inorder(tree.getRoot());

        System.out.println("================");

        for(Node node : tree) {
            System.out.println(node.getKey() + ":" + node.getValue());
        }

        System.out.println("================");

        System.out.println("the max node is " + tree.getMaximum(tree.getRoot()));

        System.out.println("the min node is " + tree.getMinimum(tree.getRoot()));

        System.out.println("================");

        tree.remove("33");

        for(Node node : tree) {
            System.out.println(node.getKey() + ":" + node.getValue());
        }
    }
}
