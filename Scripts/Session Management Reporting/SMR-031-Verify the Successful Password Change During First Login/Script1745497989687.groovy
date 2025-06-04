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
import loginPack.Login as Login
import java.util.UUID as UUID
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By

Login log1 = new Login()

log1.adminLogin()

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Users'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Create User'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Name_rbc-input-box'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Name_rbc-input-box_1'), 
    'vishwajyothi')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Email_rbc-input-box'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Email_rbc-input-box_1_2'), 
    'vishwajyothi@gmail.com')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_rbc-input-box'))

//  Generate random username
WebDriver driver = DriverFactory.getWebDriver()

// Your Username input field object
TestObject usernameField = findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_rbc-input-box')

// Generate username method
// Only letters
// Fill username, check, retry if needed
boolean usernameAccepted = false

String uniqueUsername = ''

while (!(usernameAccepted)) {
    uniqueUsername = generateUsername()

    println('Trying username: ' + uniqueUsername)

    WebUI.setText(usernameField, uniqueUsername)

    WebUI.delay(1)

    // Check if error message is displayed
    boolean isError = WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Username already taken'), 
        1, FailureHandling.OPTIONAL)

    if (isError) {
        println('Username already taken. Retrying...')

        //        WebUI.clearText(usernameField) 
        WebUI.click(usernameField)

        WebUI.sendKeys(usernameField, Keys.chord(Keys.COMMAND, 'a'))

        WebUI.sendKeys(usernameField, Keys.chord(Keys.BACK_SPACE))
    } else {
        println('Username accepted: ' + uniqueUsername)

        usernameAccepted = true
    }
}

//WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_rbc-input-box_1_2_3_4_5_6_7_8_9_10_11'), 
//    uniqueUsername)
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_rbc-input-box'))
WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_rbc-input-box_1_2_3'), 
    'vishwajyothi@123')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/label_Reviewer'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Create new user'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Create and copy'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Logout'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Logout'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Sign in'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Sign in'), 
    'Sign in')

WebUI.newTab('https://as76-pbs.sigtuple.com/login')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), uniqueUsername)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_password_loginPassword'), 'vishwajyothi@123')

WebUI.click(findTestObject('Session management reporting/Page_PBS/button_Sign In_for_PBS login'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Terms of service'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Terms of service'), 
    'Terms of service')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_TERMS OF SERVICE-----------------------_69eaa8'), 
    30)

//WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_TERMS OF SERVICE-----------------------_69eaa8'), 
//    'TERMS OF SERVICE\n------------------------------------------------------------\n\n\nLast Updated: [Apr 18 , 2024]\n\nThis document is an electronic record as per the Indian Contract Act, 1872, Information Technology Act, 2000, the rules made thereunder (as applicable), and the amended provisions pertaining to electronic records in various other statutes, as amended by the Information Technology Act, 2000 (“Act”). This electronic record is generated by a computer platform and does not require any physical or digital signatures.\n\nThis document is published in accordance with the Act and related rules, to specify the rules and regulations, privacy policy and Terms of Service (“ToS” or “Agreement”) for access or usage of Diagnostic Platform (as defined hereunder). The terms and conditions laid down in this ToS shall be construed as an agreement and govern your use of the Diagnosis Platform; by using the Diagnosis Platform, you accept these terms and conditions in full. Each time you access and/or use the diagnosis platform, you agree to be bound by and comply with all of the terms of this ToS. You irrevocably and unconditionally are agreeing to comply with, abide by and be bound by all the obligations as stipulated in this ToS, which together with our Privacy Policy and any other applicable policies referred to herein or made available by on the Diagnosis Platform, shall govern Company’s relationship with you in relation to the Diagnosis Platform. These ToS supersede all previous oral and written terms and conditions (if any) communicated to you and shall act as a binding Agreement between you and Company. You are required to carefully go through these ToS prior to using or availing access to the Diagnosis Platform.\n\nSigtuple Technologies Private Limited (“Company”) provides a solution (hardware/software) for automated analysis of medical images and to aid diagnosis.\n\nIF YOU DO NOT AGREE TO BE BOUND BY ALL CONDITIONS/CLAUSES CAPTURED IN THIS TOS, PLEASE DO NOT USE THE DIAGNOSIS PLATFORM OR THE SERVICES.\n\n\n1. Definitions\n------------------------------------------------------------\n\n"Company", "we", "us" or "our" shall mean Sigtuple Technologies Private limited and any other companies that are subsidiaries and affiliates.\n\n"Content" means and includes, without limitation, data, text, graphics, audio clips, audio chats, IM/chat transcripts, photographs, videos, software, scripts, images and any other material entered, processed, contained on or accessed through the Diagnosis Platform, including content created, modified, validated or submitted by Users.\n\n“Diagnosis Platform” means a platform used by the registered Medical Laboratory Technologists or User to generate a detailed automated diagnosis report by using a combined medical device and application i.e. AS76 with PBS in the manner as specified by the Company. This Diagnosis Platform or any part thereof shall be used or accessed from any such platform capable of accessing diagnosis platforms, such as computer platforms, mobile phones, tablets and any such other devices.\n\n“Medical Specialists” means and includes a user who is a certified medical practitioner or a doctor or a medical expert or a pathologist but shall not include assistants, administrative staff, apprentices or any other person who under the law is not a qualified and certified personnel to diagnose and provide medical opinion or reports to Patients.\n\n“Patient” means and includes any individual that procures the services of the user for the purpose of conducting a laboratory test or diagnosis test on any such samples provided (whether or not) for the purpose of health problems faced by the individual.\n\n“Protected Health Information” or “PHI” means and includes any information provided by the patient related to his/her health or any such information pertaining to the identity of the Patient.\n\n"Service(s)"means services provided through the Diagnosis Platform, including but not limited to image capturing of blood smear and analysis of the smear which shall be made available to the patient or an individual who has opted for a test only after the approval of a Medical Specialist. The Services may change from time to time, at the sole discretion of the Company. \n\n“AS 76 with PBS” is owned and operated by the Company and shall mean a product / an application used by the registered Medical Laboratory Technologists to capture images of the blood smear of the Patient that has to be tested with any other applications (or version) released later, all of which are connected to the Diagnosis Platform through integration.\n\n“Smear” means and includes any sample which can be spread thinly on a microscope slide for examination, for the purpose of medical diagnosis.\n\n\n“User” , “You” , “Medical Laboratory Technologists” means users authorized to use the Diagnosis Platform by the Company, which includes but is not limited to a Medical Specialist, a clinician, a lab technologist (whether an individual professional or an organization) who are involved in the process of medical diagnosis or similar institution availing the services provided by the Company on the Diagnosis Platform, including designated, authorized associates or executives including but not limited to any such person authorized by You to access or use the diagnosis Platform on Your behalf .\n\n\n\n\n2. Acceptance of Terms\n------------------------------------------------------------\n\nYour use of the Diagnosis Platform is subject to these ToS, which may be updated, amended, modified or revised by us from time to time without any notice to You. It is important for You to refer to these ToS from time to time to make sure that You are aware of any additions, revisions, amendments or modifications that we may have made to these ToS. Your use of the Diagnosis Platform and engagement with us constitutes your acceptance of these ToS.\n\n\n3. Service and Registration Process\n------------------------------------------------------------\n\nThe Service is designed to assist User registered on the Diagnosis Platform for obtaining and analyzing the \nSmear through the Diagnosis Platform for a detailed diagnostic report derived from the images of the Smear as well as tracking other data related to the report. \n\n\nThe services also include training and providing instructions to the user to operate the Diagnosis Platform in a way to achieve optimum results, it is your responsibility to understand and use the application in its intended way and take necessary precautions and care.\n\nThe Company reserves the right, in its sole discretion, to modify or replace all or any part of the ToS (including, without limitation, pricing and payment terms set forth in “Fees and Payment”), or change, suspend, or discontinue all or any part of the Service at any time by posting a notice on the Service or by sending You an email. It is Your responsibility to check the ToS periodically for changes. Your continued use of the Service following the posting of any changes to the ToS constitutes Your acceptance of those changes.\n\n\n4. Registration\n------------------------------------------------------------\n\nFor users, it is only possible to initiate the registration process through the Diagnosis Platform or through any such medium approved and agreed by the Company, upon receiving all the details required for the registration (as informed by the Company prior to registering the user on the Diagnosis Platform). As part of the registration process You will need to create an account, including a username and password either by yourself or with the assistance of the Company. It is Your responsibility to ensure that the information You provide is accurate, secure, and not misleading. You cannot create an account/username and password using (i) the names and information of another person; or (ii) words that are the trademarks or the property of another party (including ours); or (iii) words that in any other way are inappropriate. We reserve the right with or without notice to suspend or terminate any account in breach of the above conditions. If the registration is done by the Company, on your behalf, it shall be notified to You, upon the creation of the account and You will be required to/permitted to access the Diagnosis platform.\n\nYou shall be required to activate Your account by signing in and (i) immediately changing Your password; and (ii) accepting these ToS and the Privacy Policy and any other related policies on the Diagnosis platform. By registering on the Diagnosis Platform, You represent and warrant that (a) You are a licensed/authorized Medical Laboratory Technologist registered with the appropriate authority including but not restricted to All India Medical Laboratory Technologists Association, Indian Medical Association, or any such regulatory authority (b) You/Your organization has all the required licenses under the applicable laws to provide services to the Patients (c) there are no legal or regulatory impediments which prevent You from practicing as a Medical Specialist/s in India (d) You are authorized to operate a medical laboratory (e) it is Your responsibility to manage the usage or access of Your account by any such third party (including assistants, associates, or other administrative staff\netc.) and You shall be responsible for any damage caused by such third parties through usage of Your account. You represent and warrant that all such documents and their particulars are true and have not been obtained through any unfair, fraudulent, unethical, suspect or illegal means.\n\n\n5. Duties of a User\n------------------------------------------------------------\n\nUnder no circumstances a user shall provide the report to the respective patient without an approval of a Medical Specialist.\n\nIt shall be the duty of a user that after receiving a detailed report through our Diagnostic Platform that it shall obtain an approval certificate by a certified Medical Specialist and only then provide it to the respective Patient.\n\nThe Diagnostic Platform does not require any Protected Health Information or personally identifiable information of the Patient for generating the report, and it shall be the duty of the user to take necessary precautions and care while transmitting the content through the Diagnostic Platform and such Content shall not include any such information of the Patient which include Protected Health Information and personally identifiable information of the Patient. In case such information is provided to us we won’t be liable for any breach, misuse, leak or any undesired use of such information.\n\n\n\n\n\n\n\n6. Fees and Payment\n------------------------------------------------------------\n\nAs and when decided/implemented or basis any other such agreement executed with the user, the Company shall be entitled to levy a fee for availing the Services and using the usage Diagnosis Platform.\n\n\n7. Use of Data\n------------------------------------------------------------\n\nThrough use of the Diagnosis Platform, you shall have access to diagnostic and analytical information related to the smears of the Patient. The Diagnostic Platform will only collect the images and any other data related to smears so as to use it to provide a detailed report.\n\nIn case you wish to download any images stored in the Diagnostic Platform, it shall be your responsibility to use these images with care and shall prohibit usage of these images in a manner which results in exploitation.\n\nThe Diagnostic Platform at no point of time will require any personal health data or information of the Patient. Under no circumstances PHI and personally identifiable information of the patient will be collected by means of which any identity of the Patient can be recognised.\n\nYour usage of the data through the Diagnosis Platform shall at no point contravene the terms of the Privacy Policy. Additionally, You agree to treat it as confidential all data that You are provided access to, and that You shall use Your best efforts to maintain the confidentiality of such Data. You acknowledge that a breach of this section may lead to substantial losses or damages to Company and the respective Patients, and You agree to defend, indemnify and hold harmless the Company, its affiliates/subsidiaries/JV partners and each of its, and its affiliates/subsidiaries/JV partners employees, contractors, directors, suppliers and representatives and in addition, the Patients of the Diagnosis Platform from all damages, losses, liabilities, costs and fees incurred as result of Your breach of this provision. Further, You shall observe all caution and the applicable duty of care in provision of Services under these ToS.\n\nYou agree that the data is provided by you on the Diagnosis Platform and Company presents this Data to You in an As-Is manner and does not make any warranties, representations, etc. on the accuracy and correctness of this data.\n\n\n\n\n\n\n\n8. Termination\n------------------------------------------------------------\n\nThe Company reserves the right to suspend or terminate Your access to the Diagnosis Platform and the Services with or without notice and to exercise any other remedy available under law, in cases where,\n\nYou are in breach of any of the terms and conditions of the Agreement;\nA third party reports violation of any of its right as a result of Your use of the Services;\nThe Company is unable to verify or authenticate any information provided to Company by You.\nThe Company has reasonable grounds for suspecting any illegal, fraudulent or abusive activity on part of You.\nThe Company believes in its sole discretion that Your actions may cause legal liability for You, other Users or for the Company, or are contrary to the interests of the Diagnosis Platform.\n\nOnce temporarily suspended, indefinitely suspended or terminated, You may not continue to use the Diagnosis Platform under the same account, a different account or re-register under a new account. On termination of an account due to the reasons mentioned herein, You shall no longer have access to data, messages, files and other material stored or placed on the Diagnosis Platform by You. You shall ensure that You maintain a continuous backup of any medical services You have rendered in order to comply with record keeping process and practices.\n\nAll provisions of the ToS, which by their nature should survive termination, shall survive termination, including, without limitation, warranty disclaimers, indemnity and limitations of liability\n\n\n9. Disclaimer\n------------------------------------------------------------\n\nTHE DIAGNOSIS PLATFORM (INCLUDING, WITHOUT LIMITATION, ANY CONTENT) IS PROVIDED “AS IS” AND “AS AVAILABLE” AND IS WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND ANY WARRANTIES IMPLIED BY ANY COURSE OF PERFORMANCE OR USAGE OF TRADE, ALL OF WHICH ARE EXPRESSLY DISCLAIMED. THE COMPANY AND ITS DIRECTORS, EMPLOYEES, AGENTS, SUPPLIERS, SPONSORS AND PARTNERS DO NOT WARRANT THAT: (A) THE SERVICE WILL BE SECURE OR AVAILABLE AT ANY PARTICULAR TIME OR LOCATION; (B) ANY DEFECTS OR ERRORS WILL BE CORRECTED; (C) ANY CONTENT OR SOFTWARE AVAILABLE AT OR THROUGH THE SERVICE IS FREE OF VIRUSES OR OTHER HARMFUL COMPONENTS; OR (D) THE RESULTS OF USING THE SERVICE WILL MEET YOUR REQUIREMENTS. YOUR USE OF THE SERVICE IS SOLELY AT YOUR OWN RISK. SOME STATES DO NOT ALLOW LIMITATIONS ON HOW LONG AN IMPLIED WARRANTY LASTS, SO THE ABOVE LIMITATIONS MAY NOT APPLY TO YOU.\n\nYOU SHALL REFER TO AND ALWAYS BE BOUND BY THE COMPANY’S INSTRUCTIONS AND POLICIES AS AVAILABLE ON THE DIAGNOSIS PLATFORM.\n\n10. Indemnification\n------------------------------------------------------------\n\nYou agree to, and you hereby, defend, indemnify, and hold the Company harmless and its affiliates/subsidiaries/JV partners employees, contractors, directors, suppliers and representatives from all liabilities, losses, claims, and expenses, harmless from and against any and all claims, damages, losses, costs, investigations, liabilities, judgments, fines, penalties, settlements, interest, and expenses (including attorneys’ fees) that directly or indirectly arise from or are related to any claim, suit, action, demand, or proceeding made or brought against the Company, or on account of the investigation, defense, or settlement thereof, arising out of or in connection with: (i) your User-Generated Content; (ii) your use of the Company Diagnosis Platform and your activities in connection with the Diagnosis Platform; (iii) your breach or anticipatory breach of this ToS; (iv) your violation or anticipatory violation of any laws, rules, regulations, codes, statutes, ordinances, or orders of any\ngovernmental and quasi-governmental authorities in connection with your use of the Company Diagnosis platform or your activities in connection with the Diagnosis Platforms; (iv) information or material transmitted through your internet device, even if not submitted by you, that infringes, violates, or misappropriate any copyright, trademark, trade secret, trade dress, patent, publicity, privacy, or other right of any person or entity; (v) any misrepresentation made by you; You will cooperate as fully required by Company in the defense of any Claim and Losses. Notwithstanding the foregoing, the Company retains the exclusive right to settle, compromise, and pay any and all claims and losses. Company reserves the right to assume the exclusive defense and control of any claims and losses. You will not settle any claims and losses without (in each instance) the prior written consent of an officer of a Company.\n\n\n11. Limitation of Liability\n------------------------------------------------------------\n\nEXCEPT AS MAY BE PROVIDED IN ANY ADDITIONAL TERMS, TO THE FULLEST EXTENT PERMITTED BY APPLICABLE LAW, IN NO EVENT WILL THE TOTAL LIABILITY OF COMPANY TO YOU, FOR ALL POSSIBLE DAMAGES, LOSSES, AND CAUSES OF ACTION IN CONNECTION WITH YOUR ACCESS TO AND USE OF THE COMPANY DIAGNOSIS PLATFORM AND YOUR RIGHTS UNDER THIS TOS, EXCEED AN AMOUNT EQUAL TO 10% OF THE AMOUNT YOU HAVE PAID TO THE COMPANY IN CONNECTION WITH THE TRANSACTION(S) THAT UNDERLIE THE CLAIM(S).\n\n\n12. Content Ownership and Copyright Conditions of Access\n------------------------------------------------------------\nThe contents listed on the Diagnosis Platform belong to the Company. The information that is collected by us directly or indirectly from You shall belong to the Company. Copying of the copyrighted content published by the Company on the Diagnosis Platform, for any commercial purpose or for the purpose of earning profit or for the purpose of exploitation, shall constitute a violation of copyright and the Company reserves its rights under applicable law accordingly.\n\nWe authorize Users to view and access the content available on or from the Diagnosis Platform solely for receiving, interacting, delivering and communicating only as per this Agreement. The contents of the Diagnosis platform or generated using Diagnosis Platform, including the reports, analysis, information, text, graphics, images, logos, button icons, software code, design, and the collection, arrangement and assembly of content on the Diagnosis Platform (collectively, "Our Content"), shall be/are our property and are protected under copyright, trademark and other laws. users shall not modify Our Content or reproduce, display, publicly perform, distribute, or otherwise use Our Content in any way for any public or commercial purpose or for personal or commercial gain.\n\nFor the purpose of understanding the data generated out of the Diagnosis Platform (including Our Content) can be used by us for any such purpose, as we deem fit in our sole discretion, including but not limited to combing or analyzing the reports generated with multiple ways, for enhancing the functionality of the Diagnosis Platform, and shall always be owned by the Company.\n\n\n13. Account Ownership and Editing Rights\n------------------------------------------------------------\n\nIn any event, for making any changes to Your registered account, You shall reach out to our support team and upon scrutinizing the change with proper documentation, Your profile shall be updated by our support team. We reserve the right of ownership of all the user profiles created and photographs and to moderate the changes or updates requested by You. However, we shall take the independent decision whether to publish or reject the requests submitted for the respective changes or updates in Your account. You hereby represent and warrant that You are fully entitled under law to upload all content uploaded by You as part of Your profile or otherwise while using our Services, and that no such content breaches any third party rights, including intellectual property rights. Upon becoming aware of a breach of the foregoing representation, we may modify or delete parts of Your account information at our sole discretion with or without notice to You.\n\n\n\n\n14. Reviews and Feedback Display Rights of Company\n------------------------------------------------------------\n\nWe reserve the right to collect feedback and critical content from all the users registered on the Diagnosis Platform.\n\nWe shall have no obligation to pre-screen, review, flag, filter, modify, refuse or remove any or all critical content from any Service, except as required by applicable law.\n\nYou understand that by using the Services You may be exposed to critical content or other content that You may find offensive or objectionable. We shall not be liable for any effect on Your business due to such critical content of a negative nature. In these respects, You may use the Service at Your own risk. We shall, as a service provider, take steps as required to comply with applicable law as regards the publication of critical content. The legal rights and obligations with respect to critical content and any other information sought to be published by User shall be in accordance with these ToS.\n\nWe will take down information under standards consistent with applicable law, and shall in no circumstances be liable or responsible for critical content, which has been created by the user. The principles set out in relation to third party content in these ToS shall be applicable mutatis mutandis in relation to critical content posted on the Diagnosis Platform.\n\nIf we determine that You have provided inaccurate information or enabled fraudulent feedback, we reserve the right to immediately suspend any of Your accounts with us and make such a declaration on the Diagnosis platform alongside Your name/Your clinics name as determined by us for the protection of our business and in the interests of the other users.\n\n\n15. Independent Services\n------------------------------------------------------------\n\nYour use of each Service confers upon You only the rights and obligations relating to such Service, and not to any other service that may be provided by Company.\n\n\n16. Our Reach Rights\n------------------------------------------------------------\n\nWe reserve the rights to display sponsored ads on the Diagnosis Platform. These ads would be marked as “Sponsored Listings”. Without prejudice to the status of other content, we will not be liable for the accuracy of information or the claims made in the Sponsored Listings. We do not encourage You to visit the Sponsored Listings page or to avail any services from them. We will not be liable for the services of the providers of the Sponsored Listings.\n\nYou represent and warrant that You will use these Services in accordance with applicable law. Any contravention of applicable law as a result of Your use of these Services is Your sole responsibility, and we shall accept no liability for the same.\n\n\n17. Rights and Obligation relating to the Content\n------------------------------------------------------------\n\nUsers shall be prohibited from:\n\nviolating or attempting to violate the integrity or security of the Diagnosis Platform or any content thereof.\ntransmitting any information (including job posts, messages and hyperlinks) on or through the Diagnosis Platform that is disruptive or competitive to the provision of our Services.\nintentionally submitting on the Diagnosis Platform any incomplete, false or inaccurate information.\nmaking any unsolicited communications to other Users or us.\nusing any engine, software, tool, agent or other device or mechanism (such as spiders, robots, avatars or intelligent agents) to navigate or search the Diagnosis Platform.\nattempting to decipher, decompile, disassemble or reverse engineer any part of the Diagnosis Platform.\ncopying or duplicating in any manner any of our Content or other information available from the Diagnosis Platform.\nframing or hot linking or deep linking of any Content.\ncircumventing or disabling any digital rights management, usage rules, or other security features of the Diagnosis Platform.\n\nThe Company shall upon obtaining knowledge by itself or been brought to actual knowledge by an affected person in writing or through email signed with electronic signature about any such information as mentioned above, shall be entitled to disable such information that is in contravention of this provision. The Company shall also be entitled to preserve such information and associated records for at least 90 (ninety) days for production to governmental authorities for investigation purposes.\n\nIn case of non-compliance with any applicable laws, rules or regulations, or the Agreement (including the Privacy Policy) by a user, the Company will have the right to immediately terminate Your access or usage rights to the Diagnosis Platform and Services and to remove non-compliant information from the Diagnosis Platform.\n\nWe may disclose or transfer user generated information to our affiliates or governmental authorities in such manner as permitted or required by applicable law, and You hereby consent to such transfer. In accordance with the applicable laws, we shall transfer sensitive personal data or information including any information, to any other body corporate or a person in India, or located in any other country, that ensures the same level of data protection that is adhered to by us, only if such transfer is necessary for the performance of the lawful contract between Company or any person on its behalf and the USER or where the USER has consented to data transfer.\n\nCompany respects the intellectual property rights of others and we do not hold any responsibility for any violations of any intellectual property rights.\n\n\n18. Severability\n------------------------------------------------------------\n\nIf a provision of these terms and conditions is determined by any court or other competent authority to be unlawful and/or unenforceable, the other provisions will continue in effect. If any unlawful and/or unenforceable provision would be lawful or enforceable if part of it were deleted, that part will be deemed to be deleted, and the rest of the provision will continue in effect.\n\n\n19. Governing Law\n------------------------------------------------------------\n\nThese ToS will be governed by and construed in accordance with Indian law, and any disputes relating to these terms and conditions will be subject to the exclusive jurisdiction of the courts of Bangalore, India.\n\n\n20. Miscellaneous\n------------------------------------------------------------\n\nNothing in this ToS is intended to, or shall be deemed to, establish any partnership or joint venture between any of the parties, constitute any party the agent of another party, nor authorize any party to make or enter into any commitments for or on behalf of any other party.\n\nIf we fail to exercise or enforce a right under the ToS that failure shall not constitute a waiver of such right or provision.\n\nThis ToS sets out our entire agreement and understanding with respect to the subject matter of these terms and conditions and supersedes all representations, communications and prior agreements (written or oral).\n\nYou acknowledge that on entering into this ToS, it does not rely, and has not relied, upon any representation (whether negligent or innocent), statement or warranty made or agreed to by any person (whether a party to this ToS or not) except those expressly set out in this ToS.\n\nUnless expressly provided in the Terms and Conditions no term of them is enforceable by any person who is not a party to it.\n\nAll notices under the ToS will be in writing and will be deemed to have been duly given when received, if personally delivered or sent by certified or registered mail, return receipt requested; when receipt is electronically confirmed, if transmitted by facsimile or e-mail; or two days after it is sent, if sent for next day delivery by recognized overnight delivery service.')
WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/button_I Accept'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/button_I Accept'), 'I Accept')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_I Accept'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/h4_Reset password'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/h4_Reset password'), 'Reset password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/label_New password'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/label_New password'), 'New password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    30)

