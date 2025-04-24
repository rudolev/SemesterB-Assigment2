/**
 * This tester is presented by ChatGPT and mishka.ber.
 *
 * It uses internal methods for validation. Add the following methods to your
 * implementation
 * files temporarily for testing purposes and remove them before submission:
 *
 * In MyDynamicSet.java:
 * 
 * public ListLink<T> getLinkForKey(int key) {
 * ListLink<T> current = this.minimumLink;
 * while (current != null) {
 * if (current.key() == key)
 * return current;
 * current = current.getNext();
 * }
 * return null;
 * }
 *
 */

public class Assignment2Tester {

    private static int failedTests = 0;
    private static StringBuilder failedDetails = new StringBuilder();

    public static void main(String[] args) {
        runSafely("testMyArray", Assignment2Tester::testMyArray);
        runSafely("testMyLinkedList", Assignment2Tester::testMyLinkedList);
//        runSafely("testMySortedLinkedList", Assignment2Tester::testMySortedLinkedList);
//        runSafely("testMyAVLTree", Assignment2Tester::testMyAVLTree);
//        runSafely("testMyDynamicSet", Assignment2Tester::testMyDynamicSet);
//        runSafely("testMyFirstDataStructure", Assignment2Tester::testMyFirstDataStructure);
//        runSafely("testMySecondDataStructure", Assignment2Tester::testMySecondDataStructure);
//        runSafely("stressTestDynamicSet", Assignment2Tester::stressTestDynamicSet);
//        runSafely("stressTestFirstDataStructure", Assignment2Tester::stressTestFirstDataStructure);
//        runSafely("stressTestSecondDataStructure", Assignment2Tester::stressTestSecondDataStructure);

        System.out.println("\n===== TEST SUMMARY =====");
        if (failedTests == 0) {
            System.out.println("All tests PASSED ✅");
        } else {
            System.out.println(failedTests + " test(s) FAILED ❌:");
            System.out.println(failedDetails.toString());
        }
    }


    private static void runSafely(String name, Runnable testMethod) {
//        try {
//            testMethod.run();
//        } catch (Exception e) {
//            test("Crash in " + name + ": " + e.getMessage(), false);
//        }

        testMethod.run();

    }

    private static void test(String name, boolean result, Object... debugInfo) {
        if (result) {
            System.out.println("PASSED: " + name);
        } else {
            System.out.println("FAILED: " + name);
            StringBuilder debugText = new StringBuilder();
            for (Object info : debugInfo) {
                String line = "    -> " + info;
                System.out.println(line);
                debugText.append(line).append("\n");
            }
            failedTests++;
            failedDetails.append(" - ").append(name).append("\n").append(debugText);
        }
    }

    private static void testMyArray() {
        MyArray<String> arr = new MyArray<>(10);
        ArrayElement<String> a = new ArrayElement<>(1, "a");
        ArrayElement<String> b = new ArrayElement<>(2, "b");
        arr.insert(a);
        arr.insert(b);
        arr.reverse();
        test("MyArray reverse should reverse order (expected: b,a)", arr.get(0).equals(b) && arr.get(1).equals(a),
                "arr[0]=" + arr.get(0), "arr[1]=" + arr.get(1));

        for (int i = 3; i < 10; i++) {
            arr.insert(new ArrayElement<>(i, "val" + i));
        }
        arr.reverse();
        test("MyArray reverse with more elements (expected: 9,8,...)",
                arr.get(0).equals(new ArrayElement<>(9, "val9")) && arr.get(1).equals(new ArrayElement<>(8, "val8")),
                "arr[0]=" + arr.get(0), "arr[1]=" + arr.get(1));

        MyArray<String> emptyArr = new MyArray<>(0);
        emptyArr.reverse();
        test("MyArray reverse empty array (expected: empty)", emptyArr.size() == 0, "size=" + emptyArr.size());

        MyArray<String> singleArr = new MyArray<>(1);
        ArrayElement<String> only = new ArrayElement<>(42, "only");
        singleArr.insert(only);
        singleArr.reverse();
        test("MyArray reverse with single element", singleArr.get(0).equals(only), "arr[0]=" + singleArr.get(0));
    }

