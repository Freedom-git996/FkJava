package structs.tree.demo.binary;

import java.util.*;

/**
 * @Auther: Vectory
 * @Date: 2019/10/12
 * @Description: structs.tree
 * @version: 1.0
 */
public class Tree {

    public void preTraverse(Node root){
        if(root != null) {
            System.out.print(root.val);
            preTraverse(root.left);
            preTraverse(root.right);
        }
    }

    public void inTraverse(Node root){
        if(root != null){
            inTraverse(root.left);
            System.out.print(root.val);
            inTraverse(root.right);
        }
    }

    public void postTraverse(Node root){
        if(root != null){
            postTraverse(root.left);
            postTraverse(root.right);
            System.out.print(root.val);
        }
    }

    public void DFSTraverse(Node root){
        ArrayDeque<Node> stack = new ArrayDeque<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node node = stack.pop();
            System.out.print(node.val);
            if(node.right != null)
                stack.push(node.right);
            if(node.left != null)
                stack.push(node.left);
        }
    }

    public void BFSTraverse(Node root){
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node node = queue.poll();
            System.out.print(node.val);
            if(node.left != null)
                queue.offer(node.left);
            if(node.right != null)
                queue.offer(node.right);
        }
    }

    // 包含层级信息的深度优先遍历
    public List<List<Character>> levelDFSTraverse(Node root) {
        List<List<Character>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        dfs(root, result, 0);
        return result;
    }

    private void dfs(Node root, List<List<Character>> result, int level) {
        if (root == null) {
            return;
        }
        if (level == result.size()) {
            result.add(new ArrayList<>());
        }
        result.get(level).add(root.val);

        dfs(root.left, result, level + 1);
        dfs(root.right, result, level + 1);
    }

    // 包含层级信息的广度优先遍历
    public List<List<Character>> levelBFSTraverse(Node root) {
        List<List<Character>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            ArrayList<Character> level = new ArrayList<Character>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node head = queue.poll();
                level.add(head.val);
                if (head.left != null) {
                    queue.offer(head.left);
                }
                if (head.right != null) {
                    queue.offer(head.right);
                }
            }
            result.add(level);
        }
        return result;
    }

    // 二叉树反转
    public void invert(Node root){
        if(root != null){
            Node temp = root.left;
            root.left = root.right;
            root.right = temp;

            invert(root.left);
            invert(root.right);
        }
    }

    // 最大值
    public int getMax(Node root){
        if(root != null){
            int left = getMax(root.left);
            int right = getMax(root.right);
            return Math.max(Math.max(left, right), root.val);
        }
        return 0;
    }
}