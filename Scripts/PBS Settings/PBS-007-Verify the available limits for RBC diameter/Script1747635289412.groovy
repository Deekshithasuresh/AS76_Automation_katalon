import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import loginPackage.Login as Login
import adimin_pbs_Settings.PBS_Settings
import com.kms.katalon.core.testobject.ConditionType

//Login as Admin
Login lg = new Login()

PBS_Settings pbs_set = new PBS_Settings()

lg.AdminLogin('prem', 'prem@2807')


//Navigate to PBS Settings
WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_PBS settings'), 10)
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_PBS settings'))

//Access RBC Diameter Limits Section
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_RBC diameter limits'))

//Verify Footnote
WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/FootNoteWarningText'), 10)
WebUI.verifyElementText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/FootNoteWarningText'), 
    'The default values are set according to Palmer L, Briggs C, McFadden S, Zini G, Burthem J, Rozenberg G, Proytcheva M, Machin SJ. ICSH recommendations for the standardization of nomenclature and grading of peripheral blood cell morphological features. Int J Lab Hematol. 2015 Jun;37(3):287-303.')

// Click Edit settings
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Edit settings'))

// Verify Normal Label and its min/max inputs
TestObject normalLabel = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//span[normalize-space(text())='Normal']")
TestObject normalMinInput = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//span[normalize-space(text())='Normal']/following::input[@type='number'][1]")
TestObject normalMaxInput = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//span[normalize-space(text())='Normal']/following::input[@type='number'][2]")

WebUI.verifyElementPresent(normalLabel, 10)
WebUI.verifyElementPresent(normalMinInput, 10)
WebUI.verifyElementPresent(normalMaxInput, 10)

// Optional: Verify expected values if needed
WebUI.verifyElementAttributeValue(normalMinInput, "value", "4", 5)
WebUI.verifyElementAttributeValue(normalMaxInput, "value", "6", 5)