    private static void testMyLinkedList() {
        MyLinkedList<String> list = new MyLinkedList<>();
        ListLink<String> a = new ListLink<>(1, "a");
        ListLink<String> b = new ListLink<>(2, "b");
        list.insert(a);
        list.insert(b);
        list.reverse();
        test("MyLinkedList reverse (expected: a,b)", list.head().equals(a) && list.tail().equals(b),
                "head=" + list.head(), "tail=" + list.tail());
        list.delete(a);
        test("MyLinkedList delete head (expected: head=b)", list.head().equals(b), "head=" + list.head());
        list.delete(b);
        test("MyLinkedList delete tail and empty list (expected: head=null)",
                list.head() == null && list.tail() == null, "head=" + list.head(), "tail=" + list.tail());

        MyLinkedList<String> singleList = new MyLinkedList<>();
        ListLink<String> solo = new ListLink<>(7, "solo");
        singleList.insert(solo);
        singleList.reverse();
        test("MyLinkedList reverse with single element", singleList.head().equals(solo), "head=" + singleList.head());
    }

    private static void testMySortedLinkedList() {
        MySortedLinkedList<String> list = new MySortedLinkedList<>();
        ListLink<String> a = new ListLink<>(2, "a");
        ListLink<String> b = new ListLink<>(1, "b");
        ListLink<String> c = new ListLink<>(3, "c");
        list.insert(a);
        list.insert(b);
        list.insert(c);
        test("MySortedLinkedList insert sorted (expected: b,a,c)", list.head().equals(b) && list.tail().equals(c),
                "head=" + list.head(), "tail=" + list.tail());
        list.delete(b);
        test("MySortedLinkedList delete head (expected: a)", list.head().equals(a), "head=" + list.head());
        list.delete(c);
        test("MySortedLinkedList delete tail (expected: a)", list.tail().equals(a), "tail=" + list.tail());
    }

    private static void testMyAVLTree() {
        MyAVLTree<String> tree = new MyAVLTree<>();
        tree.insert(new TreeNode<>(3, "c"));
        tree.insert(new TreeNode<>(2, "b"));
        tree.insert(new TreeNode<>(1, "a"));
        test("MyAVLTree depthOfMin = 2", tree.depthOfMin() == 2, "depthOfMin=" + tree.depthOfMin());
        TreeNode<String> toDelete = tree.search(2);
        tree.delete(toDelete);
        test("MyAVLTree delete removes key 2", tree.search(2) == null, "search(2)=" + tree.search(2));

        TreeNode<String> node = new TreeNode<>(99, "last");
        tree.insert(node);
        tree.delete(node);
        test("MyAVLTree delete single node", tree.search(99) == null);
        test("MyAVLTree search for non-existing key", tree.search(999) == null);
    }

    private static void testMyDynamicSet() {
        MyDynamicSet<String> set = new MyDynamicSet<>(10);
        set.insert(new Element<>(2, "a"));
        set.insert(new Element<>(1, "b"));
        ListLink<String> a = set.getLinkForKey(2);
        ListLink<String> b = set.getLinkForKey(1);
        test("MyDynamicSet minimum returns smallest element", set.minimum().key() == 1);
        test("MyDynamicSet maximum returns largest element", set.maximum().key() == 2);
        test("MyDynamicSet search returns correct element", set.search(2).satelliteData().equals("a"));
        test("MyDynamicSet search returns null for missing element", set.search(999) == null);
        Element<String> succ = set.successor(b);
        test("MyDynamicSet successor returns correct element", succ != null && succ.equals(a));
        Element<String> pred = set.predecessor(a);
        test("MyDynamicSet predecessor returns correct element", pred != null && pred.equals(b));
        set.delete(b);
        test("MyDynamicSet delete removes element", set.search(1) == null);

        // Edge case: delete again, should not crash
        set.delete(b);
        test("MyDynamicSet double delete is safe", true);
    }

