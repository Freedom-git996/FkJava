package structs.tree.demo.binary;

public class TreeTest {

    public static void main(String[] args) {
        Node H = new Node('H', null, null);
        Node K = new Node('K', null, null);
        Node D = new Node('D', null , null);
        Node G = new Node('G', H, K);
        Node C = new Node('C', D, null);
        Node F = new Node('F', G, null);
        Node B = new Node('B', null, C);
        Node E = new Node('E', null, F);
        Node A = new Node('A', B, E);

        Tree tree = new Tree();
        tree.preTraverse(A);
        System.out.println("\n-------");
        tree.inTraverse(A);
        System.out.println("\n-------");
        tree.postTraverse(A);
        System.out.println("\n-------");
        tree.DFSTraverse(A);
        System.out.println("\n-------");
        tree.BFSTraverse(A);
        System.out.println("\n-------");
        tree.levelDFSTraverse(A);
        tree.levelBFSTraverse(A);
    }
}
