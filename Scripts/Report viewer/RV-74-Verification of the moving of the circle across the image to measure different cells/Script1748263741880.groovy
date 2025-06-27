import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// Selenium / Katalon
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ← Added for Robot-based drags ↓
import java.awt.Robot
import java.awt.event.InputEvent

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 4) SWITCH TO WBC → MICROSCOPIC VIEW & WAIT
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"))
WebUI.delay(120)

// 5) ZOOM IN TWICE
TestObject zoomIn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
WebUI.waitForElementClickable(zoomIn, 30)
(1..2).each { i ->
	WebUI.click(zoomIn)
	WebUI.delay(120)
	WebUI.comment("✔ Zoom-in #${i} complete")
}

// 6) HOVER & CLICK LINE TOOL
TestObject lineTool = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='line-tool']")
WebUI.waitForElementVisible(lineTool, 30)
WebUI.mouseOver(lineTool)
WebUI.delay(1)
WebUI.click(lineTool)
WebUI.delay(10)

// 7) DRAG THE GREEN LINE WITH ROBOT
int startX = 889
int startY = 544
int delta  = 100

Robot robot = new Robot()
robot.setAutoDelay(50)

def moves = [
	[dx:-delta, dy:   0],   // left
	[dx: delta, dy:   0],   // right
	[dx:    0,  dy:-delta], // up
	[dx:    0,  dy: delta]  // down
]

moves.eachWithIndex { move, idx ->
	robot.mouseMove(startX, startY)
	Thread.sleep(200)
	robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
	Thread.sleep(200)
	robot.mouseMove(startX + move.dx, startY + move.dy)
	Thread.sleep(200)
	robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
	Thread.sleep(200)
	WebUI.comment("✔ Line-tool drag #${idx+1}: (${move.dx}, ${move.dy})")
}

// 8) HOVER & CLICK CIRCLE TOOL
TestObject circleTool = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='circle-tool']")
WebUI.waitForElementVisible(circleTool, 30)
WebUI.mouseOver(circleTool)
WebUI.delay(1)
WebUI.click(circleTool)
WebUI.delay(10)

// 9) DRAG THE CIRCLE WITH ROBOT
moves.eachWithIndex { move, idx ->
	robot.mouseMove(startX, startY)
	Thread.sleep(200)
	robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
	Thread.sleep(200)
	robot.mouseMove(startX + move.dx, startY + move.dy)
	Thread.sleep(200)
	robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
	Thread.sleep(200)
	WebUI.comment("✔ Circle-tool drag #${idx+1}: (${move.dx}, ${move.dy})")
}

WebUI.comment("✅ Completed all drags with both line and circle tools.")
