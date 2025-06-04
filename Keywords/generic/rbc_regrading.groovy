package generic

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


public class rbc_regrading 

{
	public void verifyRegrade() {
		List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
				findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),1)
		for (WebElement row : cellRows) {
			//Get the percentage element (last div inside the row)
			row.click()
			String cellname=row.findElement(By.xpath(".//div")).getText()
			String cellname_removed=cellname.replace("*", "")
			println(cellname)
			//Getting the color of an significant/non-significant dot
			//			WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
			//			String dotColor = dot.getCssValue("background-color");
			//			System.out.println("Dot color: " + dotColor);
			List<WebElement> grades= row.findElements(By.xpath(".//input[@type='radio']"))
			println(grades.size())
			for (WebElement option : grades) {
				if (option.isSelected()) {
					System.out.println("Selected option value: " + option.getAttribute("value"));
					break;
				}
			}
			if(cellname.equals('Acanthocytes*') || cellname.equals('Sickle Cells*') || cellname.equals('Stomatocytes*') || cellname.equals('Howell-Jolly Bodies*') || cellname.equals('Pappenheimer Bodies*') || cellname.equals('Basophilic Stippling*')) {
				for(int i=0; i<grades.size();i++) {
					grades.get(i).click()
					TestObject toastMsg = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_Cells_regraded_toast_msg_ele')
					WebUI.waitForElementVisible(toastMsg, 10)
					TestObject regradeMsgObj = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_from_to_msg')
					WebUI.waitForElementVisible(regradeMsgObj, 10)
					String regrade_msg = WebUI.getText(regradeMsgObj).trim()
					TestObject significant_non_sign_Msg_ele = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_significant_non-sig_status')
					WebUI.waitForElementVisible(significant_non_sign_Msg_ele, 10)
					String signifiacnt_status_msg = WebUI.getText(significant_non_sign_Msg_ele).trim()
					println("Regrade message: " + regrade_msg)
					println("Signifcant status message: " + signifiacnt_status_msg)
					if(i==0) {
						assert regrade_msg == (cellname_removed + ' regraded from - to ' + i)
						if(i==0 || i==1) {
							assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
							//Getting the color of an non-significant dot
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
							String dotColor = dot.getCssValue("background-color");
							System.out.println("Dot color: " + dotColor);
							assert dotColor=="rgba(50, 152, 93, 1)"//this green-color-code is as per the requirement
						}
						else {
							assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
							//Getting the color of an significant dot
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
							String dotColor = dot.getCssValue("background-color");
							System.out.println("Dot color: " + dotColor);
							assert dotColor=="rgba(198, 27, 28, 1)"//this red-color-code is as per the requirement
						}
						WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/toast_msg_close_icon'))
						println('closed a toast msg')
					}
					else {
						assert regrade_msg == (cellname_removed + ' regraded from ' + (i - 1) + ' to ' + i)
						if(i==0 || i==1) {
							assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
							//Getting the color of an non-significant dot
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
							String dotColor = dot.getCssValue("background-color");
							System.out.println("Dot color: " + dotColor);
							assert dotColor=="rgba(50, 152, 93, 1)"
						}
						else {
							assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
							//Getting the color of an significant dot
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
							String dotColor = dot.getCssValue("background-color");
							System.out.println("Dot color: " + dotColor);
							assert dotColor=="rgba(198, 27, 28, 1)"
						}
						WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/toast_msg_close_icon'))
						println('closed a toast msg')
					}
					//Assertion for significant and non-significant
				}
				//reserve loop to regrade from 3 to 0
				for(int i=(grades.size()-2); i>=0;i--) {
					grades.get(i).click()
					TestObject toastMsg = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_Cells_regraded_toast_msg_ele')
					WebUI.waitForElementVisible(toastMsg, 10)
					TestObject regradeMsgObj = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_from_to_msg')
					WebUI.waitForElementVisible(regradeMsgObj, 10)
					String regrade_msg = WebUI.getText(regradeMsgObj).trim()
					TestObject significant_non_sign_Msg_ele = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_significant_non-sig_status')
					WebUI.waitForElementVisible(significant_non_sign_Msg_ele, 10)
					String signifiacnt_status_msg = WebUI.getText(significant_non_sign_Msg_ele).trim()
					println("Regrade message: " + regrade_msg)
					println("Signifcant status message: " + signifiacnt_status_msg)
					if(i==0) {
						assert regrade_msg == (cellname_removed + ' regraded from '+(i+1) +' to -')
						if(i==0 || i==1) {
							assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
							//Getting the color of an non-significant dot
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
							String dotColor = dot.getCssValue("background-color");
							System.out.println("Dot color: " + dotColor);
							assert dotColor=="rgba(50, 152, 93, 1)"
						}
						else {
							assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
							//Getting the color of an significant dot
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
							String dotColor = dot.getCssValue("background-color");
							System.out.println("Dot color: " + dotColor);
							assert dotColor=="rgba(198, 27, 28, 1)"
						}
						WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/toast_msg_close_icon'))
						println('closed a toast msg')
					}
					else {
						assert regrade_msg == (cellname_removed + ' regraded from ' + (i+1) + ' to ' + i)
						if(i==0 || i==1) {
							assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
							//Getting the color of an non-significant dot
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
							String dotColor = dot.getCssValue("background-color");
							System.out.println("Dot color: " + dotColor);
							assert dotColor=="rgba(50, 152, 93, 1)"
						}
						else {
							assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
							//Getting the color of an significant dot
							WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
							String dotColor = dot.getCssValue("background-color");
							System.out.println("Dot color: " + dotColor);
							assert dotColor=="rgba(198, 27, 28, 1)"
						}
						WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/toast_msg_close_icon'))
						println('closed a toast msg')
					}
				}
			}
			else {
				boolean grade_selection_status=grades[0].isSelected() //checking is 0 grade radio button selected
				if(!grade_selection_status) {
					grades[0].click()
				}
				for(int i=1; i<grades.size();i++) {
					grades.get(i).click()
					WebUI.delay(1)
					TestObject toastMsg = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_Cells_regraded_toast_msg_ele')
					WebUI.waitForElementVisible(toastMsg, 10)
					TestObject regradeMsgObj = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_from_to_msg')
					WebUI.waitForElementVisible(regradeMsgObj, 10)
					String regrade_msg = WebUI.getText(regradeMsgObj).trim()
					TestObject significant_non_sign_Msg_ele = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_significant_non-sig_status')
					WebUI.waitForElementVisible(significant_non_sign_Msg_ele, 10)
					String signifiacnt_status_msg = WebUI.getText(significant_non_sign_Msg_ele).trim()
					println("Regrade message: " + regrade_msg)
					println("Signifcant status message: " + signifiacnt_status_msg)
					assert regrade_msg == (cellname + ' regraded from ' + (i - 1) + ' to ' + i)
					if(i==0 || i==1) {
						assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
						//Getting the color of an non-significant dot
						WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
						String dotColor = dot.getCssValue("background-color");
						System.out.println("Dot color: " + dotColor);
						assert dotColor=="rgba(50, 152, 93, 1)"
					}
					else {
						assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
						//Getting the color of an significant dot
						WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
						String dotColor = dot.getCssValue("background-color");
						System.out.println("Dot color: " + dotColor);
						assert dotColor=="rgba(198, 27, 28, 1)"
					}
					WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/toast_msg_close_icon'))
					println('closed a toast msg')
				}
				//reserve loop to regrade from 3 to 0
				for(int i=(grades.size()-2); i>=0;i--) {
					grades.get(i).click()
					WebUI.delay(1)
					TestObject toastMsg = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_Cells_regraded_toast_msg_ele')
					WebUI.waitForElementVisible(toastMsg, 10)
					TestObject regradeMsgObj = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_from_to_msg')
					WebUI.waitForElementVisible(regradeMsgObj, 10)
					String regrade_msg = WebUI.getText(regradeMsgObj).trim()
					TestObject significant_non_sign_Msg_ele = findTestObject('Object Repository/RBC_Objects/Page_PBS/RBC_regraded_significant_non-sig_status')
					WebUI.waitForElementVisible(significant_non_sign_Msg_ele, 10)
					String signifiacnt_status_msg = WebUI.getText(significant_non_sign_Msg_ele).trim()
					println("Regrade message: " + regrade_msg)
					println("Signifcant status message: " + signifiacnt_status_msg)
					assert regrade_msg == (cellname + ' regraded from ' + (i+1) + ' to ' + i)
					if(i==0 || i==1) {
						assert signifiacnt_status_msg == (cellname_removed + ' is now non significant')
						//Getting the color of an non-significant dot
						WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
						String dotColor = dot.getCssValue("background-color");
						System.out.println("Dot color: " + dotColor);
						assert dotColor=="rgba(50, 152, 93, 1)"
					}
					else {
						assert signifiacnt_status_msg == (cellname_removed + ' is now significant')
						//Getting the color of an significant dot
						WebElement dot = row.findElement(By.xpath(".//div[contains(@class, 'default')]"));
						String dotColor = dot.getCssValue("background-color");
						System.out.println("Dot color: " + dotColor);
						assert dotColor=="rgba(198, 27, 28, 1)"
					}
					WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/toast_msg_close_icon'))
					println('closed a toast msg')
				}
			}
		}
	}
}
