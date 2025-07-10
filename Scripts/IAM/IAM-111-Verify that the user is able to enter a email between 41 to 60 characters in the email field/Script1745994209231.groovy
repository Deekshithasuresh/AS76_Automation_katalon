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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.util.List as List
import com.kms.katalon.core.testobject.ConditionType as ConditionType

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Password_loginPassword'), 
    'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/button_Users'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Manage_user/Page_Admin Console/span_Platelet level limits_material-icons n_ecbb8f'))

WebUI.verifyElementText(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Platelet level limits_MuiInputBase-in_8eb492'), 
    '')

// Step 2: Enter the keyword in the search field
String searchKeyword = 'manju'

String Exp_username = 'manju'

String Exp_role = 'administrator'

String Exp_Status = 'Active'


verifySearchUserByUsernameAndRole(searchKeyword, Exp_username, Exp_role, Exp_Status //search by roll acesss
    )

WebUI.verifyElementNotChecked(findTestObject('Object Repository/Manage_user/Page_Admin Console/update-button-disabled'), 
    0)

WebUI.click(findTestObject('Manage_user/Page_Admin Console/input_Email_rbc-input-box'))

WebUI.sendKeys(findTestObject('Manage_user/Page_Admin Console/input_Email_rbc-input-box'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'), 'johnsmith_performance_qa_engineer_script_runner@example.com')

String email_id_char_count = WebUI.getAttribute(findTestObject('IAM Model/Page_Admin Console/input_Email_rbc-input-box'), 
    'value')

void verifySearchUserByUsernameAndRole(String keyword, String username, String role, String status) {
    WebUI.setText(findTestObject('View list of users/Page_Admin Console/search bar'), keyword)

    WebDriver driver = DriverFactory.getWebDriver()

    List<WebElement> rows = driver.findElements(By.xpath('//table//tbody//tr'))

    boolean isKeywordPresent = true

    for (WebElement row : rows) {
        List<WebElement> col = row.findElements(By.tagName('td'))

        String Act_name = (col[0]).getText()

        String Act_username = (col[1]).getText()

        String Act_role = (col[2]).getText()

        String Act_status = (col[3]).getText()

        if (((Act_name.contains(keyword) && Act_username.equals(username)) && Act_role.equals(role)) && Act_status.equals(
            status)) {
            isKeywordPresent = true

            println('name, username and role has been found and verified')

            row.click()

            break
        }
    }
    
    assert isKeywordPresent == true : 'Not all rows contain the keyword'

    int rowCount = rows.size()

    println('Total number of rows in the table: ' + rowCount)
}

