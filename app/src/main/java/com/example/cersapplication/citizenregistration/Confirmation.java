package com.example.cersapplication.citizenregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import android.content.DialogInterface.OnClickListener;

import com.example.cersapplication.R;

public class Confirmation extends AppCompatActivity {

    CheckBox termsandCond, authorize;

    Button buttonUserProfile;

    MaterialAlertDialogBuilder termsandconditionsAlert;
    MaterialAlertDialogBuilder privacypolicy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        getActionBar().hide();

        termsandCond = findViewById(R.id.termsAndConditions);
        authorize = findViewById(R.id.Authorization);
        buttonUserProfile = findViewById(R.id.Continue);

        termsandconditionsAlert = new MaterialAlertDialogBuilder(this);
        privacypolicy = new MaterialAlertDialogBuilder(this);

        buttonUserProfile.setEnabled(false);
        buttonUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentStart = new Intent(Confirmation.this, StartProfileCitizen.class);
                intentStart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentStart);
            }
        });

        termsandCond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        termsandconditionsAlert.setTitle("TERMS AND CONDITIONS");
                        termsandconditionsAlert.setMessage("Terms and Conditions\n" +
                                "Last updated: April 19, 2023\n" +
                                "Please read these terms and conditions carefully before using Our Service.\n" +
                                "Interpretation and Definitions\n" +
                                "Interpretation\n" +
                                "The words of which the initial letter is capitalized have meanings defined under the following conditions. The following definitions shall have the same meaning regardless of whether they appear in singular or in plural.\n" +
                                "Definitions\n" +
                                "For the purposes of these Terms and Conditions:\n" +
                                "Application means the software program provided by the Company downloaded by You on any electronic device, named CERS\n" +
                                "Application Store means the digital distribution service operated and developed by Apple Inc. (Apple App Store) or Google Inc. (Google Play Store) in which the Application has been downloaded.\n" +
                                "Affiliate means an entity that controls, is controlled by or is under common control with a party, where \"control\" means ownership of 50% or more of the shares, equity interest or other securities entitled to vote for election of directors or other managing authority.\n" +
                                "Country refers to: Philippines\n" +
                                "Company (referred to as either \"the Company\", \"We\", \"Us\" or \"Our\" in this Agreement) refers to CERS .\n" +
                                "Device means any device that can access the Service such as a computer, a cellphone or a digital tablet.\n" +
                                "Service refers to the Application.\n" +
                                "Terms and Conditions (also referred as \"Terms\") mean these Terms and Conditions that form the entire agreement between You and the Company regarding the use of the Service. This Terms and Conditions agreement has been created with the help of the TermsFeed Terms and Conditions Generator.\n" +
                                "Third-party Social Media Service means any services or content (including data, information, products or services) provided by a third-party that may be displayed, included or made available by the Service.\n" +
                                "You means the individual accessing or using the Service, or the company, or other legal entity on behalf of which such individual is accessing or using the Service, as applicable.\n" +
                                "Acknowledgment\n" +
                                "These are the Terms and Conditions governing the use of this Service and the agreement that operates between You and the Company. These Terms and Conditions set out the rights and obligations of all users regarding the use of the Service.\n" +
                                "Your access to and use of the Service is conditioned on Your acceptance of and compliance with these Terms and Conditions. These Terms and Conditions apply to all visitors, users and others who access or use the Service.\n" +
                                "By accessing or using the Service You agree to be bound by these Terms and Conditions. If You disagree with any part of these Terms and Conditions then You may not access the Service.\n" +
                                "You represent that you are over the age of 18. The Company does not permit those under 18 to use the Service.\n" +
                                "Your access to and use of the Service is also conditioned on Your acceptance of and compliance with the Privacy Policy of the Company. Our Privacy Policy describes Our policies and procedures on the collection, use and disclosure of Your personal information when You use the Application or the Website and tells You about Your privacy rights and how the law protects You. Please read Our Privacy Policy carefully before using Our Service.\n" +
                                "Links to Other Websites\n" +
                                "Our Service may contain links to third-party web sites or services that are not owned or controlled by the Company.\n" +
                                "The Company has no control over, and assumes no responsibility for, the content, privacy policies, or practices of any third party web sites or services. You further acknowledge and agree that the Company shall not be responsible or liable, directly or indirectly, for any damage or loss caused or alleged to be caused by or in connection with the use of or reliance on any such content, goods or services available on or through any such web sites or services.\n" +
                                "We strongly advise You to read the terms and conditions and privacy policies of any third-party web sites or services that You visit.\n" +
                                "Termination\n" +
                                "We may terminate or suspend Your access immediately, without prior notice or liability, for any reason whatsoever, including without limitation if You breach these Terms and Conditions.\n" +
                                "Upon termination, Your right to use the Service will cease immediately.\n" +
                                "Limitation of Liability\n" +
                                "Notwithstanding any damages that You might incur, the entire liability of the Company and any of its suppliers under any provision of this Terms and Your exclusive remedy for all of the foregoing shall be limited to the amount actually paid by You through the Service or 100 USD if You haven't purchased anything through the Service.\n" +
                                "To the maximum extent permitted by applicable law, in no event shall the Company or its suppliers be liable for any special, incidental, indirect, or consequential damages whatsoever (including, but not limited to, damages for loss of profits, loss of data or other information, for business interruption, for personal injury, loss of privacy arising out of or in any way related to the use of or inability to use the Service, third-party software and/or third-party hardware used with the Service, or otherwise in connection with any provision of this Terms), even if the Company or any supplier has been advised of the possibility of such damages and even if the remedy fails of its essential purpose.\n" +
                                "Some states do not allow the exclusion of implied warranties or limitation of liability for incidental or consequential damages, which means that some of the above limitations may not apply. In these states, each party's liability will be limited to the greatest extent permitted by law.\n" +
                                "\"AS IS\" and \"AS AVAILABLE\" Disclaimer\n" +
                                "The Service is provided to You \"AS IS\" and \"AS AVAILABLE\" and with all faults and defects without warranty of any kind. To the maximum extent permitted under applicable law, the Company, on its own behalf and on behalf of its Affiliates and its and their respective licensors and service providers, expressly disclaims all warranties, whether express, implied, statutory or otherwise, with respect to the Service, including all implied warranties of merchantability, fitness for a particular purpose, title and non-infringement, and warranties that may arise out of course of dealing, course of performance, usage or trade practice. Without limitation to the foregoing, the Company provides no warranty or undertaking, and makes no representation of any kind that the Service will meet Your requirements, achieve any intended results, be compatible or work with any other software, applications, systems or services, operate without interruption, meet any performance or reliability standards or be error free or that any errors or defects can or will be corrected.\n" +
                                "Without limiting the foregoing, neither the Company nor any of the company's provider makes any representation or warranty of any kind, express or implied: (i) as to the operation or availability of the Service, or the information, content, and materials or products included thereon; (ii) that the Service will be uninterrupted or error-free; (iii) as to the accuracy, reliability, or currency of any information or content provided through the Service; or (iv) that the Service, its servers, the content, or e-mails sent from or on behalf of the Company are free of viruses, scripts, trojan horses, worms, malware, timebombs or other harmful components.\n" +
                                "Some jurisdictions do not allow the exclusion of certain types of warranties or limitations on applicable statutory rights of a consumer, so some or all of the above exclusions and limitations may not apply to You. But in such a case the exclusions and limitations set forth in this section shall be applied to the greatest extent enforceable under applicable law.\n" +
                                "Governing Law\n" +
                                "The laws of the Country, excluding its conflicts of law rules, shall govern this Terms and Your use of the Service. Your use of the Application may also be subject to other local, state, national, or international laws.\n" +
                                "Disputes Resolution\n" +
                                "If You have any concern or dispute about the Service, You agree to first try to resolve the dispute informally by contacting the Company.\n" +
                                "For European Union (EU) Users\n" +
                                "If You are a European Union consumer, you will benefit from any mandatory provisions of the law of the country in which you are resident in.\n" +
                                "United States Legal Compliance\n" +
                                "You represent and warrant that (i) You are not located in a country that is subject to the United States government embargo, or that has been designated by the United States government as a \"terrorist supporting\" country, and (ii) You are not listed on any United States government list of prohibited or restricted parties.\n" +
                                "Severability and Waiver\n" +
                                "Severability\n" +
                                "If any provision of these Terms is held to be unenforceable or invalid, such provision will be changed and interpreted to accomplish the objectives of such provision to the greatest extent possible under applicable law and the remaining provisions will continue in full force and effect.\n" +
                                "Waiver\n" +
                                "Except as provided herein, the failure to exercise a right or to require performance of an obligation under these Terms shall not effect a party's ability to exercise such right or require such performance at any time thereafter nor shall the waiver of a breach constitute a waiver of any subsequent breach.\n" +
                                "Translation Interpretation\n" +
                                "These Terms and Conditions may have been translated if We have made them available to You on our Service. You agree that the original English text shall prevail in the case of a dispute.\n" +
                                "Changes to These Terms and Conditions\n" +
                                "We reserve the right, at Our sole discretion, to modify or replace these Terms at any time. If a revision is material We will make reasonable efforts to provide at least 30 days' notice prior to any new terms taking effect. What constitutes a material change will be determined at Our sole discretion.\n" +
                                "By continuing to access or use Our Service after those revisions become effective, You agree to be bound by the revised terms. If You do not agree to the new terms, in whole or in part, please stop using the website and the Service.\n" +
                                "Contact Us\n" +
                                "If you have any questions about these Terms and Conditions, You can contact us:\n" +
                                "By email: projectCorned@gmail.com\n" +
                                "By phone number: 09946348988\n");
                                termsandconditionsAlert.setPositiveButton("Accept", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        buttonUserProfile.setEnabled(true);
                                        dialogInterface.dismiss();
                                    }
                                });
                                termsandconditionsAlert.setNegativeButton("Deny", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        termsandCond.setChecked(false);

                                    }
                                });

                                termsandCond.isShown();

                    }else{
                        buttonUserProfile.setEnabled(false);
                    }
            }
        });

    authorize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {


                privacypolicy.setTitle("Privacy Policy");
                privacypolicy.setMessage("Privacy Policy\n" +
                        "Last updated: April 19, 2023\n" +
                        "This Privacy Policy describes Our policies and procedures on the collection, use and disclosure of Your information when You use the Service and tells You about Your privacy rights and how the law protects You.\n" +
                        "We use Your Personal data to provide and improve the Service. By using the Service, You agree to the collection and use of information in accordance with this Privacy Policy. This Privacy Policy has been created with the help of the Free Privacy Policy Generator.\n" +
                        "Interpretation and Definitions\n" +
                        "Interpretation\n" +
                        "The words of which the initial letter is capitalized have meanings defined under the following conditions. The following definitions shall have the same meaning regardless of whether they appear in singular or in plural.\n" +
                        "Definitions\n" +
                        "For the purposes of this Privacy Policy:\n" +
                        "Account means a unique account created for You to access our Service or parts of our Service.\n" +
                        "Affiliate means an entity that controls, is controlled by or is under common control with a party, where \"control\" means ownership of 50% or more of the shares, equity interest or other securities entitled to vote for election of directors or other managing authority.\n" +
                        "Application refers to CERS, the software program provided by the Company.\n" +
                        "Company (referred to as either \"the Company\", \"We\", \"Us\" or \"Our\" in this Agreement) refers to CERS.\n" +
                        "Country refers to: Philippines\n" +
                        "Device means any device that can access the Service such as a computer, a cellphone or a digital tablet.\n" +
                        "Personal Data is any information that relates to an identified or identifiable individual.\n" +
                        "Service refers to the Application.\n" +
                        "Service Provider means any natural or legal person who processes the data on behalf of the Company. It refers to third-party companies or individuals employed by the Company to facilitate the Service, to provide the Service on behalf of the Company, to perform services related to the Service or to assist the Company in analyzing how the Service is used.\n" +
                        "Usage Data refers to data collected automatically, either generated by the use of the Service or from the Service infrastructure itself (for example, the duration of a page visit).\n" +
                        "You means the individual accessing or using the Service, or the company, or other legal entity on behalf of which such individual is accessing or using the Service, as applicable.\n" +
                        "Collecting and Using Your Personal Data\n" +
                        "Types of Data Collected\n" +
                        "Personal Data\n" +
                        "While using Our Service, We may ask You to provide Us with certain personally identifiable information that can be used to contact or identify You. Personally identifiable information may include, but is not limited to:\n" +
                        "Email address\n" +
                        "Phone number\n" +
                        "Usage Data\n" +
                        "Usage Data\n" +
                        "Usage Data is collected automatically when using the Service.\n" +
                        "Usage Data may include information such as Your Device's Internet Protocol address (e.g. IP address), browser type, browser version, the pages of our Service that You visit, the time and date of Your visit, the time spent on those pages, unique device identifiers and other diagnostic data.\n" +
                        "When You access the Service by or through a mobile device, We may collect certain information automatically, including, but not limited to, the type of mobile device You use, Your mobile device unique ID, the IP address of Your mobile device, Your mobile operating system, the type of mobile Internet browser You use, unique device identifiers and other diagnostic data.\n" +
                        "We may also collect information that Your browser sends whenever You visit our Service or when You access the Service by or through a mobile device.\n" +
                        "Information Collected while Using the Application\n" +
                        "While using Our Application, in order to provide features of Our Application, We may collect, with Your prior permission:\n" +
                        "•\tPictures and other information from your Device's camera and photo library\n" +
                        "We use this information to provide features of Our Service, to improve and customize Our Service. The information may be uploaded to the Company's servers and/or a Service Provider's server or it may be simply stored on Your device.\n" +
                        "You can enable or disable access to this information at any time, through Your Device settings.\n" +
                        "Use of Your Personal Data\n" +
                        "The Company may use Personal Data for the following purposes:\n" +
                        "To provide and maintain our Service, including to monitor the usage of our Service.\n" +
                        "To manage Your Account: to manage Your registration as a user of the Service. The Personal Data You provide can give You access to different functionalities of the Service that are available to You as a registered user.\n" +
                        "For the performance of a contract: the development, compliance and undertaking of the purchase contract for the products, items or services You have purchased or of any other contract with Us through the Service.\n" +
                        "To contact You: To contact You by email, telephone calls, SMS, or other equivalent forms of electronic communication, such as a mobile application's push notifications regarding updates or informative communications related to the functionalities, products or contracted services, including the security updates, when necessary or reasonable for their implementation.\n" +
                        "To provide You with news, special offers and general information about other goods, services and events which we offer that are similar to those that you have already purchased or enquired about unless You have opted not to receive such information.\n" +
                        "To manage Your requests: To attend and manage Your requests to Us.\n" +
                        "For business transfers: We may use Your information to evaluate or conduct a merger, divestiture, restructuring, reorganization, dissolution, or other sale or transfer of some or all of Our assets, whether as a going concern or as part of bankruptcy, liquidation, or similar proceeding, in which Personal Data held by Us about our Service users is among the assets transferred.\n" +
                        "For other purposes: We may use Your information for other purposes, such as data analysis, identifying usage trends, determining the effectiveness of our promotional campaigns and to evaluate and improve our Service, products, services, marketing and your experience.\n" +
                        "We may share Your personal information in the following situations:\n" +
                        "•\tWith Service Providers: We may share Your personal information with Service Providers to monitor and analyze the use of our Service, to contact You.\n" +
                        "•\tFor business transfers: We may share or transfer Your personal information in connection with, or during negotiations of, any merger, sale of Company assets, financing, or acquisition of all or a portion of Our business to another company.\n" +
                        "•\tWith Affiliates: We may share Your information with Our affiliates, in which case we will require those affiliates to honor this Privacy Policy. Affiliates include Our parent company and any other subsidiaries, joint venture partners or other companies that We control or that are under common control with Us.\n" +
                        "•\tWith business partners: We may share Your information with Our business partners to offer You certain products, services or promotions.\n" +
                        "•\tWith other users: when You share personal information or otherwise interact in the public areas with other users, such information may be viewed by all users and may be publicly distributed outside.\n" +
                        "•\tWith Your consent: We may disclose Your personal information for any other purpose with Your consent.\n" +
                        "Retention of Your Personal Data\n" +
                        "The Company will retain Your Personal Data only for as long as is necessary for the purposes set out in this Privacy Policy. We will retain and use Your Personal Data to the extent necessary to comply with our legal obligations (for example, if we are required to retain your data to comply with applicable laws), resolve disputes, and enforce our legal agreements and policies.\n" +
                        "The Company will also retain Usage Data for internal analysis purposes. Usage Data is generally retained for a shorter period of time, except when this data is used to strengthen the security or to improve the functionality of Our Service, or We are legally obligated to retain this data for longer time periods.\n" +
                        "Transfer of Your Personal Data\n" +
                        "Your information, including Personal Data, is processed at the Company's operating offices and in any other places where the parties involved in the processing are located. It means that this information may be transferred to — and maintained on — computers located outside of Your state, province, country or other governmental jurisdiction where the data protection laws may differ than those from Your jurisdiction.\n" +
                        "Your consent to this Privacy Policy followed by Your submission of such information represents Your agreement to that transfer.\n" +
                        "The Company will take all steps reasonably necessary to ensure that Your data is treated securely and in accordance with this Privacy Policy and no transfer of Your Personal Data will take place to an organization or a country unless there are adequate controls in place including the security of Your data and other personal information.\n" +
                        "Delete Your Personal Data\n" +
                        "You have the right to delete or request that We assist in deleting the Personal Data that We have collected about You.\n" +
                        "Our Service may give You the ability to delete certain information about You from within the Service.\n" +
                        "You may update, amend, or delete Your information at any time by signing in to Your Account, if you have one, and visiting the account settings section that allows you to manage Your personal information. You may also contact Us to request access to, correct, or delete any personal information that You have provided to Us.\n" +
                        "Please note, however, that We may need to retain certain information when we have a legal obligation or lawful basis to do so.\n" +
                        "Disclosure of Your Personal Data\n" +
                        "Business Transactions\n" +
                        "If the Company is involved in a merger, acquisition or asset sale, Your Personal Data may be transferred. We will provide notice before Your Personal Data is transferred and becomes subject to a different Privacy Policy.\n" +
                        "Law enforcement\n" +
                        "Under certain circumstances, the Company may be required to disclose Your Personal Data if required to do so by law or in response to valid requests by public authorities (e.g. a court or a government agency).\n" +
                        "Other legal requirements\n" +
                        "The Company may disclose Your Personal Data in the good faith belief that such action is necessary to:\n" +
                        "•\tComply with a legal obligation\n" +
                        "•\tProtect and defend the rights or property of the Company\n" +
                        "•\tPrevent or investigate possible wrongdoing in connection with the Service\n" +
                        "•\tProtect the personal safety of Users of the Service or the public\n" +
                        "•\tProtect against legal liability\n" +
                        "Security of Your Personal Data\n" +
                        "The security of Your Personal Data is important to Us, but remember that no method of transmission over the Internet, or method of electronic storage is 100% secure. While We strive to use commercially acceptable means to protect Your Personal Data, We cannot guarantee its absolute security.\n" +
                        "Children's Privacy\n" +
                        "Our Service does not address anyone under the age of 13. We do not knowingly collect personally identifiable information from anyone under the age of 13. If You are a parent or guardian and You are aware that Your child has provided Us with Personal Data, please contact Us. If We become aware that We have collected Personal Data from anyone under the age of 13 without verification of parental consent, We take steps to remove that information from Our servers.\n" +
                        "If We need to rely on consent as a legal basis for processing Your information and Your country requires consent from a parent, We may require Your parent's consent before We collect and use that information.\n" +
                        "Links to Other Websites\n" +
                        "Our Service may contain links to other websites that are not operated by Us. If You click on a third party link, You will be directed to that third party's site. We strongly advise You to review the Privacy Policy of every site You visit.\n" +
                        "We have no control over and assume no responsibility for the content, privacy policies or practices of any third party sites or services.\n" +
                        "Changes to this Privacy Policy\n" +
                        "We may update Our Privacy Policy from time to time. We will notify You of any changes by posting the new Privacy Policy on this page.\n" +
                        "We will let You know via email and/or a prominent notice on Our Service, prior to the change becoming effective and update the \"Last updated\" date at the top of this Privacy Policy.\n" +
                        "You are advised to review this Privacy Policy periodically for any changes. Changes to this Privacy Policy are effective when they are posted on this page.\n" +
                        "Contact Us\n" +
                        "If you have any questions about this Privacy Policy, You can contact us:\n" +
                        "•\tBy email: projectCorned@gmail.com\n");
                privacypolicy.setPositiveButton("Authorize", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        buttonUserProfile.setEnabled(true);
                        dialogInterface.dismiss();
                    }
                });
                privacypolicy.setNegativeButton("Deny", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        authorize.setChecked(false);
                    }
                });
                authorize.isShown();

            } else {
                buttonUserProfile.setEnabled(false);

            }
        }
         });
    }
}
