/**
 * Assignment2Tester.java
 * This tester is presented by ChatGPT and mishka.ber.
 *
 * It uses internal methods for validation. Add the following methods to your
 * implementation files temporarily for testing purposes and remove them before
 * submission:
 */

public class Assignment2Tester {
    private static final int STRESS_SIZE = 1000;

    private static int failedTests = 0;
    private static StringBuilder failedDetails = new StringBuilder();

    public static void main(String[] args) {
        runSafely("testMyArray", Assignment2Tester::testMyArray);
        runSafely("testMyLinkedList", Assignment2Tester::testMyLinkedList);
        runSafely("testMyAVLTree", Assignment2Tester::testMyAVLTree);
        runSafely("testMyDynamicSet", Assignment2Tester::testMyDynamicSet);
        runSafely("testMyFirstDataStructure", Assignment2Tester::testMyFirstDataStructure);
        runSafely("testMySecondDataStructure", Assignment2Tester::testMySecondDataStructure);

        // Stress tests
        runSafely("stressMyArray", Assignment2Tester::stressTestMyArray);
        runSafely("stressMyLinkedList", Assignment2Tester::stressTestMyLinkedList);
        runSafely("stressMyAVLTree", Assignment2Tester::stressTestMyAVLTree);
        runSafely("stressMyDynamicSet", Assignment2Tester::stressTestMyDynamicSet);
        runSafely("stressMyFirstDataStructure", Assignment2Tester::stressTestMyFirstDataStructure);
        runSafely("stressMySecondDataStructure", Assignment2Tester::stressTestMySecondDataStructure);

        System.out.println("\n===== TEST SUMMARY =====");
        if (failedTests == 0) {
            System.out.println("All tests PASSED ✅ ✨");
        } else {
            System.out.println(failedTests + " test(s) FAILED ❌:");
            System.out.println(failedDetails.toString());
        }
    }

