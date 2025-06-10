import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generic.Reclassificationvj
import generic.custumFunctionsvj

custumFunctionsvj cus = new custumFunctionsvj();

Reclassificationvj res = new Reclassificationvj();


WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

// Select a sample
//WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146'))

cus.selectReportByStatus('To be reviewed')

cus.assignOrReassignOnTabs("jyothi")

//Reclassify Large platelets to all main cells of WBC
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Neutrophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Lymphocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Eosinophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Monocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Basophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Immature Granulocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Atypical Cells/Blasts", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Immature Eosinophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Immature Basophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Promonocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Prolymphocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Hairy Cells", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Sezary Cells", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Plasma Cells", 2)
res.reclassifyPlateletToAllWBCMainCells("Large Platelets", "Others", 2)


//Reclassify Platelet clumps to all main cells of WBC
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Neutrophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Lymphocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Eosinophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Monocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Basophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Immature Granulocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Atypical Cells/Blasts", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Immature Eosinophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Immature Basophils", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Promonocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Prolymphocytes", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Hairy Cells", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Sezary Cells", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Plasma Cells", 2)
res.reclassifyPlateletToAllWBCMainCells("Platelet Clumps", "Others", 2)