// Get the placeholder attribute
String placeholder = WebUI.getAttribute(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'placeholder')

// Verify placeholder
WebUI.verifyMatch(placeholder, 'Enter new password', false)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/label_Confirm password'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/label_Confirm password'), 
    'Confirm password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    30)

// Get the placeholder attribute
String placeholder1 = WebUI.getAttribute(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'placeholder')

// Verify placeholder
WebUI.verifyMatch(placeholder1, 'Confirm password', false)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'jyothi1996')

WebUI.click(findTestObject('Session management reporting/Page_PBS/hide_img_for_new_passwd_in reset password page'))

WebUI.click(findTestObject('Session management reporting/Page_PBS/show_img_for_new_passwd_in reset password page'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'jyothi1996')

WebUI.click(findTestObject('Session management reporting/Page_PBS/hide_img_for_confirm_passwd_in reset password page'))

WebUI.click(findTestObject('Session management reporting/Page_PBS/show_img_for_confirm_passwd_in reset password page'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_Password must have atleast 1 special character'), 
    'Password must have atleast 1 special character')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Has at-least 1 special character'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Has at-least 1 special character'), 
    'Has at-least 1 special character')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/Round_Criteria_Notmet_for_Has at-least 1 special character'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_Password must be atleast 8 characters long'), 
    'Password must be atleast 8 characters long')

