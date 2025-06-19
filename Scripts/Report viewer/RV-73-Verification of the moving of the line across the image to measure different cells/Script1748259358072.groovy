import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import java.awt.Robot
import java.awt.event.InputEvent

// ────────────────────────────────────────────────────
// 1) LOGIN & OPEN A REPORT
// ────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

TestObject toBe = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']")
TestObject under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe, 5)) {
	WebUI.click(toBe)
} else {
	WebUI.click(under)
}

// ────────────────────────────────────────────────────
// 2) SWITCH TO WBC → MICROSCOPIC VIEW & WAIT 120s
// ────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"))
WebUI.delay(120)

// ────────────────────────────────────────────────────
// 3) ZOOM IN TWICE WITH 120s BETWEEN
// ────────────────────────────────────────────────────
TestObject zoomIn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
WebUI.waitForElementClickable(zoomIn, 30)
(1..2).each { i ->
	WebUI.click(zoomIn)
	WebUI.delay(120)
	WebUI.comment("✔ Zoom-in #${i} complete")
}

// ────────────────────────────────────────────────────
// 4) HOVER & CLICK LINE TOOL → WAIT 10s
// ────────────────────────────────────────────────────
TestObject lineTool = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='line-tool']")
WebUI.waitForElementVisible(lineTool, 30)
WebUI.mouseOver(lineTool)
WebUI.delay(1)
WebUI.click(lineTool)
WebUI.delay(10)

// ────────────────────────────────────────────────────
// 5) DRAG THE GREEN LINE WITH ROBOT
// ────────────────────────────────────────────────────
int startX = 889
int startY = 544
int delta  = 100

Robot robot = new Robot()
robot.setAutoDelay(50)

// define four directional drags
def moves = [
	[dx:-delta, dy:   0],   // left
	[dx: delta, dy:   0],   // right
	[dx:    0,  dy:-delta], // up
	[dx:    0,  dy: delta]  // down
]

moves.eachWithIndex { move, idx ->
	// move cursor to line center
	robot.mouseMove(startX, startY)
	Thread.sleep(200)
	// click & hold
	robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
	Thread.sleep(200)
	// drag
	robot.mouseMove(startX + move.dx, startY + move.dy)
	Thread.sleep(200)
	// release
	robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
	Thread.sleep(200)

	WebUI.comment("✔ Drag #${idx+1}: (${move.dx}, ${move.dy})")
}

WebUI.comment("✅ Completed all directional drags on the green line.")
