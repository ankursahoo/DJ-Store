/*Ankur Sahoo
 * Program DJ Store: This program manages a store that adds, sells, and displays records */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.table.DefaultTableCellRenderer;

public class Sahoo_DJStore extends JFrame implements ActionListener{

	private JTextField skuText;
	private JTextField albumText;
	private JTextField artistText;
	private JTextField priceText;
	private JTextField quantityText;
	private JTextField sellQuantity;

	private DefaultTableModel inventInfo;
	private DefaultComboBoxModel comboInfo;

	private JComboBox artistAlbums;

	private JLabel availableAmount;
	private JLabel priceAmount;

	private double selectedItemPrice;
	private JFileChooser fileChooser;
	private File txtFile;

	public Sahoo_DJStore(){

		txtFile = null;

		setTitle("DJ Emporium -- Untitled");
		setSize(500,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		PicPanel entireStore = new PicPanel("store.jpg");
		entireStore.setLayout(null);

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");

		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(this);
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(this);
		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(this);
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);

		// adds file items to the file menu
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(close);
		fileMenu.add(exit);

		menuBar.add(fileMenu);

		fileChooser = new JFileChooser();

		JLabel djEmpo = new JLabel();
		djEmpo = new JLabel("The DJ Emporium");
		djEmpo.setBounds(120, 20, 220, 100);
		djEmpo.setForeground(Color.WHITE);
		djEmpo.setFont(new Font("Calibri",Font.BOLD,30));

		JTabbedPane store = new JTabbedPane();
		store.setBounds(40,110,400,200);

		//inventory
		JPanel inventory = new JPanel();		
		PicPanel inventoryPic = new PicPanel("inventory.jpg");
		inventoryPic.setLayout(null);
		inventoryPic.setBounds(40,100,400,200);
		inventoryPic.add(inventory);

		comboInfo = new DefaultComboBoxModel();

		selectedItemPrice = 0.0;

		//headers
		String[] colNames = {"Sku","Artist","Album","Price","Quantity"};

		inventInfo = new DefaultTableModel(colNames,0)		
		{
			//only include if you don't want user
			//to be allowed to edit cells
			public boolean isCellEditable(int row, int column){
				return false;
			}

		};

		JTable inventTable = new JTable(inventInfo);

		//don't let them reorder the columns
		inventTable.getTableHeader().setReorderingAllowed(false);

		inventTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		inventTable.getColumnModel().getColumn(3).setPreferredWidth(30);

		JScrollPane inventScroll = new JScrollPane(inventTable);
		inventScroll.setBounds(20,25,350,100);
		inventoryPic.add(inventScroll);

		//addRecord
		JPanel addRecord = new JPanel();		
		PicPanel addRecordPic = new PicPanel("record.png");
		addRecordPic.setLayout(null);
		addRecordPic.setBounds(40,100,400,200);
		addRecordPic.add(addRecord);

		JLabel sku = new JLabel("Sku");
		sku.setBounds(15,0,50,50);
		sku.setForeground(Color.WHITE);
		addRecordPic.add(sku);

		skuText = new JTextField(20);
		skuText.setBounds(70,15,100,25);
		skuText.addActionListener(this);
		addRecordPic.add(skuText);

		JLabel album = new JLabel("Album");
		album.setForeground(Color.WHITE);
		album.setBounds(15,50,50,50);
		addRecordPic.add(album);

		albumText = new JTextField(20);
		albumText.setBounds(70,65,100,25);
		albumText.addActionListener(this);
		addRecordPic.add(albumText);

		JLabel artist = new JLabel("Artist");
		artist.setForeground(Color.WHITE);
		artist.setBounds(15,100,50,50);
		addRecordPic.add(artist);

		artistText = new JTextField(20);
		artistText.setBounds(70,115,100,25);
		artistText.addActionListener(this);
		addRecordPic.add(artistText);

		JLabel price = new JLabel("Price");
		price.setForeground(Color.WHITE);
		price.setBounds(215,0,50,50);
		addRecordPic.add(price);

		priceText = new JTextField(20);
		priceText.setBounds(275,15,100,25);
		//priceText.addActionListener(this);
		addRecordPic.add(priceText);

		JLabel quantity = new JLabel("Quantity");
		quantity.setForeground(Color.WHITE);
		quantity.setBounds(215,50,50,50);
		addRecordPic.add(quantity);

		quantityText = new JTextField(20);
		quantityText.setBounds(275,65,100,25);
		quantityText.addActionListener(this);
		addRecordPic.add(quantityText);

		JButton add = new JButton("Add");
		add.setBounds(265,110,70,25);
		add.addActionListener(this);
		addRecordPic.add(add);

		//sell Record
		JPanel sellRecord = new JPanel();		
		PicPanel sellRecordPic = new PicPanel("sellRecord.jpg");
		sellRecordPic.setLayout(null);
		sellRecordPic.add(sellRecord);

		artistAlbums = new JComboBox(comboInfo);
		artistAlbums.addActionListener(this);
		artistAlbums.setBounds(10,10,250,20);
		sellRecordPic.add(artistAlbums);

		JLabel available = new JLabel("Available");
		available.setBounds(275,5,70,50);
		available.setForeground(Color.WHITE);
		sellRecordPic.add(available);

		availableAmount=new JLabel("");
		availableAmount.setBounds(340,5,70,50);
		availableAmount.setForeground(Color.WHITE);
		sellRecordPic.add(availableAmount);

		JLabel sellPrice = new JLabel("Price");
		sellPrice.setBounds(275,30,70,50);
		sellPrice.setForeground(Color.WHITE);
		sellRecordPic.add(sellPrice);

		priceAmount = new JLabel("");
		priceAmount.setBounds(340,30,70,50);
		priceAmount.setForeground(Color.WHITE);
		sellRecordPic.add(priceAmount);

		JLabel sellQuanLabel = new JLabel("Quantity");
		sellQuanLabel.setBounds(275,75,70,50);
		sellQuanLabel.setForeground(Color.WHITE);
		sellRecordPic.add(sellQuanLabel);

		sellQuantity = new JTextField(20);
		sellQuantity.setBounds(335,90,50,20);
		sellQuantity.addActionListener(this);
		sellRecordPic.add(sellQuantity);

		JButton sell = new JButton("Sell");
		sell.setBounds(300,130,70,25);
		sell.addActionListener(this);
		sellRecordPic.add(sell);

		store.add("Inventory", inventoryPic);
		store.add("Add A Record", addRecordPic);
		store.add("Sell A Record", sellRecordPic);

		entireStore.add(djEmpo);
		entireStore.add(store);
		entireStore.setBounds(0,0,500,400);
		add(entireStore);

		setJMenuBar(menuBar);

		setVisible(true);
	}

