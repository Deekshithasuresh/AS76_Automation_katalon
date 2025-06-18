import com.kms.katalon.core.annotation.TearDownTestCase
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
class CloseBrowserListener {
	@TearDownTestCase
	def tearDownTestCase() {
		WebUI.closeBrowser()
	}
}

