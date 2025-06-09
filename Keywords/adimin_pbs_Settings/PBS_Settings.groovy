package adimin_pbs_Settings

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import com.kms.katalon.core.webui.driver.WebUIDriverType
//import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.chrome.ChromeDriver
//import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.common.WebUiCommonHelper
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.JavascriptExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration
import com.kms.katalon.core.util.KeywordUtil
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.regex.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys
import org.openqa.selenium.*
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.testng.asserts.SoftAssert;


public class PBS_Settings {
	SoftAssert softAssert = new SoftAssert();
	public void verifyUIElements(String tabName) {
		WebDriver driver =DriverFactory.getWebDriver()
		ArrayList<String> RBC_Size_cellname_List=new ArrayList<>(Arrays.asList("Anisocytosis","Macrocytes","Microcytes"))
		ArrayList<String> RBC_Shape_cellname_List=new ArrayList<>(Arrays.asList("Acanthocytes","Echinocytes","Elliptocytes","Fragmented Cells","Ovalocytes","Poikilocytosis","Sickle Cells","Target Cells","Teardrop Cells"))
		ArrayList<String> RBC_Color_cellname_List=new ArrayList<>(Arrays.asList("Hypochromic Cells","Polychromatic Cells"))

		WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/FootNoteWarningText'), 10)
		WebUI.verifyElementText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/FootNoteWarningText'), 'The default values are set according to Palmer L, Briggs C, McFadden S, Zini G, Burthem J, Rozenberg G, Proytcheva M, Machin SJ. ICSH recommendations for the standardization of nomenclature and grading of peripheral blood cell morphological features. Int J Lab Hematol. 2015 Jun;37(3):287-303.')

		//Table headers verification
		List<WebElement> header_row_col = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Table_header_row'), 10)

		int size = header_row_col.size()
		WebUI.comment("Header column size: " + size)

		// Ensure we have enough elements before accessing indexes
		if (size >= 5) {
			String cellname = header_row_col[0]?.getText()
			WebUI.comment("Cell Name: " + cellname)

			String grade0Text = header_row_col[1]?.getText()
			WebUI.comment("Grade 0: " + grade0Text)

			String grade1Text = header_row_col[2]?.getText()
			WebUI.comment("Grade 1: " + grade1Text)

			String grade2Text = header_row_col[3]?.getText()
			WebUI.comment("Grade 2: " + grade2Text)

			WebUI.delay(2)

			String grade3Text = header_row_col[4]?.getText()
			WebUI.comment("Grade 3: " + grade3Text)

			assert cellname == 'CellName'
			assert grade0Text == 'Grade 0'
			assert grade1Text == 'Grade 1'
			assert grade2Text == 'Grade 2'
			assert grade3Text == 'Grade 3'
		} else {
			WebUI.comment("❌ Not enough columns found in the header row. Expected 6, but found: " + size)
			WebUI.verifyEqual(size >= 5, true) // This will fail the test if less than 6
		}

		//Table content verification--cellname
		List<WebElement> rows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Table_content_row'), 10)
		for(int i=0; i<rows.size();i++) {
			String Actual_cellname=rows.get(i).findElement(By.xpath(".//td[1]")).getText()
			if(tabName.equals("Size")) {
				WebUI.comment(Actual_cellname)
				assert Actual_cellname==RBC_Size_cellname_List[i]
			}
			else if(tabName.equals("Shape")) {
				WebUI.comment(Actual_cellname)
				assert Actual_cellname==RBC_Shape_cellname_List[i]
			}
			else {
				WebUI.comment(Actual_cellname)
				assert Actual_cellname==RBC_Color_cellname_List[i]
			}
		}
	}
	public void checkTheValuePresentInEachGrade() {
		List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Cell_rows_In_Edit_page'), 10)

		String upperLimitGrade1 = "";
		String lowerLimitGrade2 = "";
		String upperLimitGrade2 = ""

		for(WebElement row:cell_rows) {
			List<WebElement> columns =row.findElements(By.xpath(".//td"))
			String cellname=columns[0].getText()
			String grade_3_value=columns[4].getText().replace(">", "").trim()
			println(cellname)
			println("Grade 3 value "+grade_3_value)

			List<WebElement> row_grade_value =row.findElements(By.xpath(".//div[@class='table-column']"))
			for(int i=0;i<2;i++) {
				WebElement LowerLimitEle =row_grade_value.get(i).findElement(By.xpath(".//div[@class='value-container']"))
				String lowerLimitValue =LowerLimitEle.getText().replace("-", "").trim()
				println("LowerLimit for grade "+(i+1)+" is "+ lowerLimitValue)
				boolean lower_limit_isEditable = LowerLimitEle.isEnabled()
				String readOnlyAttrlow = LowerLimitEle.getAttribute("readonly")
				boolean isReadOnly = (readOnlyAttrlow == null || readOnlyAttrlow == "true")
				boolean isNotEditable = lower_limit_isEditable && isReadOnly
				assert isNotEditable==true :'LowerLimit for grade '+(i+1)+' is editable'

				WebElement UpperLimitEle =row_grade_value.get(i)findElement(By.xpath(".//input[@class='rbc-input-box ']"))
				String upperLimitValue=UpperLimitEle.getAttribute("value")
				println("UpperLimit for grade "+(i+1)+" is " +upperLimitValue)
				boolean upper_limit_isEditable = UpperLimitEle.isEnabled()
				String readOnlyAttr = UpperLimitEle.getAttribute("readonly")
				boolean isNotReadOnly = (readOnlyAttr == null || readOnlyAttr == "false")
				boolean isEditable = upper_limit_isEditable && isNotReadOnly
				assert isEditable : "Field is not editable"

				if (i == 0) {
					upperLimitGrade1 = upperLimitValue;
				} else if (i == 1) {
					lowerLimitGrade2 = lowerLimitValue;
					upperLimitGrade2 = upperLimitValue
				}
			}
			assert upperLimitGrade1.equals(lowerLimitGrade2) : "Mismatch: UpperLimit of Grade 1 is not equal to LowerLimit of Grade 2";
			assert grade_3_value.equals(upperLimitGrade2) : "Mismatch: Grade 3 value is not equal to UpperLimit of Grade 2"
		}
	}

