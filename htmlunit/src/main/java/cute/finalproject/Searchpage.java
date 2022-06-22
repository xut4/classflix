package cute.finalproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import javax.swing.*;


import java.io.*;
import java.util.*;
import javax.swing.event.ListSelectionEvent;

import java.util.List;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

public class Searchpage extends JFrame{
   private  JTextField textClass;
   private JTextField textTeacher;
   private JButton classBtn;
   private JButton teacherBtn;
   private JPanel panelClass= new JPanel();
   private JPanel panelTeacher= new JPanel();
   private JPanel searchClass= new JPanel(new FlowLayout());
   private JPanel searchTeacher= new JPanel(new FlowLayout());
   private final ArrayList<String> dic = new ArrayList<>();//teacher
   private final ArrayList<String> dic2 = new ArrayList<>();//class
   private final ArrayList<String> addWordToSuggestions = new ArrayList<>();//teacher
   private final ArrayList<String> addWordToSuggestions2 = new ArrayList<>();//class

   private JList teacher;
   private JList classes;
   private DefaultListModel<String> listModel;
   private DefaultListModel<String> listModel2;
   private ArrayList<Course> a,b= new ArrayList<Course>();
   
   private int CLASS=2,TEACHER=1;
   public Searchpage(){
      super("CLASSFLIX");
      this.setLayout(null);
      Color bkg = new Color(26,25,25);
      getContentPane().setBackground(bkg);
      JLabel icon=new JLabel("C");
      icon.setFont(new Font("Brush Script MT",Font.BOLD, 30)); 
      icon.setForeground(Color.red);
      icon.setBounds(10, 10,30,30); //logo位置大小
      add(icon);

      panelClass.setLayout(new BoxLayout(panelClass, BoxLayout.Y_AXIS));//class
      panelClass.setBounds(70, 30,350,500);

      classBtn=new JButton("search");
      classBtn.setForeground(Color.white);
      classBtn.setBackground(Color.red); 
      classBtn.setBorder(new LineBorder(new Color(136, 136, 136)));//灰色
   
      textClass=new JTextField("輸入課程名稱");
      textClass.setForeground(Color.white);
      textClass.setColumns(25);
      textClass.setBackground(new Color(70, 70, 70));//深灰
      
      searchClass.add(textClass);
      searchClass.add(classBtn,BorderLayout.LINE_END);
      searchClass.setBackground(bkg);
      panelClass.add(searchClass);
       
      teacherBtn=new JButton("search");
      teacherBtn.setForeground(Color.white);
      teacherBtn.setBackground(Color.red); 
      teacherBtn.setBorder(new LineBorder(new Color(136, 136, 136)));//灰色
     
      textTeacher=new JTextField("輸入老師姓名");
      textTeacher.setForeground(Color.white);
      textTeacher.setColumns(25);
      textTeacher.setBackground(new Color(70, 70, 70));

      panelTeacher.setLayout(new BoxLayout(panelTeacher, BoxLayout.Y_AXIS));//teacher
      panelTeacher.setBounds(470, 30,350,500);
      panelTeacher.setBackground(bkg);
      searchTeacher.add(textTeacher);
      searchTeacher.add(teacherBtn,BorderLayout.LINE_END);
      searchTeacher.setBackground(bkg);
      panelTeacher.add(searchTeacher);

      listModel=new DefaultListModel<>();//teacher
      listModel2=new DefaultListModel<>();//classes
      try {
         a=JsonHandler.getList(TEACHER);//teacher
         b=JsonHandler.getList(CLASS);//class
         ArrayList<String> tt= new ArrayList<>();//teacher的資料
         ArrayList<String> cc = new ArrayList<>();//classes的資料
         for(Course x: a) //加入老師到list
         {
            listModel.addElement(x.getProfessor());
            tt.add(x.getProfessor());
         }
         for(Course x: b) //加入課名到list
         {
            listModel2.addElement(x.getClassName());
            cc.add(x.getClassName());
         }        
         teacher=new JList<>(listModel);
         classes=new JList<>(listModel2);
         teacher.setForeground(Color.white);
         classes.setForeground(Color.white);
         teacher.setBackground(new Color(70,70,70));
         classes.setBackground(new Color(70,70,70));

         setDictionary(tt,dic);//設定基本資料(教授)
         setDictionary(cc, dic2);//設定基本資料(課堂)
         ListSelectionListener listener =new ListSelectionListener(); //觸發list
         classes.addListSelectionListener(listener);
         teacher.addListSelectionListener(listener);

         JScrollPane scrollPane = new JScrollPane(teacher,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         JScrollPane scrollPane2 = new JScrollPane(classes,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         
         scrollPane.setPreferredSize(new Dimension(355,450));
         scrollPane2.setPreferredSize(new Dimension(355,450));

         panelClass.add(scrollPane2);
         panelTeacher.add(scrollPane);
         panelClass.setFont(new Font("Segoe Print",Font.PLAIN, 15));
         panelTeacher.setFont(new Font("Segoe Print",Font.PLAIN, 15));
         add(panelClass);
         add(panelTeacher);  
      } catch (IOException e1) {
         e1.printStackTrace();
      }
      ButtonHandler handler = new ButtonHandler(); //按鈕觸發
      classBtn.addActionListener(handler);
      teacherBtn.addActionListener(handler);
    }

    private class ListSelectionListener implements javax.swing.event.ListSelectionListener {
      public void valueChanged(ListSelectionEvent e) {
         if (!e.getValueIsAdjusting()) {
            JList source = (JList)e.getSource();
            String selected = source.getSelectedValue().toString(); //選到的
            InformationJsonHandler handler = new InformationJsonHandler();
            try {
               handler.initialize();
               dispose();
               if(e.getSource().equals(classes)){ //class跳到資訊頁
                  List<Course> filteredClinicList = handler.findCourses("", selected);
                  Information information = new Information(filteredClinicList.get(0));
                  information.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  information.setSize(900, 600); //設大小
                  information.setVisible(true); 
               }
               else if(e.getSource().equals(teacher)){ //teacher跳到留言頁
                  List<Course> filteredClinicList = handler.findCourses(selected, "");
                  CommentPage myFrame;
                  try {
                     myFrame = new CommentPage(filteredClinicList.get(0),TEACHER);
                     myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                     myFrame.setSize(900, 600);
                     myFrame.setVisible(true); // display frame
                  } catch (GeneralSecurityException e1) {
                     e1.printStackTrace();
                  }
               }
               else{ //搜尋後
                  for(int i=0;i<teacher.getModel().getSize();i++){ //teacher
                     if(teacher.getModel().getElementAt(i).equals(selected)){
                        List<Course> filteredClinicList = handler.findCourses(selected, "");
                        CommentPage myFrame;
                        try {
                           myFrame = new CommentPage(filteredClinicList.get(0),TEACHER);
                           myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                           myFrame.setSize(900, 600);
                           myFrame.setVisible(true); // display frame
                        } catch (GeneralSecurityException e1) {
                           e1.printStackTrace();
                        }
                     }
                  }
                  for(int i=0;i<classes.getModel().getSize();i++){ //class
                     if(classes.getModel().getElementAt(i).equals(selected)){
                        List<Course> filteredClinicList = handler.findCourses("", selected);
                        Information information = new Information(filteredClinicList.get(0));
                        information.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        information.setSize(900, 600); //設大小
                        information.setVisible(true); 
                     }
                  }
               }
            } catch (IOException | URISyntaxException e1) {
               e1.printStackTrace();
            }
         }
      }
   }

    private class ButtonHandler implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent event) {
          if (event.getSource() == teacherBtn) { //teacher
             try {
                boolean suggestionAdded = false;//資料填寫判斷
                listModel.removeAllElements(); //list清空
 
                suggestionAdded=wordTyped(textTeacher.getText(),dic, addWordToSuggestions);
                if(suggestionAdded==true) //有資料
               {
                  for(String x:addWordToSuggestions)
                  {
                     listModel.addElement(x); //加到list
                     teacher=new JList<>(listModel);
                  }
               }
               else //沒資料   
               {
                   listModel.removeAllElements();
                   for(Course y: a) //加入所有老師
                   {
                      listModel.addElement(y.getProfessor());
                   }
                   teacher=new JList<>(listModel);
                   //錯誤訊息
                   JOptionPane.showMessageDialog(Searchpage.this,"沒有相符合的教授", "警示",JOptionPane.ERROR_MESSAGE);
                }
             } catch (Exception e) {
                e.printStackTrace();
             }
          }
          else if (event.getSource() == classBtn) { //class
             try {
                boolean suggestionAdded = false;//資料填寫判斷
                listModel2.removeAllElements();
 
                suggestionAdded=wordTyped(textClass.getText(),dic2, addWordToSuggestions2);
                if(suggestionAdded==true) //有結果
               {
                  for(String x:addWordToSuggestions2)
                  {
                     listModel2.addElement(x); //加入
                     classes=new JList<>(listModel2);
                  }
               }
               else//沒結果
               {
                  for(Course y: b)
                  {
                     listModel2.addElement(y.getClassName());
                  }
                  classes=new JList<>(listModel2);
                  //錯誤訊息
                  JOptionPane.showMessageDialog(Searchpage.this,"沒有相符合的課程", "警示",JOptionPane.ERROR_MESSAGE);
               }
            } catch (Exception e) {
                e.printStackTrace();
             }
          }
       }
    }
   //auto-need
   public void setDictionary(ArrayList<String> words,ArrayList<String> dictionary) {
      dictionary.clear();
      if (words == null) {
          return;//so we can call constructor with null value for dictionary without exception thrown
      }
      for (String word : words) {
          dictionary.add(word);
      }
  }

          
   public boolean wordTyped(String typedWord,ArrayList<String> dic,ArrayList<String> addWordToSuggestion) {//核對抓text//dic 資料庫//符合的
      if (typedWord.isEmpty()) {
         return false;
      }
      boolean suggestionAdded = false;//判斷有沒有符合資料
      addWordToSuggestion.clear();
      for (String word : dic) {//get words in the dictionary which we added
         boolean fullymatches = false;
         fullymatches = CompareUtil.compareStringWithHanZi(typedWord, word);// 如果一樣回傳true
         if (fullymatches) {
            addWordToSuggestion.add(word);
            suggestionAdded = true;
         }
      }
      return suggestionAdded;
   }
}