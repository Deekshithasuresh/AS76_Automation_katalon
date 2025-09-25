import com.kms.katalon.core.annotation.*
import com.kms.katalon.core.context.TestCaseContext
import testmo.Reporter

class TestmoListener {

    @AfterTestCase
    def afterTestCase(TestCaseContext testCaseContext) {
        // Get Testmo case id (from variable or tag)
        Map variables = testCaseContext.getTestCaseVariables()
    def caseId = variables.get("TESTMO_CASE_ID")   // safely fetch
    println "CaseId = $caseId"

        if (caseId) {
            String status
            switch (testCaseContext.getTestCaseStatus()) {
                case "PASSED":
                    status = "passed"
                    break
                case "FAILED":
                    status = "failed"
                    break
                default:
                    status = "skipped"
            }

            String comment = testCaseContext.getMessage() ?: ""
            Reporter.updateResult(caseId, status, comment)
        }
    }
}