//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Is alphanumeric'), 
//    30)
WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Is alphanumeric'), 'Is alphanumeric')

//WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/Round_Criteria_Notmet_for_Is alphanumeric'), 
//    30)
//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Contains at-least 8 characters'), 
//    0)
WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Contains at-least 8 characters'), 
    'Contains at-least 8 characters')

//WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/Round_Criteria_Notmet_for_Contains at-least 8 characters'), 
//    0)
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Has at-least 1 special character'), 
//    0)
WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Has at-least 1 special character'), 
    'Has at-least 1 special character')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/Round_Criteria_Notmet_for_Has at-least 1 special character'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'jyo@19')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'jyo@19')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_Password must be atleast 8 characters long'), 
    'Password must be atleast 8 characters long')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Contains at-least 8 characters'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Contains at-least 8 characters'), 
    'Contains at-least 8 characters')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/Round_Criteria_Notmet_for_Contains at-least 8 characters'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'jyothi1996')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'jyothi1996')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_Password must have atleast 1 special character'), 
    'Password must have atleast 1 special character')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Has at-least 1 special character'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Has at-least 1 special character'), 
    'Has at-least 1 special character')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/Round_Criteria_Notmet_for_Has at-least 1 special character'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'jyothi@@')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'jyothi@@')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_Password must have atleast 1 alphabet and number'), 
    'Password must have atleast 1 alphabet and number')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Is alphanumeric'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Is alphanumeric'), 'Is alphanumeric')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/Round_Criteria_Notmet_for_Is alphanumeric'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'jyothi@1996')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'vishwa@1996')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_New password and Confirm password does not match'), 
    'New password and Confirm password does not match')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
