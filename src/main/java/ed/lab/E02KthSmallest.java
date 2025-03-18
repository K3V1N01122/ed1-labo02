package ed.lab;

public class E02KthSmallest {

    public int kthSmallest(TreeNode<Integer> root, int k) {
        return kthSmallestHelper(root, new int[]{k});
    }

    private int kthSmallestHelper(TreeNode<Integer> node, int[] k) {
        if (node == null) {
            return -1; // Valor inválido en caso de que k sea mayor que el número de nodos
        }

        // Buscar en el subárbol izquierdo
        int left = kthSmallestHelper(node.left, k);
        if (k[0] == 0) {
            return left;
        }

        // Procesar el nodo actual
        k[0]--;
        if (k[0] == 0) {
            return node.value;
        }

        // Buscar en el subárbol derecho
        return kthSmallestHelper(node.right, k);
    }
}
