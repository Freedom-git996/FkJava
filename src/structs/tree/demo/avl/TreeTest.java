package structs.tree.demo.avl;

public class TreeTest {

    public static void main(String[] args) {
        Tree<Integer, String> tree = new Tree<>();
        tree.put(3, "aaa");
        tree.put(2, "bbb");
        tree.put(1, "ccc");
        tree.put(4, "ddd");
        tree.put(5, "eee");
        tree.put(6, "fff");
        tree.put(7, "ggg");
        tree.put(10, "hhh");
        tree.put(9, "lll");

        tree.inorder(tree.getRoot());

        System.out.println("=======");

        for(Node node : tree) {
            System.out.println(node.getKey() + ":" + node.getValue());
        }

        System.out.println("=======");

        System.out.println(tree.get(5));

        System.out.println("================");

        System.out.println("the max node is " + tree.getMaximum(tree.getRoot()));

        System.out.println("the min node is " + tree.getMinimum(tree.getRoot()));

        System.out.println("================");

        System.out.println("5's prev node is " + tree.getPre(tree.getNode(5)));

        System.out.println("5's successor node is " + tree.getSuccessor(tree.getNode(5)));

        System.out.println("================");

        tree.remove(9);

        tree.remove(5);

        tree.inorder(tree.getRoot());
    }
}