	//Performs an action depending on what the user does
	public void actionPerformed(ActionEvent ae) {

		//if the user wants to open a file
		if(ae.getActionCommand().equals("Open"))
		{
			open();
		}

		//if the user wants to close a file
		else if(ae.getActionCommand().equals("Close"))
		{
			if(getTitle().indexOf("*")!=-1)
			{
				promptToSave();
			}

			setTitle("DJ Emporium -- Untitled");
			removeData();
			txtFile = null;
		}

		//if the user wants to save a file
		else if(ae.getActionCommand().equals("Save")){
			whereToSave();
		}

		//if the user wants to exit the program
		else if(ae.getActionCommand().equals("Exit"))
		{
			//if they haven't saved yet
			if(getTitle().indexOf("*")!=-1)
			{
				promptToSave();
			}

			System.exit(-1);

		}

		//if the user wants to add a record
		else if(ae.getActionCommand().equals("Add"))
		{
			addRecord();
		}

		//if the user clicks the jcombobox
		else if (ae.getSource()==artistAlbums)
		{
			int index = artistAlbums.getSelectedIndex();
			
			//changes the labels
			if(index!=-1)
			{
				availableAmount.setText("" + inventInfo.getValueAt(index, 4));
				selectedItemPrice =  ((Double)inventInfo.getValueAt(index, 3));
				priceAmount.setText("" + selectedItemPrice);
			}
		}

		//if the user wants to sell records
		else if(ae.getActionCommand().equals("Sell"))
		{
			sellRecord();
		}
	}