//    'jFUrjnaiyLP15wudMGXDibWIAswG2Ynx')
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_1'))
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
//    'jFUrjnaiyLP15wudMGXDibWIAswG2Ynx')
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_1_2'))
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password'), 
//    0)
//
//WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password'))
//
//WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password'), 
//    'Confirm password')
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password'))
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/img_1_2_3_4_5_6_7'), 
//    0)
//
//WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/h4_Successful password reset'), 
//    'Successful password reset!')
//
//WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_You can now use your new password to sign_6f5a89'), 
//    'You can now use your new password to sign in to your account!')
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Sign In'), 0)
//
//WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Sign In'), 'Sign in')
//
//WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Sign In'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Sign In'))
//WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), uniqueUsername)
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_password_loginPassword'), 
//    'jFUrjnaiyLP15wudMGXDibWIAswG2Ynx')
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_1_2'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Sign In'))
WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'jyothi@1996')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'jyothi@1996')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password_MuiButtonBase-root _50d72c'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/img_1_2_3_4_5_6_7'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/h4_Successful password reset'), 
    'Successful password reset!')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_You can now use your new password to sign_6f5a89'), 
    'You can now use your new password to sign in to your account!')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/button_sign_in_for_successful password reset'), 
    30)

WebUI.verifyElementText(findTestObject('Session management reporting/Page_PBS/button_sign_in_for_successful password reset'), 
    'Sign in')