    private static void testMyFirstDataStructure() {
        MyFirstDataStructure<String> ds = new MyFirstDataStructure<>(10);
        ds.insert(new Element<>(1, "a"));
        ds.insert(new Element<>(2, "b"));
        test("MyFirstDataStructure max is 2", ds.maximum().key() == 2, "maximum=" + ds.maximum());
        test("MyFirstDataStructure first = 1", ds.first().key() == 1, "first=" + ds.first());
        test("MyFirstDataStructure last = 2", ds.last().key() == 2, "last=" + ds.last());
        ds.findAndRemove(2);
        test("MyFirstDataStructure remove 2, max now 1", ds.maximum().key() == 1, "maximum=" + ds.maximum());

        ds.findAndRemove(999); // remove non-existing
        test("MyFirstDataStructure remove non-existing key", true);

        ds.findAndRemove(1);
        test("MyFirstDataStructure empty after all removals",
                ds.maximum() == null && ds.first() == null && ds.last() == null);
    }

    private static void testMySecondDataStructure() {
        MySecondDataStructure ds = new MySecondDataStructure(10);
        ds.insert(new Product(1, 2, 100, "A"));
        ds.insert(new Product(2, 3, 200, "B"));
        test("MySecondDataStructure avgQuality = 2.5", ds.avgQuality() == 2.5, "avgQuality=" + ds.avgQuality());
        test("MySecondDataStructure medianQuality = 2", ds.medianQuality() == 2, "medianQuality=" + ds.medianQuality());
        ds.raisePrice(100, 3);
        test("MySecondDataStructure mostExpensive = id 2", ds.mostExpensive().id() == 2);
        ds.findAndRemove(2);
        test("MySecondDataStructure remove 2, mostExpensive = id 1", ds.mostExpensive().id() == 1);
        ds.findAndRemove(1);
        test("MySecondDataStructure handles empty avg/median = -1", ds.avgQuality() == -1 && ds.medianQuality() == -1);

        ds.raisePrice(1000, 5); // empty raise
        test("MySecondDataStructure raisePrice on empty safe", true);

        ds.insert(new Product(3, 4, 50, "X"));
        ds.insert(new Product(4, 4, 150, "Y"));
        test("MySecondDataStructure median with even count = 4", ds.medianQuality() == 4);
    }

    private static void stressTestDynamicSet() {
        MyDynamicSet<String> set = new MyDynamicSet<>(1000);
        boolean allInserted = true;
        for (int i = 0; i < 1000; i++)
            set.insert(new Element<>(i, "val" + i));
        for (int i = 0; i < 1000; i++) {
            Element<String> found = set.search(i);
            if (found == null || !found.satelliteData().equals("val" + i)) {
                allInserted = false;
                break;
            }
        }
        test("Stress test MyDynamicSet 1000 inserts/searches", allInserted);
    }

    private static void stressTestFirstDataStructure() {
        MyFirstDataStructure<String> ds = new MyFirstDataStructure<>(1000);
        for (int i = 0; i < 1000; i++)
            ds.insert(new Element<>(i, "v" + i));
        for (int i = 0; i < 1000; i++)
            ds.findAndRemove(i);
        test("Stress test MyFirstDataStructure empty after 1000 removals", ds.maximum() == null,
                "maximum=" + ds.maximum());
    }

    private static void stressTestSecondDataStructure() {
        MySecondDataStructure ds = new MySecondDataStructure(1000);
        for (int i = 0; i < 1000; i++)
            ds.insert(new Product(i, i % 6, i + 1, "P" + i));
        for (int i = 0; i < 1000; i++)
            ds.raisePrice(1, i % 6);
        test("Stress test MySecondDataStructure inserts valid", true);
        test("Stress test MySecondDataStructure avgQuality >= 0", ds.avgQuality() >= 0,
                "avgQuality=" + ds.avgQuality());
        test("Stress test MySecondDataStructure medianQuality in [0,5]",
                ds.medianQuality() >= 0 && ds.medianQuality() <= 5, "medianQuality=" + ds.medianQuality());
        test("Stress test MySecondDataStructure mostExpensive not null", ds.mostExpensive() != null,
                "mostExpensive=" + ds.mostExpensive());
    }
}