	//opens and stores a file's contents into the table and jcombobox
	private void open(){

		removeData();

		int result = fileChooser.showOpenDialog(null);
		Scanner fileIn = null;

		//the user clicks a file
		if(result == JFileChooser.APPROVE_OPTION){

			txtFile = fileChooser.getSelectedFile();
			setTitle("DJ Emporium--"+txtFile.getName());

			try{

				fileIn = new Scanner(txtFile);

			}catch(FileNotFoundException e){

				System.out.println("File Not Found");
				System.exit(-1);

			}

			//adds the contents of the file to the table and combo box
			while(fileIn.hasNextLine()){

				int sku = fileIn.nextInt();
				fileIn.nextLine();
				String artist = fileIn.nextLine();
				String album = fileIn.nextLine();
				double price = fileIn.nextDouble();
				int quantity = fileIn.nextInt();

				int exists = alreadyExists(sku);

				//if the record already exists, update the quantity
				if(exists!=-1)
				{
					updateQuantity(exists,quantity);
				}

				else
				{

					Object[] row = {sku,artist,album,price,quantity};
					inventInfo.addRow(row);
					comboInfo.addElement(artist+" --- "+album);	
				}
			}

		}

	}

	//removes data from everything in the program (other than the jtextfields and quantity textfield)
	private void removeData(){

		//removes data from the table
		for(int i = 0; i<inventInfo.getRowCount();i++)
		{
			inventInfo.removeRow(i);
			i--;
		}

		comboInfo.removeAllElements();
		
		availableAmount.setText("");
		priceAmount.setText("");
	}



	//Asks user is they want to save
	private void promptToSave(){

		int reply = JOptionPane.showConfirmDialog(null, "Do you want to save?","",JOptionPane.YES_NO_OPTION);

		//if the user does want to save
		if(reply == JOptionPane.YES_OPTION){
			whereToSave();
		}
	}

	//decides where to save, and performs the appropriate action
	private void whereToSave(){

		//If a file is open ,save to that file
		if(txtFile!=null){

			saveFile(txtFile);

		}

		//If no file is open, the user chooses where to save it
		else{

			int result = fileChooser.showSaveDialog(null);

			if(result == JFileChooser.APPROVE_OPTION){

				txtFile = fileChooser.getSelectedFile();
				saveFile(txtFile);

			}

		}

	}

	//Saves the data from the table to a file
	private void saveFile(File file){

		try{
			//Adds the contents of the table to the file
			FileWriter outFile = new FileWriter(file);

			int row=0;
			int col=0;

			//adds all rows except the last to the file
			for(row = 0; row<inventInfo.getRowCount()-1; row++)
			{
				for(col = 0; col<inventInfo.getColumnCount(); col++)
				{
					outFile.write((inventInfo.getValueAt(row, col))+"\r\n");
				}
			}

			//adds the last row, but excludes the last column
			for(col = 0; col<inventInfo.getColumnCount()-1; col++)
			{
				outFile.write((inventInfo.getValueAt(row, col))+"\r\n");
			}

			//adds the last column
			outFile.write(inventInfo.getValueAt(row, col)+ "");

			outFile.close();

		}catch(IOException e){

			System.out.println("File Not Found!");
			System.exit(-1);

		}

		//Removes the * from the title, if there is one
		if(getTitle().indexOf("*")!=-1)
		{
			setTitle(getTitle().substring(0, getTitle().length()-1));
		}
	}