    private static void runSafely(String name, Runnable testMethod) {
        try {
            testMethod.run();
        } catch (Exception e) {
            test(name + " CRASHED", false,
                    "Exception: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private static void test(String name, boolean result, Object... debugInfo) {
        if (result) {
            System.out.println("✅ PASSED: " + name);
        } else {
            System.out.println("❌ FAILED: " + name);
            recordFailure(name, debugInfo);
        }
    }

    private static void recordFailure(String name, Object... debugInfo) {
        StringBuilder entry = new StringBuilder();
        entry.append(" - ").append(name).append("\n");
        for (Object info : debugInfo) {
            String line = "    -> " + info;
            entry.append(line).append("\n");
            System.out.println(line);
        }
        failedDetails.append(entry.toString());
        failedTests++;
    }

    /**
     * Tests for MyArray.reverse()
     */
    private static void testMyArray() {
        System.out.println("\n===== MyArray Tests =====");

        runSafely("testReverseEmptyArray", () -> {
            MyArray<String> array = new MyArray<>();
            array.reverse();
            boolean ok = array.size() == 0;
            test("testReverseEmptyArray", ok,
                    "Expected size=0",
                    "Got size=" + array.size());
        });

        runSafely("testReverseSingleElementArray", () -> {
            MyArray<String> array = new MyArray<>();
            array.insert(new ArrayElement<>(1, "A"));
            array.reverse();

            boolean ok = array.size() == 1
                    && array.get(0).key() == 1
                    && "A".equals(array.get(0).satelliteData())
                    && array.get(0).index() == 0;
            test("testReverseSingleElementArray", ok,
                    "Expected one element [1:A@0]",
                    "Got [" + array.get(0).key() + ":" + array.get(0).satelliteData() + "@" + array.get(0).index()
                            + "]");
        });

        runSafely("testReverseMultipleElementsArray", () -> {
            MyArray<String> array = new MyArray<>();
            array.insert(new ArrayElement<>(1, "A"));
            array.insert(new ArrayElement<>(2, "B"));
            array.insert(new ArrayElement<>(3, "C"));
            array.reverse();

            boolean ok = true;
            ok &= array.get(0).key() == 3 && "C".equals(array.get(0).satelliteData()) && array.get(0).index() == 0;
            ok &= array.get(1).key() == 2 && "B".equals(array.get(1).satelliteData()) && array.get(1).index() == 1;
            ok &= array.get(2).key() == 1 && "A".equals(array.get(2).satelliteData()) && array.get(2).index() == 2;

            String expected = "[3:C@0, 2:B@1, 1:A@2]";
            String actual = "[" +
                    array.get(0).key() + ":" + array.get(0).satelliteData() + "@" + array.get(0).index() + ", " +
                    array.get(1).key() + ":" + array.get(1).satelliteData() + "@" + array.get(1).index() + ", " +
                    array.get(2).key() + ":" + array.get(2).satelliteData() + "@" + array.get(2).index() + "]";
            test("testReverseMultipleElementsArray", ok,
                    "Expected order: " + expected,
                    "Got order:      " + actual);
        });

        runSafely("testReverseArrayWithNullElements", () -> {
            MyArray<String> array = new MyArray<>();
            array.insert(new ArrayElement<>(1, null));
            array.insert(new ArrayElement<>(2, "B"));
            array.insert(new ArrayElement<>(3, "C"));
            array.reverse();

            boolean ok = true;
            ok &= array.get(0).key() == 3 && "C".equals(array.get(0).satelliteData()) && array.get(0).index() == 0;
            ok &= array.get(1).key() == 2 && "B".equals(array.get(1).satelliteData()) && array.get(1).index() == 1;
            ok &= array.get(2).key() == 1 && array.get(2).satelliteData() == null && array.get(2).index() == 2;

            String expected = "[3:C@0, 2:B@1, 1:null@2]";
            String actual = "[" +
                    array.get(0).key() + ":" + array.get(0).satelliteData() + "@" + array.get(0).index() + ", " +
                    array.get(1).key() + ":" + array.get(1).satelliteData() + "@" + array.get(1).index() + ", " +
                    array.get(2).key() + ":" + array.get(2).satelliteData() + "@" + array.get(2).index() + "]";
            test("testReverseArrayWithNullElements", ok,
                    "Expected order: " + expected,
                    "Got order:      " + actual);
        });

        runSafely("testReverseArrayWithDuplicates", () -> {
            MyArray<String> array = new MyArray<>();
            array.insert(new ArrayElement<>(1, "A"));
            array.insert(new ArrayElement<>(2, "A"));
            array.insert(new ArrayElement<>(3, "C"));
            array.reverse();

            boolean ok = true;
            ok &= array.get(0).key() == 3 && "C".equals(array.get(0).satelliteData()) && array.get(0).index() == 0;
            ok &= array.get(1).key() == 2 && "A".equals(array.get(1).satelliteData()) && array.get(1).index() == 1;
            ok &= array.get(2).key() == 1 && "A".equals(array.get(2).satelliteData()) && array.get(2).index() == 2;

            String expected = "[3:C@0, 2:A@1, 1:A@2]";
            String actual = "[" +
                    array.get(0).key() + ":" + array.get(0).satelliteData() + "@" + array.get(0).index() + ", " +
                    array.get(1).key() + ":" + array.get(1).satelliteData() + "@" + array.get(1).index() + ", " +
                    array.get(2).key() + ":" + array.get(2).satelliteData() + "@" + array.get(2).index() + "]";
            test("testReverseArrayWithDuplicates", ok,
                    "Expected order: " + expected,
                    "Got order:      " + actual);
        });
    }

    /**
     * Tests for MyLinkedList.reverse()
     */
    private static void testMyLinkedList() {
        System.out.println("\n===== MyLinkedList Tests =====");

        runSafely("testReverseEmptyList", () -> {
            MyLinkedList<String> list = new MyLinkedList<>();
            list.reverse();
            boolean ok = (list.head() == null && list.tail() == null);
            String headDesc = list.head() != null ? list.head().key() + ":" + list.head().satelliteData() : "null";
            String tailDesc = list.tail() != null ? list.tail().key() + ":" + list.tail().satelliteData() : "null";
            test("testReverseEmptyList", ok,
                    "Expected head=null, tail=null",
                    "Got head=" + headDesc + ", tail=" + tailDesc);
        });

        runSafely("testReverseSingleElementList", () -> {
            MyLinkedList<String> list = new MyLinkedList<>();
            list.insert(new ListLink<>(1, "A"));
            list.reverse();
            boolean ok = (list.head() != null && list.tail() != null && list.head() == list.tail()
                    && list.head().key() == 1
                    && "A".equals(list.head().satelliteData()));
            String desc = list.head() != null ? list.head().key() + ":" + list.head().satelliteData() : "null";
            test("testReverseSingleElementList", ok,
                    "Expected single element head=tail as 1:A",
                    "Got head=tail as " + desc);
        });

        runSafely("testReverseMultipleElementsList", () -> {
            MyLinkedList<String> list = new MyLinkedList<>();
            list.insert(new ListLink<>(1, "A"));
            list.insert(new ListLink<>(2, "B"));
            list.insert(new ListLink<>(3, "C"));
            list.reverse();

            StringBuilder sb = new StringBuilder();
            ListLink<String> cur = list.head();
            while (cur != null) {
                sb.append(cur.key()).append(":").append(cur.satelliteData());
                if (cur.getNext() != null)
                    sb.append(",");
                cur = cur.getNext();
            }
            String actual = sb.toString();
            String expected = "1:A,2:B,3:C";
            boolean ok = expected.equals(actual);
            test("testReverseMultipleElementsList", ok,
                    "Expected order: " + expected,
                    "Got order:      " + actual);
        });

        runSafely("testReverseListWithNullElements", () -> {
            MyLinkedList<String> list = new MyLinkedList<>();
            list.insert(new ListLink<>(1, null));
            list.insert(new ListLink<>(2, "B"));
            list.insert(new ListLink<>(3, "C"));
            list.reverse();

            StringBuilder sb = new StringBuilder();
            ListLink<String> cur = list.head();
            while (cur != null) {
                sb.append(cur.key()).append(":").append(cur.satelliteData());
                if (cur.getNext() != null)
                    sb.append(",");
                cur = cur.getNext();
            }
            String actual = sb.toString();
            String expected = "1:null,2:B,3:C";
            boolean ok = expected.equals(actual);
            test("testReverseListWithNullElements", ok,
                    "Expected order: " + expected,
                    "Got order:      " + actual);
        });

        runSafely("testReverseListWithDuplicates", () -> {
            MyLinkedList<String> list = new MyLinkedList<>();
            list.insert(new ListLink<>(1, "A"));
            list.insert(new ListLink<>(2, "A"));
            list.insert(new ListLink<>(3, "C"));
            list.reverse();

            StringBuilder sb = new StringBuilder();
            ListLink<String> cur = list.head();
            while (cur != null) {
                sb.append(cur.key()).append(":").append(cur.satelliteData());
                if (cur.getNext() != null)
                    sb.append(",");
                cur = cur.getNext();
            }
            String actual = sb.toString();
            String expected = "1:A,2:A,3:C";
            boolean ok = expected.equals(actual);
            test("testReverseListWithDuplicates", ok,
                    "Expected order: " + expected,
                    "Got order:      " + actual);
        });
    }

    /**
     * Tests for MyAVLTree.depthOfMin()
     */
    private static void testMyAVLTree() {
        System.out.println("\n===== MyAVLTree Tests =====");

        runSafely("testEmptyTree", () -> {
            MyAVLTree<String> tree = new MyAVLTree<>();
            int depth = tree.depthOfMin();
            boolean ok = depth == -1;
            test("testEmptyTree", ok,
                    "Expected depth=-1",
                    "Got depth=" + depth);
        });

        runSafely("testSingleElementTree", () -> {
            MyAVLTree<String> tree = new MyAVLTree<>();
            tree.insert(new TreeNode<>(10, "A"));
            int depth = tree.depthOfMin();
            boolean ok = depth == 0;
            test("testSingleElementTree", ok,
                    "Expected depth=0",
                    "Got depth=" + depth);
        });

        runSafely("testBalancedTree", () -> {
            MyAVLTree<String> tree = new MyAVLTree<>();
            tree.insert(new TreeNode<>(20, "A"));
            tree.insert(new TreeNode<>(10, "B"));
            tree.insert(new TreeNode<>(15, "E"));
            tree.insert(new TreeNode<>(30, "C"));
            tree.insert(new TreeNode<>(5, "D"));

            int depth = tree.depthOfMin();
            boolean ok = depth == 2; // 20->10->5
            test("testBalancedTree", ok,
                    "Expected depth=2",
                    "Got depth=" + depth);
        });

        runSafely("testRightLeaningTree", () -> {
            MyAVLTree<String> tree = new MyAVLTree<>();
            tree.insert(new TreeNode<>(10, "A"));
            tree.insert(new TreeNode<>(20, "B"));
            tree.insert(new TreeNode<>(30, "C"));
            tree.insert(new TreeNode<>(40, "D"));

            int depth = tree.depthOfMin();
            boolean ok = depth == 1; // 10
            test("testRightLeaningTree", ok,
                    "Expected depth=0",
                    "Got depth=" + depth);
        });
    }

    // --- MyDynamicSet tests ---
    private static void testMyDynamicSet() {
        System.out.println("\n===== MyDynamicSet Tests =====");
        runSafely("testEmptySet", () -> {
            MyDynamicSet<String> set = new MyDynamicSet<>(10);
            test("search empty", set.search(5) == null,
                    "Expected null search", "Got " + set.search(5));
            test("min empty", set.minimum() == null,
                    "Expected null minimum", "Got " + set.minimum());
            test("max empty", set.maximum() == null,
                    "Expected null maximum", "Got " + set.maximum());
        });
        runSafely("testInsertAndSearch", () -> {
            MyDynamicSet<String> set = new MyDynamicSet<>(10);
            Element<String> a = new Element<>(5, "A"), b = new Element<>(3, "B"), c = new Element<>(7, "C");
            set.insert(a);
            set.insert(b);
            set.insert(c);
            test("search 5", set.search(5) != null && set.search(5).key() == 5,
                    "Expected key=5", "Got " + (set.search(5) != null ? set.search(5).key() : "null"));
            test("search 3", set.search(3) != null && set.search(3).key() == 3,
                    "Expected key=3", "Got " + (set.search(3) != null ? set.search(3).key() : "null"));
            test("search 7", set.search(7) != null && set.search(7).key() == 7,
                    "Expected key=7", "Got " + (set.search(7) != null ? set.search(7).key() : "null"));
            test("search missing", set.search(10) == null,
                    "Expected null search", "Got " + set.search(10));
        });
        runSafely("testDelete", () -> {
            MyDynamicSet<String> set = new MyDynamicSet<>(10);
            Element<String> a = new Element<>(5, "A"), b = new Element<>(3, "B");
            set.insert(a);
            set.insert(b);
            ListLink<String> linkA = (ListLink<String>) set.search(5);
            ListLink<String> linkB = (ListLink<String>) set.search(3);
            set.delete(linkA);
            test("deleted search=5", set.search(5) == null,
                    "Expected null", "Got " + set.search(5));
            test("min after del", set.minimum() == linkB,
                    "Expected min key=3", "Got " + (set.minimum() != null ? set.minimum().key() : "null"));
            test("max after del", set.maximum() == linkB,
                    "Expected max key=3", "Got " + (set.maximum() != null ? set.maximum().key() : "null"));
        });
        runSafely("testMinMax", () -> {
            MyDynamicSet<String> set = new MyDynamicSet<>(10);
            Element<String> a = new Element<>(5, "A"), b = new Element<>(2, "B"), c = new Element<>(8, "C");
            set.insert(a);
            set.insert(b);
            set.insert(c);
            ListLink<String> linkB = (ListLink<String>) set.search(2);
            ListLink<String> linkC = (ListLink<String>) set.search(8);
            test("min element", set.minimum() == linkB,
                    "Expected min key=2", "Got " + (set.minimum() != null ? set.minimum().key() : "null"));
            test("max element", set.maximum() == linkC,
                    "Expected max key=8", "Got " + (set.maximum() != null ? set.maximum().key() : "null"));
        });
        runSafely("testSuccessorPredecessor", () -> {
            MyDynamicSet<String> set = new MyDynamicSet<>(10);
            Element<String> a = new Element<>(1, "A"), b = new Element<>(3, "B"), c = new Element<>(5, "C");
            set.insert(a);
            set.insert(b);
            set.insert(c);
            ListLink<String> linkA = (ListLink<String>) set.search(1);
            ListLink<String> linkB = (ListLink<String>) set.search(3);
            ListLink<String> linkC = (ListLink<String>) set.search(5);
            test("succ of 1", set.successor(linkA) == linkB,
                    "Expected succ key=3",
                    "Got " + (set.successor(linkA) != null ? set.successor(linkA).key() : "null"));
            test("succ of 3", set.successor(linkB) == linkC,
                    "Expected succ key=5",
                    "Got " + (set.successor(linkB) != null ? set.successor(linkB).key() : "null"));
            test("succ null", set.successor(linkC) == null,
                    "Expected null succ", "Got " + set.successor(linkC));
            test("pred of 3", set.predecessor(linkB) == linkA,
                    "Expected pred key=1",
                    "Got " + (set.predecessor(linkB) != null ? set.predecessor(linkB).key() : "null"));
            test("pred of 5", set.predecessor(linkC) == linkB,
                    "Expected pred key=3",
                    "Got " + (set.predecessor(linkC) != null ? set.predecessor(linkC).key() : "null"));
            test("pred null", set.predecessor(linkA) == null,
                    "Expected null pred", "Got " + set.predecessor(linkA));
        });
    }

    // --- MyFirstDataStructure tests ---
    private static void testMyFirstDataStructure() {
        System.out.println("\n===== MyFirstDataStructure Tests =====");

        runSafely("testInsertAndFirstLast", () -> {
            MyFirstDataStructure<String> ds = new MyFirstDataStructure<>(10);
            ds.insert(new Element<>(5, "A"));
            ds.insert(new Element<>(10, "B"));
            ds.insert(new Element<>(1, "C"));

            boolean ok1 = ds.first() != null && ds.first().key() == 5;
            test("testInsertAndFirstLast - first", ok1,
                    "Expected first=5", "Got first=" + (ds.first() != null ? ds.first().key() : "null"));

            boolean ok2 = ds.last() != null && ds.last().key() == 1;
            test("testInsertAndFirstLast - last", ok2,
                    "Expected last=1", "Got last=" + (ds.last() != null ? ds.last().key() : "null"));
        });

        runSafely("testFindAndRemove", () -> {
            MyFirstDataStructure<String> ds = new MyFirstDataStructure<>(10);
            ds.insert(new Element<>(5, "A"));
            ds.insert(new Element<>(10, "B"));
            ds.insert(new Element<>(1, "C"));

            ds.findAndRemove(10);
            boolean ok = ds.last() == null || ds.last().key() != 10;
            test("testFindAndRemove", ok,
                    "Deleted element should not be present",
                    "Got last=" + (ds.last() != null ? ds.last().key() : "null"));
        });

        runSafely("testMaximum", () -> {
            MyFirstDataStructure<String> ds = new MyFirstDataStructure<>(10);
            ds.insert(new Element<>(5, "A"));
            ds.insert(new Element<>(10, "B"));
            ds.insert(new Element<>(1, "C"));

            Element<String> max = ds.maximum();
            boolean ok1 = max != null && max.key() == 10;
            test("testMaximum", ok1,
                    "Expected max=10", "Got max=" + (max != null ? max.key() : "null"));

            ds.findAndRemove(10);
            Element<String> max2 = ds.maximum();
            boolean ok2 = max2 != null && max2.key() == 5;
            test("testMaximum after removal", ok2,
                    "Expected max after removal=5", "Got max=" + (max2 != null ? max2.key() : "null"));
        });
    }

    // --- MySecondDataStructure tests ---
    private static void testMySecondDataStructure() {
        System.out.println("\n===== MySecondDataStructure Tests =====");

        runSafely("testEmptyStats", () -> {
            MySecondDataStructure ds = new MySecondDataStructure(10);
            test("median empty", ds.medianQuality() == -1,
                    "Expected median=-1", "Got median=" + ds.medianQuality());
            test("avg empty", ds.avgQuality() == -1,
                    "Expected avg=-1", "Got avg=" + ds.avgQuality());
            test("mostExpensive empty", ds.mostExpensive() == null,
                    "Expected null mostExpensive", "Got " + ds.mostExpensive());
        });

        runSafely("testInsertMedianAvg", () -> {
            MySecondDataStructure ds = new MySecondDataStructure(10);
            ds.insert(new Product(1, 2, 100, "P1"));
            ds.insert(new Product(2, 4, 200, "P2"));
            ds.insert(new Product(3, 1, 50, "P3"));

            int expectedMedian = 2; // qualities: 1,2,4 -> median=2
            test("median quality", ds.medianQuality() == expectedMedian,
                    "Expected median=" + expectedMedian, "Got median=" + ds.medianQuality());

            double expectedAvg = (2 + 4 + 1) / 3.0;
            test("avg quality", Math.abs(ds.avgQuality() - expectedAvg) < 1e-6,
                    "Expected avg=" + expectedAvg, "Got avg=" + ds.avgQuality());
        });

//        runSafely("testRaisePriceAndOffset", () -> {
//            MySecondDataStructure ds = new MySecondDataStructure(10);
//            ds.insert(new Product(1, 3, 100, "P"));
//            ds.raisePrice(50, 3);
//            test("offset for quality 3", ds.getOffset(3) == 50,
//                    "Expected offset=50", "Got offset=" + ds.getOffset(3));
//        });

        runSafely("testMostExpensive", () -> {
            MySecondDataStructure ds = new MySecondDataStructure(10);
            ds.insert(new Product(1, 1, 100, "A"));
            ds.insert(new Product(2, 2, 150, "B"));
            test("mostExpensive id=2", ds.mostExpensive().id() == 2,
                    "Expected id=2", "Got id=" + ds.mostExpensive().id());
        });

        runSafely("testFindAndRemoveSecond", () -> {
            MySecondDataStructure ds = new MySecondDataStructure(10);
            ds.insert(new Product(1, 1, 100, "A"));
            ds.insert(new Product(2, 2, 150, "B"));
            ds.findAndRemove(2);
            test("mostExpensive after remove=1", ds.mostExpensive().id() == 1,
                    "Expected id=1", "Got id=" + ds.mostExpensive().id());
        });
    }

    // --- Stress Tests ---
    private static void stressTestMyArray() {
        System.out.println("\n===== Stress Test MyArray =====");
        MyArray<Integer> arr = new MyArray<>(STRESS_SIZE);
        for (int i = 0; i < STRESS_SIZE; i++)
            arr.insert(new ArrayElement<>(i, i));
        arr.reverse();
        boolean ok = arr.size() == STRESS_SIZE
                && arr.get(0).key() == STRESS_SIZE - 1
                && arr.get(STRESS_SIZE - 1).key() == 0;
        test("stressMyArray", ok,
                "Expected first=" + (STRESS_SIZE - 1) + ", last=0",
                "Got first=" + arr.get(0).key() + ", last=" + arr.get(STRESS_SIZE - 1).key());
    }

    private static void stressTestMyLinkedList() {
        System.out.println("\n===== Stress Test MyLinkedList =====");
        MyLinkedList<Integer> list = new MyLinkedList<>();
        for (int i = 0; i < STRESS_SIZE; i++)
            list.insert(new ListLink<>(i, i));
        list.reverse();
        boolean ok = list.head() != null && list.tail() != null
                && list.head().key() == 0
                && list.tail().key() == STRESS_SIZE - 1;
        test("stressMyLinkedList", ok,
                "Expected head=0, tail=" + (STRESS_SIZE - 1),
                "Got head=" + list.head().key() + ", tail=" + list.tail().key());
    }

    private static void stressTestMyAVLTree() {
        System.out.println("\n===== Stress Test MyAVLTree =====");
        MyAVLTree<Integer> tree = new MyAVLTree<>();
        for (int i = 0; i < STRESS_SIZE; i++)
            tree.insert(new TreeNode<>(i, i));
        int depth = tree.depthOfMin();
        boolean ok = depth >= 0;
        test("stressMyAVLTree", ok,
                "Got depth=" + depth);
    }

    private static void stressTestMyDynamicSet() {
        System.out.println("\n===== Stress Test MyDynamicSet =====");
        MyDynamicSet<Integer> set = new MyDynamicSet<>(STRESS_SIZE);
        for (int i = 0; i < STRESS_SIZE; i++)
            set.insert(new Element<>(i, i));
        boolean ok = true;
        for (int i = 0; i < 100; i++) {
            int k = (int) (Math.random() * STRESS_SIZE);
            if (set.search(k) == null) {
                ok = false;
                break;
            }
        }
        test("stressMyDynamicSet", ok,
                "Random search should succeed");
    }

    private static void stressTestMyFirstDataStructure() {
        System.out.println("\n===== Stress Test MyFirstDataStructure =====");
        MyFirstDataStructure<Integer> ds = new MyFirstDataStructure<>(STRESS_SIZE);
        for (int i = 0; i < STRESS_SIZE; i++)
            ds.insert(new Element<>(i, i));
        boolean ok = ds.first() != null && ds.first().key() == 0
                && ds.last() != null && ds.last().key() == STRESS_SIZE - 1
                && ds.maximum() != null && ds.maximum().key() == STRESS_SIZE - 1;
        test("stressMyFirstDataStructure", ok,
                "Expected first=0,last,max=" + (STRESS_SIZE - 1),
                "Got first=" + (ds.first() != null ? ds.first().key() : "null") +
                        ", last=" + (ds.last() != null ? ds.last().key() : "null") +
                        ", max=" + (ds.maximum() != null ? ds.maximum().key() : "null"));
    }

    private static void stressTestMySecondDataStructure() {
        System.out.println("\n===== Stress Test MySecondDataStructure =====");
        MySecondDataStructure ds = new MySecondDataStructure(STRESS_SIZE);
        for (int i = 0; i < STRESS_SIZE; i++) {
            int q = i % 6;
            ds.insert(new Product(i, q, 100 + i, "P" + i));
        }
        int expectedMedian = 2; // qualities: 0,1,2,3,4,5 -> median=2
        int median = ds.medianQuality();
        double avg = ds.avgQuality();
        boolean ok = median >= 0 && avg >= 0;
        test("stressMySecondDataStructure", ok && median == expectedMedian,
                "Got median=" + median + ", avg=" + avg);
    }

}
