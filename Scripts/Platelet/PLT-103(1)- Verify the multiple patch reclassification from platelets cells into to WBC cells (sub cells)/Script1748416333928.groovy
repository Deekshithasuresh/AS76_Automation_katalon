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


CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('jyothi')

//Reclassify Large platelets to sub-cell of WBC
res.classifyPlateletToWbcSubCell("Large Platelets","Band Forms", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Hypersegmented", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Neutrophils with Toxic Granules", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Reactive", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Large Granular Lymphocytes", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Atypical Cells", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Lymphoid Blasts", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Myeloid Blasts", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Promyelocytes", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Myelocytes", 2)
res.classifyPlateletToWbcSubCell("Large Platelets","Metamyelocytes", 2)

//Reclassify Platelet clumps to sub-cell of WBC
res.classifyPlateletToWbcSubCell("Platelet Clumps","Band Forms", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Hypersegmented", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Neutrophils with Toxic Granules", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Reactive", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Large Granular Lymphocytes", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Atypical Cells", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Lymphoid Blasts", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Myeloid Blasts", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Promyelocytes", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Myelocytes", 2)
res.classifyPlateletToWbcSubCell("Platelet Clumps","Metamyelocytes", 2)
