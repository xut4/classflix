package cute.finalproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Information extends JFrame{
    private JLabel classNameLable;
    private JLabel teacherLable;
    private JLabel codeLable;
    private JLabel isEnglishLable;
    private JTextArea teachingMethodLable;
    private JTextArea evaluationLable;
    private JPanel panelNum= new JPanel(new BorderLayout());
    private JPanel panelEng= new JPanel(new BorderLayout());
    private JPanel panelTeach= new JPanel(new BorderLayout());
    private JPanel panelScore= new JPanel(new BorderLayout());
    private JPanel overView= new JPanel();
    private JButton messageBtn;
    private JButton back;
    private Course courses;
    private JPanel btnPanel;
    
    public Information(Course course) { //資訊頁
        super(course.getClassName()+"    "+course.getProfessor()); 
        courses=course;
        setLayout(null); 
        Container cp=this.getContentPane();
        JLabel icon=new JLabel("C");
        icon.setFont(new Font("Brush Script MT",Font.BOLD, 30));
        icon.setForeground(Color.red);
        icon.setBounds(10,10,30,30);
        add(icon);

        overView.setLayout(new BoxLayout(overView, BoxLayout.Y_AXIS));
        overView.setBackground(new Color(26,25,25));
        cp.setLayout(new BorderLayout());
        JScrollPane jsp=new JScrollPane();
        //課名
        classNameLable = new JLabel(course.getClassName());        
        classNameLable.setFont(new Font("微软雅黑", Font.BOLD, 30));
        classNameLable.setAlignmentX(Component.CENTER_ALIGNMENT);
        classNameLable.setForeground(Color.white);
        overView.add(classNameLable);
        //老師
        teacherLable = new JLabel("老師 : " + course.getProfessor());
        teacherLable.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        teacherLable.setAlignmentX(Component.CENTER_ALIGNMENT);
        teacherLable.setForeground(Color.white);
        overView.add(teacherLable);
        //代碼
        setmyBorder(panelNum,"課號");
        codeLable = new JLabel(course.getClassCode());
        codeLable.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        codeLable.setForeground(Color.white);
        panelNum.add(codeLable);
        overView.add(panelNum);
        //使用英文
        setmyBorder(panelEng,"使用英文");
        isEnglishLable = new JLabel(course.getIsEnglish());
        isEnglishLable.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        isEnglishLable.setForeground(Color.white);
        panelEng.add(isEnglishLable);
        overView.add(panelEng);

        //教學方式
        setmyBorder(panelTeach,"教學方式");
        teachingMethodLable = new JTextArea( course.getTeachingMethod(), Font.PLAIN, 20);
        teachingMethodLable.setLineWrap(true);        //激活自动换行功能 
        teachingMethodLable.setWrapStyleWord(true);            // 激活断行不断字功能
        teachingMethodLable.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        teachingMethodLable.setBackground(new Color(70,70,70));
        teachingMethodLable.setForeground(Color.white);
        panelTeach.add(teachingMethodLable);
        overView.add(panelTeach);

        //評量方式
        setmyBorder(panelScore,"評量方式");
        evaluationLable = new JTextArea( course.getEvaluation());
        evaluationLable.setLineWrap(true);        //激活自动换行功能 
        evaluationLable.setWrapStyleWord(true);            // 激活断行不断字功能
        evaluationLable.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        evaluationLable.setBackground(new Color(70,70,70));
        evaluationLable.setForeground(Color.white);
        panelScore.add(evaluationLable);
        overView.add(panelScore);

        back = new JButton("返回");
        back.setFont(new Font("微软雅黑",Font.PLAIN, 14));
        back.setBorder(new LineBorder(new Color(136, 136, 136)));//灰色
        back.addMouseListener(new MouseAdapter(){//滑鼠進出事件
            public void mouseEntered(MouseEvent e){back.setBackground(Color.red);back.setForeground(Color.white);}
            public void mouseExited(MouseEvent e){back.setBackground(Color.white);back.setForeground(Color.black);}
         });
        messageBtn = new JButton("評價");
        messageBtn.setFont(new Font("微软雅黑",Font.PLAIN, 14));
        messageBtn.setBorder(new LineBorder(new Color(136, 136, 136)));//灰色
        messageBtn.addMouseListener(new MouseAdapter(){ //滑鼠進出事件
            public void mouseEntered(MouseEvent e){messageBtn.setBackground(Color.red);messageBtn.setForeground(Color.white);}
            public void mouseExited(MouseEvent e){messageBtn.setBackground(Color.white);messageBtn.setForeground(Color.black);}
          });
         

        jsp.setViewportView(overView);
        jsp.getVerticalScrollBar().setUnitIncrement(15); //scroll速度
        cp.add(jsp,BorderLayout.CENTER);
        btnPanel = new JPanel();
        btnPanel.setBackground(new Color(26,25,25));
        btnPanel.add(back);
        btnPanel.add(messageBtn);
        cp.add(btnPanel, BorderLayout.SOUTH);

        ButtonHandler handler = new ButtonHandler();
        messageBtn.addActionListener(handler);
        back.addActionListener(handler);
    }
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==messageBtn){
                dispose();                
                try {
                    CommentPage myFrame = new CommentPage(courses,2);
                    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    myFrame.setSize(900, 600);
                    myFrame.setVisible(true); // display frame   
                } catch (IOException | GeneralSecurityException e1) {
                    e1.printStackTrace();
                }             
            }
            else{
                dispose();
                Searchpage page1 = new Searchpage();
                page1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
                page1.setSize(900, 600); // set frame size
		        page1.setVisible(true); // display frame    
            }
        }
    }
	private void setmyBorder(JPanel panel,String str){
        Color red=new Color(250, 1, 1);
        panel.setBorder(BorderFactory.createTitledBorder(null,str,TitledBorder.LEFT, TitledBorder.TOP, new Font("微软雅黑",Font.BOLD,25),red));
        panel.setBackground(new Color(70,70,70));
    }
}