	public void enterValueInUpperLimitField(List<String> values) {
		List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Cell_rows_In_Edit_page'), 10)
		for(WebElement row:cell_rows) {
			row.click()
			List<WebElement> row_grade_value =row.findElements(By.xpath(".//div[@class='table-column']"))
			for(int i=0;i<2;i++) {
				WebElement UpperLimitEle =row_grade_value.get(i)findElement(By.xpath(".//input[@class='rbc-input-box ' or @class='rbc-input-box error']"))
				for (String value : values) {
					if(value ==~ /\d+\.\d+/) //if value is decimal
					{
						UpperLimitEle.sendKeys(Keys.chord(Keys.COMMAND, 'a'))
						UpperLimitEle.sendKeys(Keys.chord(Keys.BACK_SPACE))
						UpperLimitEle.sendKeys(value)
						//					int scaled = (value.toBigDecimal() * 10).intValue()// will convert the value=0.5 to 05
						//					String result = String.format("%02d", scaled)
						String result=value.replace(".", "")
						String decimal2=UpperLimitEle.getAttribute('value')
						assert decimal2==result
					}

					else {
						UpperLimitEle.sendKeys(Keys.chord(Keys.COMMAND, 'a'))
						UpperLimitEle.sendKeys(Keys.chord(Keys.BACK_SPACE))
						UpperLimitEle.sendKeys(value)
					}
					WebUI.delay(1)
				}
			}
		}
	}


	public ArrayList<String> getValuesPresentInUpperLimitFields() {
		List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Cell_rows_In_Edit_page'), 10)
		ArrayList<String> grade_values= new ArrayList<>()
		for(WebElement row:cell_rows) {
			//row.click()
			List <WebElement> UpperLimitEles =row.findElements(By.xpath(".//input[@class='rbc-input-box ']"))
			for(int i=0;i<2;i++) {
				grade_values.add(UpperLimitEles.get(i).getAttribute('value'))
			}
		}
		println(grade_values)
		return grade_values
	}

	public void checkThatValuesGotUpdated(ArrayList<String> ActualValues, ArrayList<String> ExpectedValues,String testcase) {
		println( 'Actual values'+ActualValues)
		println('Expected values'+ExpectedValues)

		for(int i=0;i<ActualValues.size();i++) {
			if (testcase.equals('edit')) {
				if(i%2==0) {
					assert ActualValues[i] == ExpectedValues[0]
				}

				else {
					assert ActualValues[i]== ExpectedValues[1]
				}
			}
			if(testcase.equals('reset_to_default') || testcase.equals('cancel') ||testcase.equals('save')) {
				assert ActualValues[i]== ExpectedValues[i]
			}
		}
	}

