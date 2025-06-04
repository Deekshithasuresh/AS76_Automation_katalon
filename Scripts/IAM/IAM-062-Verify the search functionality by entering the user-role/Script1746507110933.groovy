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
String searchKeyword = 'reviewer'

String Exp_username = 'operatoruser'

String Exp_role = 'reviewer'

String Exp_Status = 'Active'

verifySearchUserByUsernameAndRole(searchKeyword, Exp_username, Exp_role, Exp_Status //search by roll acesss
    )

//verifySearchUserByUsernameAndRole(RoleBasedSearchKeyword,Exp_username,Exp_role,Exp_Status)
// Replace with the keyword you want to search
// Step 3: Wait for the table to filter (you can adjust wait time or use dynamic wait based on your application's behavior)
//WebUI.waitForElementVisible(findTestObject('Object Repository/YourPage/table'), 10 // Assuming the table is identified in the object repo)
// Step 4: Get all the rows from the table (assuming the table rows are identified by 'tableRows' object in the repository)
//List<WebElement> tableRows = WebUI.findWebElements(findTestObject('Object Repository/YourPage/tableRows'), 10)
//List<WebElement> tableRows = WebUI.findWebElements(findTestObject('Object Repository/View list of users/Page_Admin Console/search bar table row'),
//10)
// Step 5: Loop through the rows to verify if the search keyword is present
// Flag to track if all rows contain the search keyword
// Step 6: Assert the condition that all rows should contain the search keyword
// Step 7: Count the number of rows in the table
WebUI.click(findTestObject('View list of users/Page_Admin Console/search bar'))

WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.BACK_SPACE //row.click()
        ))

WebUI.closeBrowser()

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

            break
        }
    }
    
    assert isKeywordPresent == true : 'Not all rows contain the keyword'

    int rowCount = rows.size()

    println('Total number of rows in the table: ' + rowCount)
}

