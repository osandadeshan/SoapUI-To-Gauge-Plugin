# HTML Report Generation for SoapUI XML Execution
New framework to execute SoapUI XML from Gauge Framework and generate a detailed HTML Report



Pre Requirements:

1)	IntelliJ IDEA Community Edition.
2)	Gauge and Java Language Runner. 
3)	HTML Report Plugin (Please refer “Installing Gauge and IntelliJ IDEA Community Edition” documentation for further knowledge)
4)	Apache Maven
5)	SoapUI Free Version (v4.5.1 or above)



Getting Started

1)	Open IntelliJ IDEA Community Edition and Click “Create New Project”
2)	Select “Gauge” and Click “Next” button
3)	Enter a name for the project and Click “Finish” button
4)	Download this RAR from here (https://drive.google.com/file/d/0BxipNkUcDIYFaEpJU09lVzJ4bTA/view?usp=sharing)
5)	Extract it
6)	Please verify you have following folders in the extracted folder
    env, ext, pom
7)	Copy these two folders and pom.xml file
8)	Open your project in “Windows Explorer”
9)	Go inside the project “TestProject”
10)	Paste the above files into this location (Confirm to replace files)
11)	Go to IntelliJ IDEA IDE
12)	Right Click on pom.xml and click “Add as Maven Project”
13)	Click “Recreate” button
14)	Again Right Click on pom.xml and click “Add as Maven Project” 
15)	Right Click on “TestProject” and click “Open Module Settings”
16)	Select “Libraries” from the left section
17)	Click on the “+” from the top of the window and click “Java”
18)	Browse “ext” folder inside your project and select “soapui-5.3.0” JAR file
19)	Click Ok
20)	Click Ok
21)	Similar to that, add “mysql-connector-java-5.1.37” and “executesoapui” JARs inside the “ext” folder one by one
22)	Then add “libs” folder inside the “ext” folder
23)	Click “Apply” button
24)	Click “Ok” button
25)	Expand “src” folder on “TestProject” and delete the “StepImplementation.Java” file
26)	Right click on “java” folder and add a new Java class
27)	In that Java file, add following code snippet
            import com.osanda.ExecuteSoapUIAnyTestCase;
            import com.thoughtworks.gauge.Step;

            /**
             * Created by Osanda on 3/20/2017.
             */
            public class Test {
                @Step("Execute <testCase> of <testSuite>")
                public void testm(String testCase, String testSuite) throws Exception {     
                    ExecuteSoapUIAnyTestCase executeSoapUIAnyTestCase = new   ExecuteSoapUIAnyTestCase();
                    executeSoapUIAnyTestCase.executeSpecificTestCase(testCase, testSuite);
                }
            }

28)	Expand “env” folder on “TestProject” and open “default.properties” file
29)	Change the “SOAPUI_XML_PATH” property value into the location of your SoapUI XML file
30)	Expand “specs” folder on “TestProject” and delete the “example.spec” file
31)	Right click on “spec” folder and add a new Specification
32)	In that Specification file, add following lines
            Specification Heading
            =====================
            Created by Osanda on 3/20/2017

            This is an executable specification file which follows markdown syntax.
            Every heading in this file denotes a scenario. Every bulleted point denotes a step.

            Scenario Heading
            ----------------
            * Execute "Login Existing User With Correct Username & Empty Password TestCase" of "LoginTestSuite"

33)	Right click on the specification and click “Run ‘your_spec.spec’”
34)	It will generate a HTML report for your TestCase execution in the "reports" folder of “TestProject”





Date Created	: 03/20/2017
Version         : 1.0.0
Owner           : Osanda Deshan <osanda.deshan@gmail.com>
Description     : HTML Report Generation for SoapUI XML Execution