	public void EneterValueInUpperLimitFields(String Grade1Value, String Grade2Value, String testcase) {
		WebDriver driver =DriverFactory.getWebDriver()

		List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Cell_rows_In_Edit_page'), 10)
		WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_to_Default_CTA'))
		WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_Confirm_CTA'))
		for(WebElement row:cell_rows) {

			WebUI.delay(2)
			List <WebElement> UpperLimitEle =row.findElements(By.xpath(".//input[@class='rbc-input-box ']"))
			UpperLimitEle[0].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
			UpperLimitEle[0].sendKeys(Keys.chord(Keys.BACK_SPACE))
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
			String error_msg=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
			assert error_msg.equals('Values should be between 0-100')
			UpperLimitEle[0].sendKeys(Grade1Value)

			UpperLimitEle[1].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
			UpperLimitEle[1].sendKeys(Keys.chord(Keys.BACK_SPACE))
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
			String error_msg1=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
			assert error_msg1.equals('Values should be between 0-100')
			UpperLimitEle[1].sendKeys(Grade2Value)

			if(testcase.equals('checkErrorMessage')) {
				WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
				String error_msg_text1=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
				assert error_msg_text1.equals('Upper limit of grade 2 cannot be lower than upper limit of grade 1')

				UpperLimitEle[0].sendKeys(Keys.chord(Keys.COMMAND, 'a'))
				UpperLimitEle[0].sendKeys(Keys.chord(Keys.BACK_SPACE))
				WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
				String error_msg2=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
				assert error_msg2.equals('Values should be between 0-100')
				int Grade1Value_2=Integer.parseInt(Grade1Value)
				String Grade1Value_22=String.valueOf(Grade1Value_2-1)
				UpperLimitEle[0].sendKeys(Grade1Value_22)

				WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
				String error_msg_text2=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
				assert error_msg_text2.equals('Upper limit of grade 1 cannot be higher than upper limit of grade 2')
			}


			//6 4
			//25 20
		}
	}

	public void checkFunctionalityOfCTAS(TestObject MainCTA,TestObject SecondaryCTA, String testcase) {
		WebDriver driver = DriverFactory.getWebDriver()

		WebUI.waitForElementVisible(MainCTA, 20)
		WebUI.click(MainCTA)

		WebElement container=WebUI.findWebElement(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Confirmation_pop-up_title_container'), 10)
		WebElement warning_icon=container.findElement(By.xpath(".//img[@alt='warning']"))
		WebElement warning_msg=container.findElement(By.xpath(".//div[@class='title']"))
		String Warning_Title =warning_msg.getText()
		WebElement Description=driver.findElement(By.className("description"))
		String Description_text =Description.getText()
		assert warning_icon.isDisplayed() :"warning icon is not displayed"
		assert warning_msg.isDisplayed() :"Warning title is not displayed"

		if(testcase.equals('save_cancel')) {
			assert Warning_Title.equals('Are you sure you want to modify?')
			assert Description_text.equals('Modifying these settings will impact all future reports. Do you still wish to continue?')
			WebUI.waitForElementVisible(SecondaryCTA, 20)
			WebUI.verifyElementPresent(SecondaryCTA, 10, FailureHandling.OPTIONAL)
			WebUI.click(SecondaryCTA)
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10)
			WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10, FailureHandling.OPTIONAL)
		}

