package itmo.st;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import itmo.st.rb.RBTree;

public class RBTreeTest {
   private RBTree tree;

   @BeforeEach
   void setUp() {
      tree = new RBTree();
   }

   @Test
   void testInsertAndContains() {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);

      assertTrue(tree.contains(10));
      assertTrue(tree.contains(5));
      assertTrue(tree.contains(15));
      assertFalse(tree.contains(20));
   }

   @Test
   void testEmptyTree() {
      assertFalse(tree.contains(10));
   }

   @Test
   void testDuplicateInsert() {
      tree.insert(10);
      tree.insert(10);

      assertTrue(tree.contains(10));
   }

   @Test
   void testMultipleInserts() {
      int[] values = { 7, 3, 18, 10, 22, 8, 11, 26 };

      for (int value : values) {
         tree.insert(value);
      }

      for (int value : values) {
         assertTrue(tree.contains(value));
      }

      assertFalse(tree.contains(100));
      assertFalse(tree.contains(0));
   }

   @Test
   void testFlipColors() {
      RBTree.Node node = tree.new Node(10, false);
      node.left = tree.new Node(5, true);
      node.right = tree.new Node(15, true);

      tree.flipColors(node);

      assertTrue(node.color);
      assertFalse(node.left.color);
      assertFalse(node.right.color);
   }

   @Test
   void testFlipColorsWithNullLeaves() {
      RBTree.Node node = tree.new Node(10, false);
      node.left = null;
      node.right = null;

      tree.flipColors(node);

      assertTrue(node.color);
      assertNull(node.left);
      assertNull(node.right);

      node.right = tree.new Node(15, false);
      tree.flipColors(node);
      assertFalse(node.color);
      assertNull(node.left);
      assertTrue(node.right.color);

      node.right = null;
      node.left = tree.new Node(5, true);
      tree.flipColors(node);
      assertTrue(node.color);
      assertFalse(node.left.color);
      assertNull(node.right);
   }


   @Test
   void testDeleteAndBalance() {
      int[] values = {10, 5, 15, 3, 7, 12, 18};
      for (int value : values) {
         tree.insert(value);
      }

      // Удаление листа
      tree.delete(3);
      assertFalse(tree.contains(3));
      assertTrue(tree.contains(5));
      assertTrue(tree.contains(7));

      // Удаление узла с одним потомком
      tree.delete(15);
      assertFalse(tree.contains(15));
      assertTrue(tree.contains(18));

      // Удаление узла с двумя потомками
      tree.delete(10);
      assertFalse(tree.contains(10));
      assertTrue(tree.contains(12));
      assertTrue(tree.contains(5));
      assertTrue(tree.contains(7));
      assertTrue(tree.contains(18));

      // Удаление корня
      tree.delete(12);
      assertFalse(tree.contains(12));
      assertTrue(tree.contains(5));
      assertTrue(tree.contains(7));
      assertTrue(tree.contains(18));

      // Удаление всех элементов
      for (int value : new int[]{5, 7, 18}) {
         tree.delete(value);
         assertFalse(tree.contains(value));
      }

      // Проверка пустого дерева
      assertNull(tree.root);
   }

   @Test
   void testDeleteNonExistingElement() {
      tree.insert(10);
      tree.insert(5);
      
      tree.delete(15);
      assertTrue(tree.contains(10));
      assertTrue(tree.contains(5));
      assertFalse(tree.contains(15));
   }

   @Test
   void testBalanceAfterDeletion() {
      int[] values = {10, 5, 15, 3, 7, 12, 18};
      for (int value : values) {
         tree.insert(value);
      }

      tree.delete(3);
      assertFalse(tree.root.left.left.color);

      tree.delete(15);
      assertFalse(tree.root.right.color);

      tree.delete(10);
      assertFalse(tree.root.color);
   }

}
