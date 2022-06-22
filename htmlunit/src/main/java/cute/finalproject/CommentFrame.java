package cute.finalproject;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommentFrame extends JFrame { //評論頁
    private  JLabel classLabel= new JLabel("課  名 :");
    private final JComboBox<String>  className; 
    private  JLabel teacherLabel= new JLabel("老師名 :");
    private final JTextField teacherName= new JTextField(15);
    private  JLabel scoreLabel= new JLabel("評分重點(是否看中出席/考試還是報告) :");
    private final JTextArea score;
    private  JLabel ruleLabel= new JLabel("上課模式 :");
    private final JTextArea rule;
    private  JLabel learnLabel= new JLabel("涼的程度(1-10分) :");
    private final JTextArea learn;
    private  JLabel hwLabel= new JLabel("作業方式(頻率/內容) :");
    private final JTextArea hw;
    private  JLabel commentLabel= new JLabel("其他補充心得:");
    private final JTextArea comment;

    private final JButton done=new JButton("完成");
    private final JButton back=new JButton("返回");
    private final JPanel myPanel=new JPanel(new GridLayout(6,1,2,1));
    private Course courses;
    private int type;
    private ImageIcon image,image1;
    private JLabel imgLabel = new JLabel();
    private JLabel imgLabel1 = new JLabel();
    private int TEACHER=1;

    public CommentFrame(Course course,int type0){//1 for teacher ,2 for  class 
        setTitle("留言表單");
        setLayout(null);
        courses=course;
        type=type0;
        getContentPane().setBackground(new Color(26, 25, 25));
        buildButton(back);
        back.addMouseListener(new MouseAdapter(){ //滑鼠進出事件
            public void mouseEntered(MouseEvent e){back.setBackground(Color.red);back.setForeground(Color.white);}
            public void mouseExited(MouseEvent e){back.setBackground(Color.white);back.setForeground(Color.black);}
         });
        buildButton(done);
        done.addMouseListener(new MouseAdapter(){ //滑鼠進出事件
            public void mouseEntered(MouseEvent e){done.setBackground(Color.red);done.setForeground(Color.white);}
            public void mouseExited(MouseEvent e){done.setBackground(Color.white);done.setForeground(Color.black);}
        });
        
        JLabel icon=new JLabel("C");
        icon.setFont(new Font("Brush Script MT",Font.BOLD, 30));
        icon.setForeground(Color.red);
        icon.setBounds(10, 10,30,30);
        add(icon);

        className = new JComboBox<>(); //點老師進入可選擇課堂
        className.addItem(" "); 
        className.setForeground(Color.black);
        try {
            for(String s:JsonHandler.get(course.getProfessor())){//加入下拉式清單
                className.addItem(s);
                if(s.equals(course.getClassName())) {//讓預設是點進來的課名
                    className.setSelectedItem(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(type0==TEACHER)//讓預設是空白
            className.setSelectedItem(" ");
        else{
            className.setEnabled(false);
        className.setForeground(Color.black);}

        teacherName.setText(course.getProfessor()); //預設字
        teacherName.setEditable(false);

        score=buildField();        
        rule=buildField();  
        learn=buildField();  
        hw=buildField();  
        comment=buildField();  

        JScrollPane jsp5=new JScrollPane(comment,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        myPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Comment",TitledBorder.CENTER,TitledBorder.TOP,new Font("Segoe Print",Font.PLAIN,20), Color.white));
        myPanel.setLayout(new GridLayout(14,1));
        myPanel.setBounds(35, 35, 400, 500);
        myPanel.setBackground(new Color(70,70,70));//250,219,255

        classLabel.setForeground(Color.white);
        teacherLabel.setForeground(Color.white);
        scoreLabel.setForeground(Color.white);
        learnLabel.setForeground(Color.white);
        hwLabel.setForeground(Color.white);
        ruleLabel.setForeground(Color.white);
        commentLabel.setForeground(Color.white);

        myPanel.add(classLabel);
        myPanel.add(className);
        myPanel.add(teacherLabel);
        myPanel.add(teacherName);
        myPanel.add(scoreLabel);
        myPanel.add(score);
        myPanel.add(learnLabel);
        myPanel.add(learn);
        myPanel.add(hwLabel);
        myPanel.add(hw);
        myPanel.add(ruleLabel);
        myPanel.add(rule);
        myPanel.add(commentLabel);
        myPanel.add(jsp5);

        done.setBounds(600, 495, 100, 40);
        back.setBounds(500, 495, 100, 40);
        add(myPanel);
        add(done);
        add(back);

        image = new ImageIcon("picc.gif");
        image.setImage(image.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
        imgLabel.setIcon(image);
        imgLabel.setBounds(500,200,500,300);
        add(imgLabel);

        image1 = new ImageIcon("name.gif");
        image1.setImage(image1.getImage().getScaledInstance(300, 100, Image.SCALE_DEFAULT));
        imgLabel1.setIcon(image1);
        imgLabel1.setBounds(500,100,700,100);
        add(imgLabel1);
        
        ButtonHandler handler = new ButtonHandler(); //觸發button
        done.addActionListener(handler);
        back.addActionListener(handler);
    }
    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            InformationJsonHandler handler = new InformationJsonHandler();
            try {
                handler.initialize();
            } catch (IOException | URISyntaxException e2) {
                e2.printStackTrace();
            }
            if(e.getSource()==done){
                //存入
                Sheet sheet=new Sheet();
                try {
                    if(check()){//確認有無空字串
                        sheet.doit();//抓到SHEET，下面寫入
                        sheet.writer(sheet.getService(), sheet.getId(),className.getSelectedItem().toString(),teacherName.getText(),score.getText(),rule.getText(),learn.getText(),hw.getText(),comment.getText());
                        dispose();
                        try {
                            CommentPage myFrame = new CommentPage(courses,type);
                            myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            myFrame.setSize(900, 600);
                            myFrame.setVisible(true); // display frame   
                        } catch (IOException | GeneralSecurityException e1) {
                            e1.printStackTrace();
                        }    
                    }
                    else{
                        JOptionPane.showMessageDialog(CommentFrame.this, "無法提交空留言!", "Warning!", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (IOException | GeneralSecurityException e1) {
                    e1.printStackTrace();
                }

            }
            else if(e.getSource()==back){
                dispose(); 
                try {
                    CommentPage myFrame = new CommentPage(courses,type);
                    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    myFrame.setSize(900, 600);
                    myFrame.setVisible(true); // display frame   
                } catch (IOException | GeneralSecurityException e1) {
                    e1.printStackTrace();
                }    
            }
            
        }
    }
 private Boolean check() { //確認有無留言//先讓所有格內預設是空格，表單才不會NULL
        int i=0;
        if(score.getText().trim().equals("") && score != null){score.setText(" ");i++;}
        if(rule.getText().trim().equals("") && rule != null){rule.setText(" ");i++;}
        if(hw.getText().trim().equals("") && hw != null){hw.setText(" ");i++;}
        if(learn.getText().trim().equals("") && learn != null){learn.setText(" ");i++;}
        if(comment.getText().trim().equals("") && comment != null){comment.setText(" ");i++;}
        if(i==5)return false;
        return true;
    }
    private JTextArea buildField() {
        JTextArea field = new JTextArea(2,50);
        field.setText(" ");
        field.setLineWrap(true);        //自動換行
        field.setWrapStyleWord(true);   // 斷行不斷字功能
        return field;
    }
    private void buildButton(JButton button){
        button.setForeground(Color.black);
        button.setBackground(Color.white);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(136, 136, 136)));//灰色
    }
}