//Kevin Palencia
package ed.lab;

public class E01InvertBT {
    public TreeNode<Integer> invertTree(TreeNode<Integer> root) {
        if (root == null) {
            return null;
        }

        // Intercambiar los hijos izquierdo y derecho
        TreeNode<Integer> temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);

        return root;
    }
}