	//adds a record
	private void addRecord()
	{
		//checks for invalid input
		try{

			DecimalFormat dec = new DecimalFormat("#.00");

			int validSku = Integer.parseInt(skuText.getText());
			int validQuantity = Integer.parseInt(quantityText.getText());
			double validPrice = Double.parseDouble(dec.format(Double.parseDouble(priceText.getText())));

			if(validSku<0 || validQuantity<0 || validPrice<0)
			{
				JOptionPane.showMessageDialog(null, "You did not enter a valid number", "Alert!", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String validAlbum = albumText.getText();
			String validArtist = artistText.getText();

			if(validAlbum.length()==0 || validArtist.length()==0)
			{
				JOptionPane.showMessageDialog(null, "You did not enter all the required information", "Alert!", JOptionPane.ERROR_MESSAGE);
				return;
			}

			//clears the textfields
			skuText.setText("");
			albumText.setText("");
			artistText.setText("");
			priceText.setText("");
			quantityText.setText("");

			if(getTitle().indexOf("*")==-1)
			{
				setTitle(getTitle()+"*");
			}

			int row = alreadyExists(validSku);

			//if the record already exists
			if(row!=-1)
			{
				int newVal = updateQuantity(row,validQuantity);
				JOptionPane.showMessageDialog(null, "Quantity of the record is changed to "+newVal,"",JOptionPane.PLAIN_MESSAGE);
				return;
			}

			//stores everything into the table and jcomboBox
			Object[] rows = {validSku,validAlbum,validArtist,validPrice, validQuantity};

			inventInfo.addRow(rows);

			comboInfo.addElement(rows[1] + "---" + rows[2]);

			JOptionPane.showMessageDialog(this, "You successfully added the record","Success", JOptionPane.PLAIN_MESSAGE);

		}catch(NumberFormatException e){

			JOptionPane.showMessageDialog(null, "Incorrect Number Format","Error",JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	//if a record is found in the table, return its position; returns -1 otherwise
	private int alreadyExists(int sku){

		//searches through the table for the record
		for(int row = 0; row<inventInfo.getRowCount();row++ )
		{
			if((Integer)(inventInfo.getValueAt(row, 0))==(sku)){
				return row;
			}
		}

		return -1;

	}

	//updates the quantity
	private int updateQuantity(int row, int quantity)
	{
		int newVal = ((Integer)(inventInfo.getValueAt(row, 4)))+quantity;
		inventInfo.setValueAt(newVal, row, 4);
		return newVal;
	}

	//lets the user sell records
	private void sellRecord()
	{
		int quantity = 0;
		int recordQuantity = 0;
		DecimalFormat dec = new DecimalFormat("#.00");

		recordQuantity = (Integer)(inventInfo.getValueAt(artistAlbums.getSelectedIndex(),4));

		//checks for invalid input
		try
		{
			quantity = Integer.parseInt(sellQuantity.getText());
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Incorrect Number Format","Error",JOptionPane.WARNING_MESSAGE);
			return;
		}

		if((quantity>recordQuantity||quantity<0)){

			JOptionPane.showMessageDialog(null, "Incorrect Quantity!","Error",JOptionPane.WARNING_MESSAGE);
			return;
		}

		//calculates amount due and updates quantity and jlabels
		double toPay = selectedItemPrice*quantity;
		JOptionPane.showMessageDialog(null, "Amount Due: "+dec.format(toPay),"",JOptionPane.INFORMATION_MESSAGE);
		int amountLeft = updateQuantity(artistAlbums.getSelectedIndex(), (-1)*quantity);
		availableAmount.setText(amountLeft+ "");
		sellQuantity.setText("");

		if(getTitle().indexOf("*")==-1)
		{
			setTitle(getTitle()+"*");
		}

	}

	public static void main(String[] args){
		Sahoo_DJStore store = new Sahoo_DJStore();

	}

	class PicPanel extends JPanel{

		private BufferedImage image;
		private int w,h;
		public PicPanel(String fname){

			//reads the image
			try {
				image = ImageIO.read(new File(fname));
				w = image.getWidth();
				h = image.getHeight();

			} catch (IOException ioe) {
				System.out.println("Could not read in the pic");
				System.exit(0);
			}

		}

		public Dimension getPreferredSize() {
			return new Dimension(w,h);
		}
		//this will draw the image
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image,0,0,this);
		}

	}
}

