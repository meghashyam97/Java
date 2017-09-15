import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.table.*;
import java.io.*;
import java.util.*;


class AutoCompleteTextField extends JTextField implements KeyListener,
            DocumentListener {
    private ArrayList<String> possibilities;
    private int currentGuess;
    private Color incompleteColor;
    private boolean areGuessing;
    private boolean caseSensitive;

    AutoCompleteTextField() {
        this(5, false);
    }

    AutoCompleteTextField(int columns) {
        this(columns, false);
    }

    AutoCompleteTextField(int columns, boolean caseSensitive) {
        super.setColumns(columns);
        this.possibilities = new ArrayList<String>();
        this.incompleteColor = Color.GRAY.brighter();
        this.currentGuess = -1;
        this.areGuessing = false;
        this.caseSensitive = caseSensitive;
        this.addKeyListener(this);
        this.getDocument().addDocumentListener(this);
    }

    public void addPossibility(String possibility) {
        this.possibilities.add(possibility);
        Collections.sort(possibilities);
    }

    public void removePossibility(String possibility) {
        this.possibilities.remove(possibility);
    }

    public void removeAllPossibilities() {
        this.possibilities = new ArrayList<String>();
    }

    public void setIncompleteColor(Color incompleteColor) {
        this.incompleteColor = incompleteColor;
    }

    private String getCurrentGuess() {
        if (this.currentGuess != -1)
            return this.possibilities.get(this.currentGuess);

        return this.getText();
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    private void findCurrentGuess() {
        String entered = this.getText();
        if (!this.caseSensitive)
            entered = entered.toLowerCase();

        for (int i = 0; i < this.possibilities.size(); i++) {
            currentGuess = -1;

            String possibility = this.possibilities.get(i);
            if (!this.caseSensitive)
                possibility = possibility.toLowerCase();
            if (possibility.startsWith(entered)) {
                this.currentGuess = i;
                break;
            }
        }
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        this.areGuessing = false;
        this.currentGuess = -1;
    }

    @Override
    public void paintComponent(Graphics g) {
        String guess = this.getCurrentGuess();
        String drawGuess = guess;

        super.paintComponent(g);
        String entered = this.getText();
        Rectangle2D enteredBounds = g.getFontMetrics().getStringBounds(entered, g);

        if (!(this.caseSensitive)) {
            entered = entered.toLowerCase();
            guess = guess.toLowerCase();
        }
        
        if (!(guess.startsWith(entered)))
            this.areGuessing = false;

        if (entered != null && !(entered.equals("")) 
                && this.areGuessing) {
            String subGuess = drawGuess.substring(entered.length(), drawGuess.length());
            Rectangle2D subGuessBounds = g.getFontMetrics().getStringBounds(drawGuess, g);

            int centeredY = ((getHeight() / 2) + (int)(subGuessBounds.getHeight() / 2));

            g.setColor(this.incompleteColor);
            g.drawString(subGuess, (int)(enteredBounds.getWidth()) + 40,centeredY);
        }
    }

    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) { 
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.areGuessing) {
                this.setText(this.getCurrentGuess());
                this.areGuessing = false;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (this.areGuessing) {
                this.setText(this.getCurrentGuess());
                this.areGuessing = false;
                e.consume();
            }
        }
    }
    
    public void keyReleased(KeyEvent e) { }

    public void insertUpdate(DocumentEvent e) {
        String temp = this.getText();

        if (temp.length() == 1)
            this.areGuessing = true;

        if (this.areGuessing)
            this.findCurrentGuess();

    }

    public void removeUpdate(DocumentEvent e) {
        String temp = this.getText();

        if (!(this.areGuessing))
            this.areGuessing = true;

        if (temp.length() == 0)
            this.areGuessing = false;
        else if (this.areGuessing) {
            this.findCurrentGuess();
        }
    }

    public void changedUpdate(DocumentEvent e) { }
}
/*
<object code="abc" width=800 height=800>
</object>
*/
public class abc extends JApplet {
 