		if(testcase.equals('save_confirm')) {
			assert Warning_Title.equals('Are you sure you want to modify?')
			assert Description_text.equals('Modifying these settings will impact all future reports. Do you still wish to continue?')
			WebUI.waitForElementVisible(SecondaryCTA, 20)
			WebUI.verifyElementPresent(SecondaryCTA, 10, FailureHandling.OPTIONAL)
			WebUI.click(SecondaryCTA)
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Successful_Toast_Msg'), 10)
			WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Successful_Toast_Msg'), 10, FailureHandling.OPTIONAL)
			String ToastMsg_Header=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Toast_Msg_Header'))
			assert ToastMsg_Header.equals('Settings updated')
			String ToastMsg_Desc=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Toast_msg_description'))
			assert ToastMsg_Desc.equals('Updated settings will only apply to new reports. Existing reports will remain unchanged')
		}
		if(testcase.equals('reset_cancel')) {
			assert Warning_Title.equals('Are you sure you want to reset to default?')
			assert Description_text.equals('Resetting to default will impact all future reports. Do you still wish to continue?')
			WebUI.waitForElementVisible(SecondaryCTA, 20)
			WebUI.verifyElementPresent(SecondaryCTA, 10, FailureHandling.OPTIONAL)
			WebUI.click(SecondaryCTA)
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10)
			WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10, FailureHandling.OPTIONAL)
		}
		if(testcase.equals('reset_confirm')) {
			assert Warning_Title.equals('Are you sure you want to reset to default?')
			assert Description_text.equals('Resetting to default will impact all future reports. Do you still wish to continue?')
			WebUI.waitForElementVisible(SecondaryCTA, 20)
			WebUI.verifyElementPresent(SecondaryCTA, 10, FailureHandling.OPTIONAL)
			WebUI.click(SecondaryCTA)
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10)
			WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10, FailureHandling.OPTIONAL)
		}
		if(testcase.equals('Cancel_cancel')) {
			assert Warning_Title.contains('Are you sure you want to cancel ')
			assert Description_text.equals('All unsaved changes will be lost. Do you wish to continue?')
			WebUI.waitForElementVisible(SecondaryCTA, 20)
			WebUI.verifyElementPresent(SecondaryCTA, 10, FailureHandling.OPTIONAL)
			WebUI.click(SecondaryCTA)
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10)
			WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10, FailureHandling.OPTIONAL)
		}
		if(testcase.equals('Cancel_confirm')) {
			assert Warning_Title.contains('Are you sure you want to cancel ')
			assert Description_text.equals('All unsaved changes will be lost. Do you wish to continue?')
			WebUI.waitForElementVisible(SecondaryCTA, 20)
			WebUI.verifyElementPresent(SecondaryCTA, 10, FailureHandling.OPTIONAL)
			WebUI.click(SecondaryCTA)
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10)
			WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_GRADE_LIMITS_HEADING'), 10, FailureHandling.OPTIONAL)
		}
	}


	public void enterValuesIntoMicroMacroCellFields(String MicrocyteValue, String MacrocyteValue,String testcase) {
		WebDriver driver =DriverFactory.getWebDriver()

		//				List<WebElement> cell_input_boxes = WebUiCommonHelper.findWebElements(
		//						findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/RBC_dia_input_boxes'), 10)

		TestObject microcyte_input=findTestObject("Object Repository/PBS_Settings_Objects/Page_Admin Console/Microcyte_dia_input")
		WebElement  microcyte_input_field = WebUiCommonHelper.findWebElement(microcyte_input, 10)
		TestObject macrocyte_input=findTestObject("Object Repository/PBS_Settings_Objects/Page_Admin Console/Macrocyte_dia_input")
		WebElement  macrocyte_input_field = WebUiCommonHelper.findWebElement(macrocyte_input, 10)

		int micro_int_value=Integer.parseInt(MicrocyteValue)
		int macro_int_value=Integer.parseInt(MacrocyteValue)

		assert microcyte_input_field.isDisplayed()
		microcyte_input_field.sendKeys(Keys.chord(Keys.COMMAND, 'a'))
		microcyte_input_field.sendKeys(Keys.chord(Keys.BACK_SPACE))
		WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
		String error_msg=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
		assert error_msg.equals('Values should be between 0-15')

		WebUI.delay(2)
		macrocyte_input_field.sendKeys(Keys.chord(Keys.COMMAND, 'a'))
		macrocyte_input_field.sendKeys(Keys.chord(Keys.BACK_SPACE))
		WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
		String error_msg1=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
		assert error_msg1.equals('Values should be between 0-15')

		if(testcase.equals("microcyte_higher")) {
			macrocyte_input_field.sendKeys(MacrocyteValue)
			microcyte_input_field.sendKeys(MicrocyteValue)
		}
		if(testcase.equals("macrocyte_higher")) {
			microcyte_input_field.sendKeys(MicrocyteValue)
			macrocyte_input_field.sendKeys(MacrocyteValue)
		}


		if((micro_int_value>macro_int_value) && testcase.equals("microcyte_higher")) {

			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
			String error_msg2=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
			WebElement  save_btn= WebUiCommonHelper.findWebElement(findTestObject("Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_CTA"), 10)
			assert save_btn.isEnabled()== false
			assert error_msg2.equals('Diameter limit for microcytes cannot be higher than diameter limit for macrocytes')
		}

		if((micro_int_value>macro_int_value) && testcase.equals("macrocyte_higher")) {
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
			String error_msg3=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
			WebElement  save_btn= WebUiCommonHelper.findWebElement(findTestObject("Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_CTA"), 10)
			assert save_btn.isEnabled()== false
			assert error_msg3.equals('Diameter limit for macrocytes cannot be lower than diameter limit for microcytes')
		}

		if(micro_int_value==macro_int_value) {
			WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)
			String error_msg4=WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))
			WebElement  save_btn= WebUiCommonHelper.findWebElement(findTestObject("Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_CTA"), 10)
			assert save_btn.isEnabled()== false
			assert error_msg4.equals('Diameter limit of macrocyte and microcyte cannot be same')
		}


		if(micro_int_value<macro_int_value) {
			WebElement  save_btn= WebUiCommonHelper.findWebElement(findTestObject("Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_CTA"), 10)
			assert save_btn.isEnabled()== true
		}
	}

	public void enterValueIntoPlateLetLevelField(WebElement field,String Value) {
		WebDriver driver =DriverFactory.getWebDriver()

		//		int micro_int_value=Integer.parseInt(MicrocyteValue)
		//		int macro_int_value=Integer.parseInt(MacrocyteValue)

		assert field.isDisplayed()==true
		field.sendKeys(Keys.chord(Keys.COMMAND, 'a'))
		field.sendKeys(Keys.chord(Keys.BACK_SPACE))
		field.sendKeys(Value)
	}

	public ArrayList<String> getValuesPresentInPlateletLevelFields() {
		List<WebElement> input_fields = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Platelet_level_input_boxes'), 10)
		ArrayList<Integer> level_values= new ArrayList<>()
		for(WebElement input:input_fields) {
			level_values.add(input.getAttribute('value'))
		}
		println(level_values)
		return level_values
	}

	//	public ArrayList<String> getValuesPresentInRBFields() {
	//		List<WebElement> input_fields = WebUiCommonHelper.findWebElements(
	//				findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Platelet_level_input_boxes'), 10)
	//		ArrayList<Integer> level_values= new ArrayList<>()
	//		for(WebElement input:input_fields) {
	//			level_values.add(input.getAttribute('value'))
	//		}
	//		println(level_values)
	//		return level_values
	//	}

	public boolean verifyChangesGettingRecordedInHistory(String TypeOfChange, String desc,String Original_value,String Modified_value) {
		boolean flag=false;
		List<WebElement> history_rows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/History-rows'), 10)
		for(WebElement row:history_rows) {
			String Type_of_change_in_history=row.findElement(By.xpath("./div[1]/div[1]")).getText()
			String Time_and_Date_in_history=row.findElement(By.xpath("./div[1]/div[2]")).getText()
			String desc_of_change_in_history=row.findElement(By.xpath("./div[2]")).getText()
			String original_value_in_History=row.findElement(By.xpath("./div[3]/span[1]")).getText()
			String modified_value_in_History=row.findElement(By.xpath("./div[3]/span[2]")).getText()
			println(Type_of_change_in_history)
			println(desc_of_change_in_history)
			println("Original_value: "+original_value_in_History+" Modified_value: "+modified_value_in_History)
			if(Type_of_change_in_history.equals(TypeOfChange) && original_value_in_History.equals(Original_value) && modified_value_in_History.equals(Modified_value)) {
				softAssert.assertEquals(Type_of_change_in_history, TypeOfChange, " Mismatch!! in type of change");
				softAssert.assertEquals(desc_of_change_in_history, desc, " Mismatch!! in desc");
				softAssert.assertEquals(original_value_in_History, Original_value, " Mismatch!! in origianl ");
				softAssert.assertEquals(modified_value_in_History, Modified_value, " Mismatch!! in modified");
				softAssert.assertAll();
				flag=true;
				break;
			}
			else {
				flag=false;
				continue
			}

			//softAssert.assertTrue(condition, "Condition failed!");

			System.out.println("Continues even if assertions fail");

			//			assert	Type_of_change_in_history.equals(TypeOfChange)
			//			assert	desc_of_change_in_history.equals(desc)
			//			assert	original_value_in_History==Original_value
			//			assert	modified_value_in_History==Modified_value
		}
		return flag
	}
}


