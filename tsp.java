import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class tsp
{		
	public static void main(String [] args)
	{
		//OPENING THE INPUT WINDOW FOR USER
		InputWindow window = new InputWindow();
		window.setVisible(true);
	}
}

class InputWindow extends JFrame {
	
	//CREATING USERINPUT CLASS TO GET USER DATA
	private UserInput input = new UserInput();
	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @param stuff 
	 */
	    public InputWindow()
	    {
	    //INITIALISING THE WINDOW
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 669, 462);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//ADDING JLABEL TO PROMPT USER TO INPUT ORDERS
		JLabel lblNewLabel = new JLabel("Input your orders");
		lblNewLabel.setFont(new Font("Nirmala UI Semilight", Font.PLAIN, 16));
		lblNewLabel.setBounds(271, 89, 176, 40);
		contentPane.add(lblNewLabel);
		
		//CREATING TEXT PANE FOR USER INPUT
		JTextPane textPane = new JTextPane();
		textPane.setBounds(48, 153, 582, 206);
		contentPane.add(textPane);
		
		//CREATING THE OK BUTTON FOR WHEN USER HAS INPUT DATA
		JButton btnNewButton = new JButton("ok");
		btnNewButton.setFont(new Font("Nirmala UI Semilight", Font.PLAIN, 16));
		btnNewButton.setBounds(533, 394, 85, 21);
		contentPane.add(btnNewButton);
		
		//WHEN BUTTON IS PRESSED CALCULATE BEST ROUTE AND CLOSE INPUT WINDOW
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				input.add(textPane.getText());
				new Calculation(input);
				dispose();
			}
		});
	}
}

class Calculation
{
	//GETTING USER INPUT
	private UserInput input = new UserInput();
	
	public Calculation(UserInput input)
	{
		//REPLACING NEW LINES WITH A COMMA AND SPLITTING THE DATA USING THE COMMA
		this.input = input;
		String first = this.input.get();
		first = first.replace("\n", "").replace("\r", ",");
		String [] array = first.split(",");
		
		//CREATING ARRAYLISTS USED IN CALCULATION
		ArrayList<Double>lng = new ArrayList<Double>();
		ArrayList<Double>lat = new ArrayList<Double>();
		ArrayList<Integer>order = new ArrayList<Integer>();
		ArrayList<String>address = new ArrayList<String>();
		
		//GETTING THE LONGITUDES FROM USER INPUT
		for(int i = 3; i < array.length; i = i+5)
		{
			lng.add(Double.parseDouble(array[i]));
		}
		
		//GETTING ADDRESSES FROM USER INPUT
		for(int i = 1; i < array.length; i = i+5)
		{
			address.add(array[i]);
		}
		
		//GETTING LATITUDE FROM USER INPUT
		for(int i = 4; i < array.length; i = i+5)
		{
			lat.add(Double.parseDouble(array[i]));
		}
		
		//CREATING AN ARRAY OF ORDER NUMBERS
		for(int i = 0; i < address.size(); i++) {order.add(i+1);}
		
		int jtemp = 0;
		int itemp = 0;
		
		ArrayList<Integer>finalorder = new ArrayList<Integer>();
		ArrayList<String>address2 = new ArrayList<String>();
		
		//FINDING THE SHORTEST DISTANCE FROM THE HIGHEST PRIORITY HOUSE TO THE NEXT HOUSE
		for(int i = 0; i < lng.size(); i++)
		{
			double smallest = Double.MAX_VALUE;
			
			for(int j = 0; j < lng.size(); j++)
			{ 
				if(lng.get(itemp) != lng.get(j) && lat.get(itemp) != lat.get(j) && lng.get(j)!=(double) 0)
				{
					double x = 0;
					
					double rad1 = Math.toRadians(lat.get(itemp));
			        double rad2 = Math.toRadians(lng.get(itemp));
			        double rad3 = Math.toRadians(lat.get(j));
					double rad4 = Math.toRadians(lng.get(j));
			        
			        x = Math.sin(rad1) * Math.sin(rad3) + Math.cos(rad1) * Math.cos(rad3) * Math.cos(Math.abs((rad2-rad4)));
			        x = 6371 * Math.acos(x);
			        double y =  (double) Math.round(x*100.0)/100;
			        
			        if(y < smallest)
			        {
			        	smallest = y;;
			        	jtemp = j;
			        }
				}
			}
			lat.set(itemp,(double) 0);
			lng.set(itemp,(double) 0);
			finalorder.add(order.get(itemp));
			address2.add(address.get(itemp));
			itemp  = jtemp;
		}
		
		//OPENING THE OUTPUT WINDOW
		Output output = new Output(finalorder, address2);
		output.setVisible(true);
	}
}

class Output extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @param order 
	 */
        public Output(ArrayList<Integer> finalorder,ArrayList<String> address2) {
        
        //INITILIASING THE WINDOW
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 898, 550);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//LABEL TO SHOW USERS THE ROUTE
		JLabel lblNewLabel = new JLabel("Order Numbers:");
		lblNewLabel.setFont(new Font("Nirmala UI Semilight", Font.PLAIN, 16));
		lblNewLabel.setBounds(52, 406, 135, 22);
		contentPane.add(lblNewLabel);

		//DISPLAY THE IMAGE OF MAP 
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("s_map.png"));
		lblNewLabel_1.setBounds(52, 57, 392, 328);
		contentPane.add(lblNewLabel_1);
		
		String output = new String("");
		
		//CREATING A STRING WITH NEW ORDER TO VISIT THE CUSTOMERS IN SEPERATED BY A COMMA
		for(int i = 0; i < finalorder.size(); i++)
		{
			if(i != finalorder.size()-1){output += Integer.toString(finalorder.get(i)) + ", ";}
			else{output += Integer.toString(finalorder.get(i));}
		}
		
		//CREATING A STRING WITH NEW ADDRESS ORDER TO VISIT THE CUSTOMERS IN SEPERATED BY A COMMA
		String add = new String("");
		for(int i = 0; i < address2.size(); i++)
		{
			if(i != address2.size()-1){add += address2.get(i) + "\n";}
			else{add += address2.get(i);}
		}
		
		//DISPLAYING THE CUSTOMERS ADDRESS IN ORDER TO VISIT THEM WITH SCROLL WHEEL
		JTextArea textArea_1 = new JTextArea(add);
		textArea_1.setEditable(false);
		textArea_1.setLineWrap(true);
		textArea_1.setWrapStyleWord(true);
		textArea_1.setFont(new Font("Nirmala UI Semilight", Font.PLAIN, 16));
		JScrollPane scrollPane = new JScrollPane(textArea_1);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(454, 57, 392, 328);
		contentPane.add(scrollPane);
		
		//DISPLAYING THE ORDER NUMBERS TO BE VISITED IN NEW ORDER
		JTextArea textArea = new JTextArea(output);
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Nirmala UI Semilight", Font.PLAIN, 16));
		textArea.setBounds(52, 438, 808, 65);
		contentPane.add(textArea);
	}
}

//CREATING A CLASS TO STORE USER DATA THAT CAN BE USED IN ALL CLASSES
class UserInput
{
	String input;
	
	public void add(String input) {
		this.input = input;
	}
	
	public String get()
	{
		return input;
	}
}