     	public void init() {
		SwingUtilities.invokeLater(new Runnable(){
	       		public void run() {
				initGui();
			}
		});
	}
	class Student
	{
		String usn,gpa;
		String name,phone,address;
		Student(String a,String b,String s1,String s2,String s3)
		{
			this.usn=a;
			this.gpa=b;
			this.name=s1;
			this.phone=s2;
			this.address=s3;
			
		}
		public String getId()
		{	return this.usn;	}
		public String getGpa()
		{	return this.gpa;	}
		public String getName()
		{	return this.name;	}
		public String getPhone()
		{	return this.phone;	}
		public String getAdd()
		{	return this.address;	}
	}

	         	//set color things
    private static class MyCellRenderer extends DefaultTableCellRenderer {
    	private static final Color SELECTED_COLOR = Color.pink;
      	private static final Color UNSELECTED_COLOR = Color.lightGray;
      	private static final Color BASE_COLOR = null;
      	private boolean showSelected = false;
      	private String s=null;

      	public void setShowSelected(boolean showSelected,String sq) {
         		this.showSelected = showSelected;
         		this.s=sq;
         		//System.out.println(this.s);
      	}

      	@Override
      	public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row,int column) {
        	super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
			String status = (String)table.getModel().getValueAt(row,0);
			//System.out.println(status);
        	if (showSelected) {
        		if (status.equals(this.s)) {
        			setBackground(SELECTED_COLOR);
       	     } else {
       	     	setBackground(UNSELECTED_COLOR);
     	       }
        	 } else {
    	     		setBackground(BASE_COLOR);
       	  	 }
	
	      	return this;
		}
	}
	class jp2 extends JPanel
	{
		//String g1="abcdge";
		jp2(ArrayList<Student> arr)
		{
			super();
			this.setLayout(null);
			//create table
			
			String col[] = {"ID","GPA","Name","Phone","Address"};
			DefaultTableModel tableModel = new DefaultTableModel(col, 0) {
      			@Override
      				public Class<?> getColumnClass(int columnIndex) {
         				return Integer.class;
      				}
   			};
			tableModel.addRow(col);
         	JTable table = new JTable(tableModel);
         	MyCellRenderer myCellRenderer = new MyCellRenderer();
         	table.setDefaultRenderer(Integer.class, myCellRenderer);

   			
			//add data to table from list
         	for(int i=0;i<arr.size();i++)
         	{
         		String w1=arr.get(i).getId();
         		String w2=arr.get(i).getGpa();
         		String w3=arr.get(i).getName();
         		String w4=arr.get(i).getPhone();
         		String w5=arr.get(i).getAdd();
         		String w[]={w1,w2,w3,w4,w5};
         		tableModel.addRow(w);
         	}
         	//panel elements
			JTextField jt1=new JTextField();
			JTextField jt2=new JTextField();
			JTextField jt3=new JTextField();
			JTextField jt4=new JTextField();
			JTextField jt5=new JTextField();
			JTextField jt6=new JTextField();
			//JTextField jt7=new JTextField("GPA");
			//JTextField jt8=new JTextField("Name");
			//JTextField jt9=new JTextField("Phone");
			//JTextField jt10=new JTextField("Address");
			AutoCompleteTextField at = new AutoCompleteTextField(15,false);
			at.setIncompleteColor(Color.BLUE);
			//if(g1!="abcdge")
			//{
				//at.addPossibility(g1);	
			//}
			JTextField jt11=new JTextField();
			JTextField jt12=new JTextField();
			JTextField jt13=new JTextField();
			JTextField jt14=new JTextField();
			JButton jb1=new JButton("UPDATE");
			JButton jb2=new JButton("SEARCH");
			JButton jb3=new JButton("INSERT");
			JButton jb4=new JButton("BACKUP");
			JLabel jl1=new JLabel("ID");
			JLabel jl2=new JLabel("GPA");
			JLabel jl3=new JLabel("Name");
			JLabel jl4=new JLabel("Phone");
			JLabel jl5=new JLabel("Address");
			ArrayList<Student> backup = new ArrayList<Student>();
			jb1.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ae2){
										String s1,s2,s3,s4,s5;
										s1=jt1.getText();
										s2=jt2.getText();
										s3=jt3.getText();
										s4=jt4.getText();
										s5=jt5.getText();
										for(int i=0;i<tableModel.getRowCount();i++)
											if(tableModel.getValueAt(i,0).equals(s1))
											{
												tableModel.setValueAt(s1,i,0);
												tableModel.setValueAt(s2,i,1);
												tableModel.setValueAt(s3,i,2);
												tableModel.setValueAt(s4,i,3);
												tableModel.setValueAt(s5,i,4);
											}
										jt1.setText(null);
										jt2.setText(null);
										jt3.setText(null);
										jt4.setText(null);
										jt5.setText(null);
									}
										});
			jb2.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ae2){
										String s1;
										s1=jt6.getText();
										int k;
										for(k=0;k<tableModel.getRowCount();k++)
											if(tableModel.getValueAt(k,0).equals(s1))
												break;
										myCellRenderer.setShowSelected(true,s1);
										table.repaint();
										jt6.setText(null);
									
										//s2=jt7.getText();
										//s3=jt8.getText();
										//s4=jt9.getText();
										//s5=jt10.getText();
									}
										});
			jb3.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ae2){
										String s1,s2,s3,s4,s5;
										s1=at.getText();
										//jp2.this.g1=s1;
										at.addPossibility(s1);
										s2=jt11.getText();
										s3=jt12.getText();
										s4=jt13.getText();
										s5=jt14.getText();
										String q[]={s1,s2,s3,s4,s5};
										tableModel.addRow(q);
										at.setText(null);
										jt11.setText(null);
										jt12.setText(null);
										jt13.setText(null);
										jt14.setText(null);
									}
										});
			jb4.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ae3){
										String s1,s2,s3,s4,s5;
										for(int i=1;i<tableModel.getRowCount();i++)
										{
											s1=(String)tableModel.getValueAt(i,0);
											s2=(String)tableModel.getValueAt(i,0);
											s3=(String)tableModel.getValueAt(i,0);
											s4=(String)tableModel.getValueAt(i,0);
											s5=(String)tableModel.getValueAt(i,0);
											Student qwer=new Student(s1,s2,s3,s4,s5);
											backup.add(qwer);
										}
									}
										});
									
										
			String adfg="jsahfj";
			jl1.setBounds(40,50,100,50);
			jl2.setBounds(164,50,100,50);
			jl3.setBounds(288,50,100,50);
			jl4.setBounds(412,50,100,50);
			jl5.setBounds(536,50,100,50);



			jt1.setBounds(40,100,100,50);
			jt2.setBounds(164,100,100,50);
			jt3.setBounds(288,100,100,50);
			jt4.setBounds(412,100,100,50);
			jt5.setBounds(536,100,100,50);
			jb1.setBounds(660,100,100,50);
			/*JPanel udt = new JPanel(new FlowLayout());  						
			udt.add(jt1);
			udt.add(jt2);
			udt.add(jt3);
			udt.add(jt4);
			udt.add(jt5);
			udt.add(jb1);
			*/
			jt6.setBounds(40,200,100,50);
			jb2.setBounds(164,200,100,50);
			/*JPanel srch = new JPanel(new FlowLayout());
			srch.add(jt6);
			srch.add(jb2);
			srch.add(new JLabel());
			srch.add(new JLabel());
			srch.add(new JLabel());
			srch.add(new JLabel());
			*/
			at.setBounds(40,300,100,50);
			jt11.setBounds(164,300,100,50);
			jt12.setBounds(288,300,100,50);
			jt13.setBounds(412,300,100,50);
			jt14.setBounds(536,300,100,50);
			jb3.setBounds(660,300,100,50);
			/*JPanel inst = new JPanel(new FlowLayout());
			inst.add(at);
			inst.add(jt11);
			inst.add(jt12);
			inst.add(jt13);
			inst.add(jt14);
			inst.add(jb3);
			udt.setBounds(100,100,700,100);
			srch.setBounds(100,250,700,100);
			inst.setBounds(100,400,700,100);
			table.setBounds(100,550,700,100);
			this.add(udt);
			this.add(srch);
			this.add(inst);
			*/
			table.setBounds(40,400,720,300);
			jb4.setBounds(675,700,100,50);
			this.add(jt1);
			this.add(jt2);
			this.add(jt3);
			this.add(jt4);
			this.add(jt5);
			this.add(jb1);
			this.add(jt6);
			this.add(jb2);
			this.add(at);
			this.add(jt11);
			this.add(jt12);
			this.add(jt13);
			this.add(jt14);
			this.add(jb3);
			this.add(table);
			this.add(jl1);
			this.add(jl2);
			this.add(jl3);
			this.add(jl4);
			this.add(jl5);
			this.add(jb4);
			
			
			
					
		}
	}
	public void initGui()
	{
		/*JPanel jp = new JPanel(new FlowLayout());
		AutoCompleteTextField at = new AutoCompleteTextField(20,false);
		at.setIncompleteColor(Color.BLUE);
		at.addPossibility("123");
		at.addPossibility("122");
		at.addPossibility("456");
		at.addPossibility("789");
		jp.add(at);
		*/
		ArrayList<Student> datalist = new ArrayList<Student>();
	
		try
		{	
			FileReader fr = new FileReader("data.csv");
	      	BufferedReader br = new BufferedReader(fr);
	      	String stringRead = br.readLine();
			//System.out.println(stringRead);	
	      	while( stringRead != null )
	      	{
	       		//	while(st.hasMoreTokens())
				{
	
	      	  			StringTokenizer st = new StringTokenizer(stringRead, ",");
					String a=st.nextToken();
					String b=st.nextToken();
	        			String s1 = st.nextToken();
	        			String s2= st.nextToken(); 
	        			String s3 = st.nextToken(); 
	
        				Student temp = new Student(a,b,s1,s2,s3);
        				datalist.add(temp);
	
       					// read the next line
        				stringRead = br.readLine();
				}
			
   			}
   	   		br.close( );
   	 	}
   	 	catch(IOException ioe){System.out.println("error");}
		
		
		jp2 jp = new jp2(datalist);
		JPanel gui = new JPanel(new BorderLayout());
			
        	JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	gui.add(buttons, BorderLayout.NORTH);
        	
 	
		JButton tree = new JButton("Reading from file");
 	        buttons.add(tree);
 	        JButton text = new JButton("Reading from List");
	        buttons.add(text);

	
        	CardLayout cardLayout = new CardLayout();
        	
        	JPanel content = new JPanel(cardLayout);
        	gui.add(content, BorderLayout.CENTER);
 		content.add(jp, "tree");
		ArrayList<Student> al2=new ArrayList<Student>();
		al2.add(new Student("1234","5","shgd","shyg","hgd"));
		al2.add(new Student("1345","7","afbg","ahfu","aufh"));
		jp2 jpp = new jp2(al2);
        	content.add(jpp, "text");
 
        	ActionListener al = new ActionListener(){
            		public void actionPerformed(ActionEvent ae) {
                		if (ae.getSource().equals(tree)) {
                		    cardLayout.show(content, "tree");
                		} else {
                		    cardLayout.show(content, "text");
                		}
            		}
        	};
        	tree.addActionListener(al);
        	text.addActionListener(al);
 
        	setContentPane(gui);
    	}
		


	}

