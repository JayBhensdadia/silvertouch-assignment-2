import java.util.ArrayList;
import java.util.List;

class TestCase {
    private String testCaseName;

    public TestCase(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public void runTest() {
        System.out.println("Executing Test: " + testCaseName);

        boolean testPassed = Math.random() < 0.785; 

        if (testPassed) {
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed!");
        }
    }
}

class TestSuite {
    private List<TestCase> testCases;

    public TestSuite() {
        this.testCases = new ArrayList<>();
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }

    public void runTestSuite() {
        System.out.println("Running Test Suite...");
        for (TestCase testCase : testCases) {
            testCase.runTest();
        }
        System.out.println("Test Suite Execution Completed.");
    }
}

class Main {
    public static void main(String[] args) {
        TestCase test_login = new TestCase("Login Test");
        TestCase test_registration = new TestCase("Registration Test");
        TestCase test_check = new TestCase("Checkout Test");

        TestSuite testSuite = new TestSuite();

        testSuite.addTestCase(test_login);
        testSuite.addTestCase(test_registration);
        testSuite.addTestCase(test_check);

        // Run the test suite
        testSuite.runTestSuite();
    }
}
