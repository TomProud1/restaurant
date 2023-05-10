import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder; 

public class Restaurant {

	private JFrame frmRestaurant;
	
	private JTextField txtUsername;
	private JTextField txtpasswordField;
	private JTable jtable1;
	private JTextField textFieldSubTotal;
	private JTextField textFieldTotal;
	private JTextField textFieldCash;
	private JTextField textFieldTax;
	private JTextField textFieldDis;
	private JTextField textFieldBal;
	private JTextPane JTextPaneBill;
	private JLabel lblPicture;
	
	/**
	 * Method was made to fit size of an ImageIcon to a JButton
	 */
	private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
	    Image img = icon.getImage();  
	    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
	    return new ImageIcon(resizedImage);
	}

	
	/**
	 * Method was made to addMouseListiner on JButtons with mouseEntered on green color
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Restaurant window = new Restaurant();
					window.frmRestaurant.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Restaurant() {
		initialize();
	}

	public void addTable(String Name, Double Price) {
		String Qty = JOptionPane.showInputDialog(null, "Please Enter Your Quantity", "1");
		
		Double tqty = Double.valueOf(Qty);
		Double Tot_prc1 = Price * tqty;
		
		DecimalFormat df = new DecimalFormat (".00");
		String Tot_prc = df.format(Tot_prc1);
		
		//System.out.println(Tot_prc);
		
		// add product to cart
		
		DefaultTableModel dt = (DefaultTableModel) jtable1.getModel();
		
		Vector v = new Vector();
		v.add(Name);
		v.add(Qty);
		v.add(Tot_prc.replace(",", "."));
		dt.addRow(v);
		
		// Running cart_total Method in the Program
		cart_total();
	}
	
	public void cart_total() {
		
		int numofrow = jtable1.getRowCount();
		
		double total = 0;
		
		for (int i = 0; i < numofrow; i++) {
			
			double value = Double.valueOf(jtable1.getValueAt(i, 2).toString());
			
			total +=value;
			
		}
		// Round to two decimal places SUB TOTAL
		DecimalFormat df = new DecimalFormat (".00");
		String d1 = df.format(total);
		
		// Showing Sub Total in Panel Cart
		textFieldSubTotal.setText((d1).replace(",", "."));
		
		// Showing Total in Panel Cart
		double tx = Double.valueOf(textFieldTax.getText());
		double ds = Double.valueOf(textFieldDis.getText());
		
		double allTot = total + tx - ds;
		
		// Round on two decimal places TOTAL
		DecimalFormat dff = new DecimalFormat (".00");
		String d2 = dff.format(allTot);
		
		// Showing Total in Panel Cart
		textFieldTotal.setText(d2.replace(",", "."));
			//(String.valueOf(d2).replace(",", "."))
	}	
	// This was made to show printing data to the bill in Jpanel - JtextArea
	public void drwobill() {
		JTextPaneBill.setText(JTextPaneBill.getText() +" \t       The Italian Restuarant \n");
		JTextPaneBill.setText(JTextPaneBill.getText() +" \t       487/Green Street 1B \n");
		JTextPaneBill.setText(JTextPaneBill.getText() +" \t       State Colorado, City Denver \n");
		JTextPaneBill.setText(JTextPaneBill.getText() +"***************************************************************************\n");
		JTextPaneBill.setText(JTextPaneBill.getText() +"    Iteam \t\t\t Qty \t Price \n");
		JTextPaneBill.setText(JTextPaneBill.getText() +"***************************************************************************\n");
		
		
		//add jtable product list
		
		DefaultTableModel dt = (DefaultTableModel) jtable1.getModel();
		
		for (int i = 0; i < jtable1.getRowCount(); i++) {
			
			String itm = dt.getValueAt(i,0).toString(); //Iteam Name
			String gt = dt.getValueAt(i,1).toString(); //Iteam Qtl
			String prc = dt.getValueAt(i,2).toString();//Iteam cal price
			
			JTextPaneBill.setText(JTextPaneBill.getText()+"  " + itm + "\t" + gt + "\t" + prc + "\n");
		}
		
		// end product for loop
		
		JTextPaneBill.setText(JTextPaneBill.getText()+"-----------------------------------------------------------------------------------------\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"  Subtotal:\t\t\t\t " +textFieldSubTotal.getText() + "\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"  Tax:\t\t\t\t " +textFieldTax.getText() + "\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"  Discount:\t\t\t\t " +textFieldDis.getText() + "\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"------------------------------------------------------------------------------------------\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"  Total:\t\t\t\t " +textFieldTotal.getText() + "\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"  Cash:\t\t\t\t " +textFieldCash.getText() + "\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"  Balance:\t\t\t\t " +textFieldBal.getText() + "\n");
		
		// This was made to show Date and Time from Calendar
		
		Calendar timer = Calendar.getInstance();
		timer.getTime();
		SimpleDateFormat tTime = new SimpleDateFormat("HH:mm:ss");
		tTime.format(timer.getTime());
		SimpleDateFormat Tdate = new SimpleDateFormat("dd-MMM-yyyy");
		Tdate.format(timer.getTime());

		JTextPaneBill.setText(JTextPaneBill.getText()+"------------------------------------------------------------------------------------------\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+" Date: " + Tdate.format(timer.getTime()) + "\t\t           Time: " + tTime.format(timer.getTime())+"\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"***************************************************************************\n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"\t     Thanks For Your Business..! \n");
		JTextPaneBill.setText(JTextPaneBill.getText()+"***************************************************************************\n");
	}
	
	public void textdocument() {
		String nameFile = "textBillFile.txt";
		File text = new File (nameFile);
		
		if(!text.exists()) {
			try {				
				text.createNewFile();
			} catch (IOException e) {
				System.out.println("an error occurred!!!");
			}
		}
		
		try {
            PrintWriter out = new PrintWriter(new FileWriter("textBillFile.txt", true));
            JTextPaneBill.getText();
            JTextPaneBill.write(out);
            out.write("=========================================================");
            out.write("=========================================================");
            out.write("=========================================================");
            out.write("=========================================================");
            out.write("=============================================== \n");
           //out.println(JTextPaneBill);
            out.flush();
            out.close();
                 
        } catch (IOException e1) {
            System.err.println("Error occurred");
            e1.printStackTrace();
        }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRestaurant = new JFrame();
		frmRestaurant.setTitle("Restaurant");
		frmRestaurant.setBounds(45, 30, 1450, 750);
		frmRestaurant.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRestaurant.getContentPane().setLayout(null);
		
		CardLayout myLayout = new CardLayout();
		frmRestaurant.getContentPane().setLayout(myLayout);
		
		/*
		 * THIS IS A JPanel LOGINPAGE (BELOW SHOWING ALL BUTTONS FOR LOGIN IN THE SYSTEM)!
		 */
		JPanel LoginPage = new JPanel();
		frmRestaurant.getContentPane().add(LoginPage, "login");
		LoginPage.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login System Resturant");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(630, 86, 164, 31);
		LoginPage.add(lblLogin);
		
		JButton btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmRestaurant.getContentPane(), "mainrestaurant");
			}
		});
		btnCheck.setBounds(596, 316, 96, 21);
		LoginPage.add(btnCheck);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(630, 184, 175, 19);
		LoginPage.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsername.setBounds(553, 185, 54, 15);
		LoginPage.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(553, 230, 90, 15);
		LoginPage.add(lblPassword);
		
		txtpasswordField = new JPasswordField();
		txtpasswordField.setBounds(630, 229, 175, 19);
		LoginPage.add(txtpasswordField);
		
		// --This button was made to Login in the next JPanel with Password and Username!--//
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = txtpasswordField.getText();
				String username = txtUsername.getText();
				
				if(password.contains("sivka") && username.contains("sivka")) {
					txtpasswordField.setText(null);
					txtUsername.setText(null);
					myLayout.show(frmRestaurant.getContentPane(), "mainrestaurant");
				}else {
					JOptionPane.showMessageDialog(null, "Invalind Login Details","Login Error", JOptionPane.ERROR_MESSAGE);
					txtpasswordField.setText(null);
					txtUsername.setText(null);
				}
					
			}
		});
		btnLogin.setBounds(490, 316, 96, 21);
		LoginPage.add(btnLogin);
		
		// This button was made to RESET the text in Username and Password Field!
		
		JButton btnResetLogin = new JButton("Reset");
		btnResetLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText(null);
				txtpasswordField.setText(null);
			}
		});
		btnResetLogin.setBounds(703, 316, 96, 21);
		LoginPage.add(btnResetLogin);
		
		// This button was made to get Exit from Login JFrame!
		
		JButton btnExitLogin = new JButton("Exit");
		btnExitLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmRestaurant = new JFrame ("Exit");
				if (JOptionPane.showConfirmDialog(frmRestaurant,"Confirm if you want to exit!", "Employee data base system", 
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExitLogin.setBounds(809, 316, 96, 21);
		LoginPage.add(btnExitLogin);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(983, 14, 0, 2);
		LoginPage.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(988, 14, 0, 2);
		LoginPage.add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(479, 139, 454, 2);
		LoginPage.add(separator_1_1);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(479, 285, 454, 2);
		LoginPage.add(separator_1_2);
		
		/*
		 * THIS IS A JPanel MAIN PAGE RESTUARANT (BELOW SHOWING ALL BUTTONS FROM MAIN PAGE)!
		 */
		JPanel MainPageRestaurant = new JPanel();
		frmRestaurant.getContentPane().add(MainPageRestaurant, "mainrestaurant");
		MainPageRestaurant.setLayout(null);
		
		JPanel panelButton = new JPanel();
		panelButton.setBounds(10, 10, 665, 693);
		MainPageRestaurant.add(panelButton);
		panelButton.setLayout(null);
		
		//Pictures sources as a ImageIcon for JButton//
		
		ImageIcon icon1 = new ImageIcon("res\\CharGrillChikenChop1.png");
		ImageIcon icon2 = new ImageIcon("res\\DeepFriedCrispyChicken2.png");
		ImageIcon icon3 = new ImageIcon("res\\ChickenMaryland3.png");
		ImageIcon icon4 = new ImageIcon("res\\CharGrillGermanPorkBratwurst5.png");
		ImageIcon icon5 = new ImageIcon("res\\CharGrillPorkChopWChipolata7.png");
		ImageIcon icon6 = new ImageIcon("res\\GrillFishWCreamSauce9.png");
		ImageIcon icon7 = new ImageIcon("res\\Fish&Chips10.png");
		ImageIcon icon8 = new ImageIcon("res\\GrillSalmonFilletWCreamSauces11.png");
		ImageIcon icon9 = new ImageIcon("res\\CharGrillSirloinSteak13.png");
		ImageIcon icon10 = new ImageIcon("res\\MixedGrill14.png");
		ImageIcon icon11 = new ImageIcon("res\\PrawnAglioOlioSpaghetti15.png");
		ImageIcon icon12 = new ImageIcon("res\\CaliforniaRaisinRiceWChickenChop18.png");
		ImageIcon icon13 = new ImageIcon("res\\CaliforniaRaisinRiceWCrispyChicken19.png");
		ImageIcon icon14 = new ImageIcon("res\\WholeSpringChicken22.png");
		ImageIcon icon15 = new ImageIcon("res\\CheeseFries25.png");
		ImageIcon icon16 = new ImageIcon("res\\NuggerWFries26.png");
	
		JButton btnRestaurant1 = new JButton(icon1);
		btnRestaurant1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Char Grill Chiken Chop                 ", 6.90);
			}
		});
		btnRestaurant1.setBounds(10, 10, 155, 98);
		panelButton.add(btnRestaurant1);
		
		// Set image to size of JButton//
		btnRestaurant1.setIcon(resizeIcon(icon1, btnRestaurant1.getWidth(), btnRestaurant1.getHeight()));
		
		JButton btnRestaurant2 = new JButton(icon2);
		btnRestaurant2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Deep Fried Crispy Chicken             ", 7.90);
			}
		});
		btnRestaurant2.setBounds(175, 10, 155, 98);
		panelButton.add(btnRestaurant2);
		
		// Set image to size of JButton//
		btnRestaurant2.setIcon(resizeIcon(icon2, btnRestaurant2.getWidth(), btnRestaurant2.getHeight()));
		
		JButton btnRestaurant3 = new JButton(icon3);
		btnRestaurant3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Chicken Maryland                       ", 8.90);
			}
		});
		btnRestaurant3.setBounds(340, 10, 155, 98);
		panelButton.add(btnRestaurant3);
		
		// Set image to size of JButton//
		btnRestaurant3.setIcon(resizeIcon(icon3, btnRestaurant3.getWidth(), btnRestaurant3.getHeight()));
		
		JButton btnRestaurant4 = new JButton(icon4);
		btnRestaurant4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Char Grill German Pork Bratwurst       ", 8.90);
			}
		});
		btnRestaurant4.setBounds(505, 10, 155, 98);
		panelButton.add(btnRestaurant4);
		
		// Set image to size of JButton//
		btnRestaurant4.setIcon(resizeIcon(icon4, btnRestaurant4.getWidth(), btnRestaurant4.getHeight()));
		
		JButton btnRestaurant5 = new JButton(icon5);
		btnRestaurant5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Char Grill Pork Chop w Chipolata       ", 8.90);
			}
		});
		btnRestaurant5.setBounds(10, 121, 155, 98);
		panelButton.add(btnRestaurant5);
		
		// Set image to size of JButton//
		btnRestaurant5.setIcon(resizeIcon(icon5, btnRestaurant5.getWidth(), btnRestaurant5.getHeight()));
		
		JButton btnRestaurant6 = new JButton(icon6);
		btnRestaurant6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Grill Fish w CreamSauce                ", 6.90);
			}
		});
		btnRestaurant6.setBounds(175, 121, 155, 98);
		panelButton.add(btnRestaurant6);
		
		// Set image to size of JButton//
		btnRestaurant6.setIcon(resizeIcon(icon6, btnRestaurant6.getWidth(), btnRestaurant6.getHeight()));
		
		JButton btnRestaurant7 = new JButton(icon7);
		btnRestaurant7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Fish & Chips                           ", 6.90);
			}
		});
		btnRestaurant7.setBounds(340, 121, 155, 98);
		panelButton.add(btnRestaurant7);
		
		// Set image to size of JButton//
		btnRestaurant7.setIcon(resizeIcon(icon7, btnRestaurant7.getWidth(), btnRestaurant7.getHeight()));
		
		JButton btnRestaurant8 = new JButton(icon8);
		btnRestaurant8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Grill Salmon Fillet w Cream Sauces     ", 8.90);
			}
		});
		btnRestaurant8.setBounds(505, 121, 155, 98);
		panelButton.add(btnRestaurant8);
		
		// Set image to size of JButton//
		btnRestaurant8.setIcon(resizeIcon(icon8, btnRestaurant8.getWidth(), btnRestaurant8.getHeight()));
		
		JButton btnRestaurant9 = new JButton(icon9);
		btnRestaurant9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Char Grill Sirloin Steak               ", 10.90);
			}
		});
		btnRestaurant9.setBounds(10, 229, 155, 98);
		panelButton.add(btnRestaurant9);
		
		// Set image to size of JButton//
		btnRestaurant9.setIcon(resizeIcon(icon9, btnRestaurant9.getWidth(), btnRestaurant9.getHeight()));
		
		JButton btnRestaurant10 = new JButton(icon10);
		btnRestaurant10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Mixed Grill                            ", 12.90);
			}
		});
		btnRestaurant10.setBounds(175, 229, 155, 98);
		panelButton.add(btnRestaurant10);
		
		// Set image to size of JButton//
		btnRestaurant10.setIcon(resizeIcon(icon10, btnRestaurant10.getWidth(), btnRestaurant10.getHeight()));
		
		JButton btnRestaurant11 = new JButton(icon11);
		btnRestaurant11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Prawn Aglio Olio Spaghetti             ", 6.90);
			}
		});
		btnRestaurant11.setBounds(340, 229, 155, 98);
		panelButton.add(btnRestaurant11);
		
		// Set image to size of JButton//
		btnRestaurant11.setIcon(resizeIcon(icon11, btnRestaurant11.getWidth(), btnRestaurant11.getHeight()));
		
		JButton btnRestaurant12 = new JButton(icon12);
		btnRestaurant12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("California Raisin Rice w Chicken Chop ", 6.90);
			}
		});
		btnRestaurant12.setBounds(505, 229, 155, 98);
		panelButton.add(btnRestaurant12);
		
		// Set image to size of JButton//
		btnRestaurant12.setIcon(resizeIcon(icon12, btnRestaurant12.getWidth(), btnRestaurant12.getHeight()));
		
		JButton btnRestaurant13 = new JButton(icon13);
		btnRestaurant13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("California Raisin Rice w Crispy Chicken", 5.90);
			}
		});
		btnRestaurant13.setBounds(10, 337, 155, 98);
		panelButton.add(btnRestaurant13);
		
		// Set image to size of JButton//
		btnRestaurant13.setIcon(resizeIcon(icon13, btnRestaurant13.getWidth(), btnRestaurant13.getHeight()));
		
		JButton btnRestaurant14 = new JButton(icon14);
		btnRestaurant14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Whole Spring Chicken                   ", 9.50);
			}
		});
		btnRestaurant14.setBounds(175, 337, 155, 98);
		panelButton.add(btnRestaurant14);
		
		// Set image to size of JButton//
		btnRestaurant14.setIcon(resizeIcon(icon14, btnRestaurant14.getWidth(), btnRestaurant14.getHeight()));
		
		JButton btnRestaurant15 = new JButton(icon15);
		btnRestaurant15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Cheese Fries                           ", 3.80);
			}
		});
		btnRestaurant15.setBounds(340, 337, 155, 98);
		panelButton.add(btnRestaurant15);
		
		// Set image to size of JButton//
		btnRestaurant15.setIcon(resizeIcon(icon15, btnRestaurant15.getWidth(), btnRestaurant15.getHeight()));
		
		JButton btnRestaurant16 = new JButton(icon16);
		btnRestaurant16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTable("Nugger w Fries                          ", 3.80);
			}
		});
		btnRestaurant16.setBounds(505, 337, 155, 98);
		panelButton.add(btnRestaurant16);
		
		// Set image to size of JButton//
		btnRestaurant16.setIcon(resizeIcon(icon16, btnRestaurant16.getWidth(), btnRestaurant16.getHeight()));
		
		
		// This button was made to delete fields from TextPanelBill and data Field
		JButton btnNewButton = new JButton("New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel dt = (DefaultTableModel) jtable1.getModel();
				dt.setRowCount(0);
				
				JTextPaneBill.setText("");
				textFieldSubTotal.setText("");
				textFieldTotal.setText("");
				textFieldCash.setText("");
				textFieldBal.setText("");
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnNewButton.setBounds(530, 580, 125, 61);
		panelButton.add(btnNewButton);
		
		// This button was made to delete a ROW in JTable and recalculate Total and Sub Total field
		JButton btnDeleteRow = new JButton("Delete Row");
		btnDeleteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// delete the row
				DefaultTableModel tblModel = (DefaultTableModel) jtable1.getModel();
				tblModel.removeRow(jtable1.getSelectedRow());
				// recalculating the total and subtotal
				cart_total();
			}
		});
		
		btnDeleteRow.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnDeleteRow.setBounds(530, 460, 125, 30);
		panelButton.add(btnDeleteRow);
		
		
		// This button was made to clear the PaneBill Only
		JButton btnDeletePaneBill = new JButton("Delete Bill");
		btnDeletePaneBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextPaneBill.setText("");
			}
		});
		
		btnDeletePaneBill.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnDeletePaneBill.setBounds(530, 500, 125, 30);
		panelButton.add(btnDeletePaneBill);
		
		
		// This button is deleting only cash jtextfield
		JButton btnDeleteCashEnter = new JButton("Delete Cash");
		btnDeleteCashEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldCash.setText("");
			}
		});
		btnDeleteCashEnter.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnDeleteCashEnter.setBounds(530, 540, 125, 30);
		panelButton.add(btnDeleteCashEnter);
		
		// This was made to get the icon image from res
		ImageIcon icon17 = new ImageIcon("res\\ItalianRestuarant.jpeg");
		
		lblPicture = new JLabel("");
		lblPicture.setBounds(10, 460, 497, 181);
		panelButton.add(lblPicture);
		
		// This was made to resize the icon to the lblPictute
		lblPicture.setIcon(resizeIcon(icon17, lblPicture.getWidth(), lblPicture.getHeight()));
		
		JButton btnEXIT = new JButton("EXIT");
		btnEXIT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmRestaurant.getContentPane(), "login");
			}
		});
		btnEXIT.setBounds(10, 651, 107, 32);
		panelButton.add(btnEXIT);
		
		/**
		 * Method was made to add mouse Entered in GREEN
		
		btnRestaurant1.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	btnRestaurant1.setBackground(Color.GREEN);
		    }
		});
	 */
		
		/**
		 * JPANEL was made to add J Table
	 */
		
		JPanel panelDisplay = new JPanel();
		panelDisplay.setBounds(685, 10, 741, 446);
		MainPageRestaurant.add(panelDisplay);
		panelDisplay.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 339, 426);
		panelDisplay.add(scrollPane);
		
		jtable1 = new JTable();
		jtable1.setBorder(new EmptyBorder(1, 1, 1, 1));
		jtable1.setBackground(Color.WHITE);
		scrollPane.setViewportView(jtable1);
		jtable1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Iteam", "Qty", "Price"
			}
		));
	
		// This was made Column Iteam is bigger then others//
		jtable1.getColumnModel().getColumn(0).setPreferredWidth(300);
		//jtable1.setHorizontalAlignment(SwingConstants.CENTER);

	   // jtable1.getColumnModel().getColumn(0).setCellRenderer(); 
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(359, 10, 372, 426);
		panelDisplay.add(scrollPane_2);
		
		JTextPaneBill = new JTextPane();
		scrollPane_2.setViewportView(JTextPaneBill);
		
		JPanel panelCart = new JPanel();
		panelCart.setBounds(685, 466, 741, 237);
		MainPageRestaurant.add(panelCart);
		panelCart.setLayout(null);
		
		JLabel lblSubTotal = new JLabel("Sub Total:");
		lblSubTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSubTotal.setBounds(10, 22, 143, 23);
		panelCart.add(lblSubTotal);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotal.setBounds(10, 67, 90, 23);
		panelCart.add(lblTotal);
		
		JLabel lblCash = new JLabel("Cash:");
		lblCash.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCash.setBounds(10, 114, 90, 23);
		panelCart.add(lblCash);
		
		JLabel lblTax = new JLabel("Tax $ :");
		lblTax.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTax.setBounds(291, 22, 90, 23);
		panelCart.add(lblTax);
		
		JLabel lblDis = new JLabel("Dis $ :");
		lblDis.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDis.setBounds(523, 22, 90, 23);
		panelCart.add(lblDis);
		
		JLabel lblBal = new JLabel("Bal :");
		lblBal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBal.setBounds(523, 67, 90, 23);
		panelCart.add(lblBal);
		
		JButton btnPay = new JButton("Pay");
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/**
				 * PAY BILL BUTTON get text from totaal field and calculate how much 
				   was paid with cash from textfieldCash and calculate Balance and setText to textfieldBalance
			 */
				
				double tot = Double.valueOf(textFieldTotal.getText());
				double pay = Double.valueOf(textFieldCash.getText());
				
				double bal = pay - tot;
				
				// Round on two decimal places FIELD BALANCE
				DecimalFormat dff = new DecimalFormat (".00");
				String d3 = dff.format(bal);
				
				// Showing value in Balance textFieldBal after calculating pay minus total
				textFieldBal.setText(String.valueOf(d3).replace(",", "."));
				
				//This action activating the method which showing the BILL text in field JTextPane
				drwobill();
				textdocument();
			}
		});
		
		
		
		btnPay.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnPay.setBounds(338, 94, 143, 94);
		panelCart.add(btnPay);
		
		//This button was made for printing BILL from JTexPanel
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					JTextPaneBill.print();
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnPrint.setBounds(529, 127, 187, 47);
		panelCart.add(btnPrint);
		
		textFieldSubTotal = new JTextField();
		textFieldSubTotal.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldSubTotal.setFont(new Font("Tahoma", Font.PLAIN, 19));
		textFieldSubTotal.setText("0");
		textFieldSubTotal.setEditable(false);
		textFieldSubTotal.setBounds(115, 22, 152, 25);
		panelCart.add(textFieldSubTotal);
		textFieldSubTotal.setColumns(10);
		
		textFieldTotal = new JTextField();
		textFieldTotal.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTotal.setFont(new Font("Tahoma", Font.PLAIN, 19));
		textFieldTotal.setText("0");
		textFieldTotal.setEditable(false);
		textFieldTotal.setColumns(10);
		textFieldTotal.setBounds(115, 70, 152, 25);
		panelCart.add(textFieldTotal);
		
		textFieldCash = new JTextField();
		textFieldCash.setFont(new Font("Tahoma", Font.PLAIN, 19));
		textFieldCash.setColumns(10);
		textFieldCash.setBounds(115, 117, 152, 25);
		panelCart.add(textFieldCash);
		
		textFieldTax = new JTextField();
		textFieldTax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// activate a Method cart_total with JTextFieldTAX
				cart_total();
			}
		});
		textFieldTax.setText("2.77");
		textFieldTax.setFont(new Font("Tahoma", Font.PLAIN, 19));
		textFieldTax.setColumns(10);
		textFieldTax.setBounds(361, 22, 152, 25);
		panelCart.add(textFieldTax);
		
		textFieldDis = new JTextField();
		textFieldDis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// activate a Method cart_total with JTextFieldDis
				cart_total();
			}
		});
		textFieldDis.setText("5.99");
		textFieldDis.setFont(new Font("Tahoma", Font.PLAIN, 19));
		textFieldDis.setColumns(10);
		textFieldDis.setBounds(588, 22, 143, 25);
		panelCart.add(textFieldDis);
		
		textFieldBal = new JTextField();
		textFieldBal.setText("0");
		textFieldBal.setFont(new Font("Tahoma", Font.PLAIN, 19));
		textFieldBal.setEditable(false);
		textFieldBal.setColumns(10);
		textFieldBal.setBounds(588, 67, 143, 25);
		panelCart.add(textFieldBal);
	
	}
}
