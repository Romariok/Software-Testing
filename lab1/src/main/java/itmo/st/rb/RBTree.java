package itmo.st.rb;

public class RBTree {
private static final boolean RED = true;
private static final boolean BLACK = false;

public class Node {
    public int key;
    public Node left, right;
    public boolean color;

    public Node(int key, boolean color) {
        this.key = key;
        this.color = color;
    }
}

public Node root;

private boolean isRed(Node x) {
    if (x == null) return false;
    return x.color == RED;
}

private Node rotateLeft(Node h) {
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    x.color = h.color;
    h.color = RED;
    return x;
}

private Node rotateRight(Node h) {
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    x.color = h.color;
    h.color = RED;
    return x;
}

public void flipColors(Node h) {
    h.color = !h.color;
    if (h.left != null) h.left.color = !h.left.color;
    if (h.right != null) h.right.color = !h.right.color;
}

public void insert(int key) {
    root = insert(root, key);
    root.color = BLACK;
}

private Node insert(Node h, int key) {
    if (h == null) return new Node(key, RED);
    
    if (key < h.key) h.left = insert(h.left, key);
    else if (key > h.key) h.right = insert(h.right, key);
    
    if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
    if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left) && isRed(h.right)) flipColors(h);

    return h;
}

public boolean contains(int key) {
    return contains(root, key);
}

private boolean contains(Node x, int key) {
    while (x != null) {
        if (key < x.key) x = x.left;
        else if (key > x.key) x = x.right;
        else return true;
    }
    return false;
}


public void delete(int key) {
    if (!contains(key)) return;
    
    if (!isRed(root.left) && !isRed(root.right))
        root.color = RED;
    
    root = delete(root, key);
    if (root != null) root.color = BLACK;
}

private Node delete(Node h, int key) {
    if (key < h.key) {
        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);
        h.left = delete(h.left, key);
    } else {
        if (isRed(h.left))
            h = rotateRight(h);
        if (key == h.key && h.right == null)
            return null;
        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);
        if (key == h.key) {
            Node x = min(h.right);
            h.key = x.key;
            h.right = deleteMin(h.right);
        } else {
            h.right = delete(h.right, key);
        }
    }
    return balance(h);
}

private Node moveRedLeft(Node h) {
    flipColors(h);
    if (isRed(h.right.left)) {
        h.right = rotateRight(h.right);
        h = rotateLeft(h);
        flipColors(h);
    }
    return h;
}

private Node moveRedRight(Node h) {
    flipColors(h);
    if (isRed(h.left.left)) {
        h = rotateRight(h);
        flipColors(h);
    }
    return h;
}

private Node deleteMin(Node h) {
    if (h.left == null)
        return null;
    
    if (!isRed(h.left) && !isRed(h.left.left))
        h = moveRedLeft(h);
    
    h.left = deleteMin(h.left);
    return balance(h);
}

private Node min(Node x) {
    while (x.left != null)
        x = x.left;
    return x;
}

private Node balance(Node h) {
    if (isRed(h.right)) h = rotateLeft(h);
    if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left) && isRed(h.right)) flipColors(h);
    
    return h;
}

}
