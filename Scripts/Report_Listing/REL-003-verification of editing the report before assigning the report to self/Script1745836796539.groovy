import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper as Helper
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Summary'))

WebUI.waitForPageLoad(10)

List<String> containerXpaths = ['(//div[contains(@class,\'smearReport_morphology-editor\')])[1]//div[contains(@class,\'widget-container\')]' // RBC
    , '(//div[contains(@class,\'smearReport_morphology-editor\')])[2]//div[contains(@class,\'widget-container\')]' // WBC
    , '(//div[contains(@class,\'smearReport_morphology-editor\')])[3]//div[contains(@class,\'widget-container\')]' // Platelet
    , '(//div[contains(@class,\'smearReport_morphology-editor\')])[4]//div[contains(@class,\'widget-container\')]' // Hemoparasite
    , '(//div[contains(@class,\'smearReport_morphology-editor\')])[5]//div[contains(@class,\'widget-container\')]' // Impression
]

List<Integer> bad = []

containerXpaths.eachWithIndex({ def xpath, def idx ->
        TestObject container = new TestObject("container$idx").addProperty('xpath', ConditionType.EQUALS, xpath)

        try {
            WebUI.waitForElementVisible(container, 5)

            String classes = WebUI.getAttribute(container, 'class')
			
			WebUI.comment("Container ${idx+1} classes: '${classes}'")

            // must contain either 'disabled' or 'dx-state-disabled'
            if (!(classes.contains('disabled') || classes.contains('dx-state-disabled'))) {
                bad << (idx + 1)
            }
        }
        catch (Exception e) {
            WebUI.comment("Error locating container ${idx+1}: ${e.message}")

            bad << (idx + 1)
        } 
    })

// 4) Final check
if (bad) {
    assert false
} else {
    WebUI.comment('✅ All editor containers are correctly disabled on Summary tab.')
}

// 5) Teardown
WebUI.closeBrowser()