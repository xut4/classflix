package cute.finalproject;
import com.gargoylesoftware.htmlunit.WebClient;

import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import com.google.gson.Gson;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.DomElement;

public class Crawler { //爬取教務系統資料
    private static ArrayList<Course> courses = new ArrayList<>();
    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "jssecacerts"); //解決SSL問題
        try{
            WebClient webClient = new WebClient(BrowserVersion.CHROME); //模擬建立開啟一個谷歌瀏覽器視窗
            webClient.getOptions().setCssEnabled(false); //HtmlUnit對CSS的支援不好，關閉之
			webClient.getOptions().setJavaScriptEnabled(true); //HtmlUnit對JavaScript的支援
            webClient.getOptions().setThrowExceptionOnScriptError(false); //是否丟擲response的錯誤
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());

            HtmlPage page = webClient.getPage("https://ais.ntou.edu.tw/Default.aspx");
            HtmlForm login = (HtmlForm)page.getElementById("form1");
            HtmlSubmitInput button = login.getInputByName("LGOIN_BTN");
            HtmlTextInput userName = login.getInputByName("M_PORTAL_LOGIN_ACNT");
            userName.setValueAttribute("00957123");
            HtmlPasswordInput passWord = login.getInputByName("M_PW");
            passWord.setValueAttribute("trista910225");
            HtmlPage mainFrame = button.click(); //登入

            HtmlPage frame = (HtmlPage)mainFrame.getFrameByName("menuFrame").getEnclosedPage(); //左邊主選單
            HtmlAnchor anchor = (HtmlAnchor)frame.getElementById("Menu_TreeViewn1"); //教務系統
            HtmlPage click = (HtmlPage)anchor.click();
            frame = (HtmlPage)click.getFrameByName("menuFrame").getEnclosedPage();

            anchor = (HtmlAnchor)frame.getElementById("Menu_TreeViewt29"); //選課系統
            click = (HtmlPage)anchor.click();
            frame = (HtmlPage)click.getFrameByName("menuFrame").getEnclosedPage();

            anchor = (HtmlAnchor)frame.getElementById("Menu_TreeViewt38"); //課程課表查詢
            anchor.click();
            frame = (HtmlPage)mainFrame.getFrameByName("mainFrame").getEnclosedPage();
            
            HtmlSelect select = (HtmlSelect) frame.getElementById("Q_FACULTY_CODE"); //開課系所
            HtmlOption option = select.getOptionByValue("090M"); //090M-共同教育中心博雅教育組
            select.setSelectedAttribute(option, true);
            button = (HtmlSubmitInput)frame.getElementById("QUERY_BTN1"); //開課單位查詢
            click = button.click();
            frame = (HtmlPage)click.getFrameByName("mainFrame").getEnclosedPage();
            
            HtmlTextInput pageInput = (HtmlTextInput)frame.getElementById("PC_PageSize");
            pageInput.setValueAttribute("150"); //將一頁筆數設為150筆
            HtmlButtonInput pageButton = (HtmlButtonInput)frame.getElementById("PC_ShowRows"); //筆的按鈕
            click = pageButton.click();
            frame = (HtmlPage)mainFrame.getFrameByName("mainFrame").getEnclosedPage();

            DomElement totalElement = frame.getFirstByXPath("//span[@id='PC2_TotalRow']"); //總共幾筆
            String total = totalElement.getTextContent(); 
            int totalInt = Integer.parseInt(total);
            totalInt += 2;
            for(int i = 2; i < totalInt; i++){
                if(i < 10) {
                    HtmlAnchor classNumber = (HtmlAnchor)frame.getElementById("DataGrid_ctl0" + i + "_COSID"); //課號
                    click = classNumber.click();
                    frame = (HtmlPage)click.getFrameByName("mainFrame").getEnclosedPage();
                }
                else {
                    HtmlAnchor classNumber = (HtmlAnchor)frame.getElementById("DataGrid_ctl" + i + "_COSID"); //課號
                    click = classNumber.click();
                    frame = (HtmlPage)click.getFrameByName("mainFrame").getEnclosedPage();
                }
                DomElement elementClassCode = frame.getFirstByXPath("//span[@id='M_COSID']"); //課程代碼
                String  classCode = elementClassCode.getTextContent();

                DomElement elementProfessor = frame.getFirstByXPath("//span[@id='M_LECTR_TCH_CH']"); //教授
                String professor = elementProfessor.getTextContent();

                DomElement elementClassName = frame.getFirstByXPath("//span[@id='CH_LESSON']"); //課名
                String  className = elementClassName.getTextContent();

                DomElement elementEnglishClassName = frame.getFirstByXPath("//span[@id='M_ENG_LESSON']"); //英文課名
                String englishClassName = elementEnglishClassName.getTextContent();

                DomElement elementTime = frame.getFirstByXPath("//span[@id='M_SEG']"); //上課時間
                String time = elementTime.getTextContent();

                DomElement elementIsEnglish = frame.getFirstByXPath("//span[@id='M_COS_ENGLISH_FG']"); //是否使用英文
                String isEnglish = elementIsEnglish.getTextContent();

                DomElement elementTeachingMethod = frame.getFirstByXPath("//span[@id='M_CH_TEACH']"); //教學方式
                String teachingMethod = elementTeachingMethod.getTextContent();

                DomElement elementEvaluation = frame.getFirstByXPath("//span[@id='M_CH_TYPE']"); //評量方式
                String evaluation = elementEvaluation.getTextContent();

                HtmlButtonInput close = (HtmlButtonInput)frame.getElementById("Button1"); //關閉按鈕
                close.click();
                frame = (HtmlPage)mainFrame.getFrameByName("mainFrame").getEnclosedPage();

                Course course = new Course(classCode, professor, className, englishClassName, time, isEnglish, teachingMethod, evaluation);
                courses.add(course);
            }
            String json = new Gson().toJson(courses);
            String path = "C:\\Users\\elain\\java\\java_finalproject\\courses.json"; //資料存進json位址
            File file = new File(path);
            file.createNewFile();
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(json);
            write.flush();
            write.close();
            webClient.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}