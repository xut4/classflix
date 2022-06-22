package cute.finalproject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;


public class CommentView extends JPanel {
	private static final String space = "    ";

	private final JPanel classPanel= new JPanel(new BorderLayout()); 
	private final JPanel teacherPanel= new JPanel(new BorderLayout()); 
	private final JPanel scorePanel= new JPanel(new BorderLayout()); 
	private final JPanel rulePanel= new JPanel(new BorderLayout()); 
	private final JPanel eazyPanel= new JPanel(new BorderLayout()); 
	private final JPanel hwPanel= new JPanel(new BorderLayout()); 
	private final JPanel commentPanel= new JPanel(new BorderLayout()); 
    
	private final JLabel classLabel;
	private final JLabel teacherLabel;
	private final JLabel scoreLabel;
	private final JLabel ruleLabel;
	private final JLabel eazyLabel;
	private final JLabel hwLabel;
	private final JLabel commentLabel;

	private final JTextField classValue;
	private final JTextField teacherValue;
	private final JTextField scoreValue;
	private final JTextField ruleValue;
	private final JTextField learnValue;
	private final JTextField hwValue;
	private final JTextArea commentValue;
	//留言
	public CommentView(String className,String teacher,String score,String rule,String learn,String hw,String comment) {
		classLabel = buildTitleLabel("課程名稱");
		classValue = buildValueField(className);
		classPanel.add(classLabel,BorderLayout.WEST);
		classPanel.setBackground(new Color(26,25,25));
		classPanel.add(classValue);

		teacherLabel = buildTitleLabel("老師姓名");
		teacherValue = buildValueField(teacher);
		teacherPanel.add(teacherLabel,BorderLayout.WEST);
		teacherPanel.setBackground(new Color(26,25,25));
		teacherPanel.add(teacherValue);

		scoreLabel = buildTitleLabel("評分方式");
		scoreValue = buildValueField(score);
		scorePanel.add(scoreLabel,BorderLayout.WEST);
		scorePanel.setBackground(new Color(26,25,25));
		scorePanel.add(scoreValue);

		ruleLabel = buildTitleLabel("上課規則");
		ruleValue = buildValueField(rule);
		rulePanel.add(ruleLabel,BorderLayout.WEST);
		rulePanel.setBackground(new Color(26,25,25));
		rulePanel.add(ruleValue);

		eazyLabel = buildTitleLabel("涼的程度");
		learnValue = buildValueField(learn);
		eazyPanel.add(eazyLabel,BorderLayout.WEST);
		eazyPanel.setBackground(new Color(26,25,25));
		eazyPanel.add(learnValue);

		hwLabel = buildTitleLabel("作業方式");
		hwValue = buildValueField(hw);
		hwPanel.add(hwLabel,BorderLayout.WEST);
		hwPanel.setBackground(new Color(26,25,25));
		hwPanel.add(hwValue);

		commentLabel = buildTitleLabel("其他心得");
		commentPanel.add(commentLabel,BorderLayout.WEST);
		commentValue=new JTextArea(comment);
		commentValue.setEditable(false);
		commentValue.setBackground(new Color(26,25,25));
		commentValue.setForeground(Color.white); 
		commentValue.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
        commentValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        commentValue.setLineWrap(true);        //自動換行 
		commentPanel.setBackground(new Color(26,25,25));
		commentPanel.add(commentValue);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(classPanel);
		add(teacherPanel);
		add(scorePanel);
		add(rulePanel);
		add(eazyPanel);
		add(hwPanel);
		add(commentPanel);
	}

	private JTextField buildValueField(String text) { 
		JTextField field = new JTextField(text);  
		field.setForeground(Color.white); 
        field.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		field.setHorizontalAlignment(JTextField.LEFT);
		field.setBackground(new Color(26,25,25));
		field.setEditable(false);
		return field;
	}

	private JLabel buildTitleLabel(String title) {
		JLabel label = new JLabel(space+space + title + space);
		label.setForeground(Color.white);
		label.setBackground(new Color(26,25,25));
        label.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label.setHorizontalAlignment(JTextField.RIGHT);
		return label;
	}
}