WebUI.verifyElementClickable(findTestObject('Session management reporting/Page_PBS/button_sign_in_for_successful password reset'))

WebUI.click(findTestObject('Session management reporting/Page_PBS/button_sign_in_for_successful password reset'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), uniqueUsername)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1996')

WebUI.click(findTestObject('Session management reporting/Page_PBS/button_Sign In_for_PBS login'))

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/span_My reports'), 2)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Change Password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Current password_old-password'), 
    'jyothi@1996')

WebUI.click(findTestObject('Session management reporting/Page_PBS/hide_img_for_current_passwd_in change password page'))

WebUI.click(findTestObject('Session management reporting/Page_PBS/show_password_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'jyothi@1995')

WebUI.click(findTestObject('Session management reporting/Page_PBS/hide_img_for_new_passwd_in chnage passowrd page'))

WebUI.click(findTestObject('Session management reporting/Page_PBS/show_password_img'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'jyothi@1995')

WebUI.click(findTestObject('Session management reporting/Page_PBS/hide_img_for_confirm_passwd_in change password page'))

WebUI.click(findTestObject('Session management reporting/Page_PBS/show_password_img'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password'), 
    'Confirm password')

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Confirm password'))

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/img_for_password_changed'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/h4_Password changed'), 'Password changed!')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_Your password has been successfully chang_99a351'), 
    'Your password has been successfully changed. Please login using the new password')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/button_sign_in_for_password changed'), 
    30)

WebUI.verifyElementText(findTestObject('Session management reporting/Page_PBS/button_sign_in_for_password changed'), 'Sign in')

WebUI.verifyElementClickable(findTestObject('Session management reporting/Page_PBS/button_sign_in_for_password changed'))

WebUI.click(findTestObject('Session management reporting/Page_PBS/button_sign_in_for_password changed'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), uniqueUsername)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Session management reporting/Page_PBS/hide_password_img'))

WebUI.click(findTestObject('Session management reporting/Page_PBS/show_password_img'))

WebUI.click(findTestObject('Session management reporting/Page_PBS/button_Sign In_for_PBS login'))

String generateUsername() {
    String randomText = UUID.randomUUID().toString().replaceAll('-', '').replaceAll('[^a-zA-Z]', '')

    return 'user-' + randomText.substring(0, 3)
}

