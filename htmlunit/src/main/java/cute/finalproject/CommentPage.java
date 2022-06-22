package cute.finalproject;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class CommentPage extends JFrame{ //留言頁
    private final JButton doComment=new JButton("留言去!");
    private final JButton back=new JButton("返回");
    private JPanel overView= new JPanel();
    private JLabel label= new JLabel("尚無留言");
    private Course courses;
    private int type;
    private JPanel btnPanel = new JPanel();
    private JPanel panell;
    private int CLASS=2,TEACHER=1;
    
    public CommentPage(Course course,int type0) throws IOException, GeneralSecurityException{
        if(type0==CLASS)
            setTitle(course.getClassName()+"   留言板");
        if(type0==TEACHER)
            setTitle(course.getProfessor()+"   留言板");
        setLayout(null); 
        courses=course;
        type=type0;
        label.setForeground(Color.white);
        back.setForeground(Color.black);back.setBackground(Color.white); back.setFocusPainted(false);
        back.setBorder(new LineBorder(new Color(136, 136, 136)));//灰色
        back.addMouseListener(new MouseAdapter(){//滑鼠進出事件
            public void mouseEntered(MouseEvent e){back.setBackground(Color.red);back.setForeground(Color.white);}
            public void mouseExited(MouseEvent e){back.setBackground(Color.white);back.setForeground(Color.black);}
         });
        doComment.setForeground(Color.black);doComment.setBackground(Color.white); doComment.setFocusPainted(false);
        doComment.setBorder(new LineBorder(new Color(136, 136, 136)));//灰色
        doComment.addMouseListener(new MouseAdapter(){//滑鼠進出事件
            public void mouseEntered(MouseEvent e){doComment.setBackground(Color.red);doComment.setForeground(Color.white);}
            public void mouseExited(MouseEvent e){doComment.setBackground(Color.white);doComment.setForeground(Color.black);}
        });
        Container cp=this.getContentPane();
        Color YYYY=new Color(26,25,25);
        overView.setLayout(new BoxLayout(overView, BoxLayout.Y_AXIS));
        overView.setBackground(YYYY);
        cp.setLayout(new BorderLayout());
        
        JLabel icon=new JLabel("C");
        icon.setFont(new Font("Brush Script MT",Font.BOLD, 30));
        icon.setForeground(Color.red);
        cp.setBackground(YYYY);
        icon.setBounds(10, 10,30,30);
        //add(icon);

        Sheet sheet=new Sheet();
        List<List<Object>> commentsList;
        sheet.doit();
        commentsList=sheet.reader(sheet.getService(), sheet.getId());

        if (commentsList == null || commentsList.isEmpty()) { 
            System.out.println("No data found.");
        } 
        else {
            int ifnull=0;
            for(List<Object> object:commentsList){
                ArrayList<String>  comment=new ArrayList<>();
                for(Object list:object){
                    comment.add(list.toString());
                }
                Boolean same=false;
                switch (type){
                    case 2:
                        if(comment.get(0).equals(course.getClassName()))same=true;
                        break;
                    case 1:
                        if(comment.get(1).equals(course.getProfessor()))same=true;
                        break;
                }
                if(same){
                    ifnull++;
                    CommentView view=new CommentView(comment.get(0), comment.get(1), comment.get(2), comment.get(3), comment.get(4), comment.get(5), comment.get(6));
                    view.setBackground(new Color(26,25,25));
                    view.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                    overView.add(view);
                }
            }
            if(ifnull==0){ //沒有留言
                label.setFont(new Font("微软雅黑", Font.BOLD, 50));
                panell = new JPanel();
                panell.setBackground(YYYY);
                panell.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
                panell.add(label);
                overView.add(panell,BorderLayout.CENTER);
            }
        }
        JScrollPane jsp=new JScrollPane(overView);
        jsp.getVerticalScrollBar().setUnitIncrement(15);//卷軸速度
        cp.add(jsp,BorderLayout.CENTER);
        btnPanel.add(back);
        btnPanel.add(doComment);
        btnPanel.setBackground(YYYY);
        cp.add(icon,BorderLayout.NORTH);
        cp.add(btnPanel, BorderLayout.SOUTH);
        ButtonHandler handler = new ButtonHandler(); //觸發button
        doComment.addActionListener(handler);
        back.addActionListener(handler);
    }
    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==doComment){
                dispose();                
                CommentFrame myFrame = new CommentFrame(courses,type);
                myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                myFrame.setSize(900, 600);
                myFrame.setVisible(true); // display frame  

            }
            else{
                if(type==TEACHER){
                    dispose();
                    Searchpage page1 = new Searchpage();
                    page1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
                    page1.setSize(900, 600); // set frame size
                    page1.setVisible(true); // display frame   
                }
                else{
                    dispose();
                    Information information = new Information(courses);
                    information.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    information.setSize(900, 600); //設大小
                    information.setVisible(true); 
                }
            }

        }
    }
    
}