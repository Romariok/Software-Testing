package itmo.st;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import itmo.st.rb.RBTree;

public class RBTreeTest {
   private RBTree tree;

   private static final int ROOT_VALUE = 10;
   private static final int LEFT_CHILD = 5;
   private static final int RIGHT_CHILD = 15;
   private static final int NON_EXISTING_VALUE = 20;

   private static final int[] MULTIPLE_VALUES = { 7, 3, 18, 10, 22, 8, 11, 26 };
   private static final int[] BALANCED_TREE_VALUES = { 10, 5, 15, 3, 7, 12, 18 };

   @BeforeEach
   void setUp() {
      tree = new RBTree();
   }

   @Test
   void testBasicOperations() {
      tree.insert(ROOT_VALUE);
      tree.insert(LEFT_CHILD);
      tree.insert(RIGHT_CHILD);
      assertAll(
            () -> assertTrue(tree.contains(ROOT_VALUE)),
            () -> assertTrue(tree.contains(LEFT_CHILD)),
            () -> assertTrue(tree.contains(RIGHT_CHILD)),
            () -> assertFalse(tree.contains(NON_EXISTING_VALUE)));
   }

   @ParameterizedTest
   @ValueSource(ints = { -1, 0, 20, 30 })
   void testDuplicateInsert(int value) {
      tree.insert(value);
      tree.insert(value);
      assertTrue(tree.contains(value));
   }

   @Test
   void testMultipleInserts() {
      insertValues(MULTIPLE_VALUES);
      assertArrayContains(MULTIPLE_VALUES);
      assertFalse(tree.contains(100));
      assertFalse(tree.contains(0));
   }

   @ParameterizedTest
   @MethodSource("provideNodesForColorFlipping")
   void testFlipColorsParameterized(int nodeValue, int leftValue, int rightValue,
         boolean initialNodeColor, boolean initialLeftColor,
         boolean initialRightColor) {
      RBTree.Node node = createNode(nodeValue, initialNodeColor);
      node.left = createNode(leftValue, initialLeftColor);
      node.right = createNode(rightValue, initialRightColor);

      tree.flipColors(node);

      assertTrue(node.color);
      assertFalse(node.left.color);
      assertFalse(node.right.color);
   }

   private static Stream<Arguments> provideNodesForColorFlipping() {
      return Stream.of(
            Arguments.of(ROOT_VALUE, LEFT_CHILD, RIGHT_CHILD, false, true, true));
   }

   @Test
   void testDeleteOperations() {
      insertValues(BALANCED_TREE_VALUES);
      performDeletionTests();
   }

   private void insertValues(int[] values) {
      for (int value : values) {
         tree.insert(value);
      }
   }

   private void assertArrayContains(int[] values) {
      for (int value : values) {
         assertTrue(tree.contains(value));
      }
   }

   private RBTree.Node createNode(int value, boolean color) {
      return tree.new Node(value, color);
   }

   private void performDeletionTests() {
      deleteAndVerify(3, new int[] { 5, 7 });

      deleteAndVerify(15, new int[] { 18 });

      deleteAndVerify(10, new int[] { 12, 5, 7, 18 });

      deleteAndVerify(12, new int[] { 5, 7, 18 });

      for (int value : new int[] { 5, 7, 18 }) {
         deleteAndVerify(value, new int[] {});
      }

      assertNull(tree.root);
   }

   private void deleteAndVerify(int valueToDelete, int[] remainingValues) {
      tree.delete(valueToDelete);
      assertFalse(tree.contains(valueToDelete));
      for (int value : remainingValues) {
         assertTrue(tree.contains(value));
      }
   }
}
