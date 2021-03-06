package VideoGame_Discounts;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.FileInputStream;
import java.io.InputStream;
 
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SimpleGUI extends ParseWebsites{

	String[] titles = {"\"To feel cheap, is to feel exquisite.\" - Probably someone good with money", 
			  "$$$ Let's save us some money boys $$$",
			  "Don't spend all your money in one place...or do! Whatever!", 
			  "HEYYEYAAEYAAAEYAEYAA",
			  "Got the goods?"};
	
	private int num = 0;
	private AudioStream sound;

	public void createGUI() 
	{
		int randNum = ThreadLocalRandom.current().nextInt(0, titles.length);
		JFrame frame = new JFrame(titles[randNum]);
		frame.setLayout(new BorderLayout());
		frame.setSize(1280,720);
		
		{
			// Open an input stream to the audio file.
			try {
				InputStream in = new FileInputStream("Layer Cake.wav");
				AudioStream as = new AudioStream(in);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		ParseWebsites data = new ParseWebsites();
		addComponentsToPane(frame, data);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	    
	public void addComponentsToPane(final Container pane, ParseWebsites deals) 
	{
	    // create header JPanel and add a statusLabel
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout());
		JLabel headerText = new JLabel();
		headerText.setText("Welcome to the Main Menu!");
		header.add(headerText);
		
	    // create controls JPanel and buttons, then add the buttons
		JPanel controls = new JPanel();
		controls.setLayout(new FlowLayout());
		
		// create the music button and its necessary components
		JButton music = new JButton("Shop Theme: OFF");
		
		// create website buttons and exit buttons
		JButton reddit = new JButton("Reddit");
		JButton cheap = new JButton("CheapAssGamer");
		JButton games = new JButton("Gamesdeal");
		JButton green = new JButton("GreenManGaming");
		JButton steam = new JButton("Steam");
		JButton exit = new JButton("Exit");
		controls.add(music);
		controls.add(reddit);
		controls.add(cheap);
		controls.add(games);
		controls.add(green);
		controls.add(steam);
		controls.add(exit);
		
		// create JList, where deals are to be displayed
		DefaultListModel<String> listModel = new DefaultListModel<String>();
	    listModel.addElement("Click one of the buttons to start retrieving data!");
	    JList<String> list = new JList<String>(listModel);
	    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    list.setLayoutOrientation(JList.VERTICAL);
	    list.setVisibleRowCount(-1);
	    JScrollPane listScroller = new JScrollPane(list);
		
	    try {
 			
     		InputStream in = new FileInputStream("Layer Cake.wav");
 			AudioStream as = new AudioStream(in);
 			sound = as;
	    
	    } catch (Exception err) {
     		err.printStackTrace();
 	    }
	    
	 // action when "music" button is pressed		
	 		music.addActionListener(new ActionListener()
	 		{
	 	        public void actionPerformed(ActionEvent e)
	 	        {
	 	        		if (num == 0)
	 	        		{
	 	        			AudioPlayer.player.start(sound);
	 	        			music.setText("Shop Theme: ON");
	 	        		}
	 	        	
	 	        		else
	 	        		{
	 	        			AudioPlayer.player.stop(sound);
	 	        			music.setText("Shop Theme: OFF");
	 	        		}
	 	        		num = ((num+1) % 2);		
 	        	    }
	 	    });
	    
		// action when "Reddit" button is pressed		
		reddit.addActionListener(new ActionListener()
		{
	        public void actionPerformed(ActionEvent e)
	        {
	        	headerText.setText("Displaying deals from Reddit's front page.");
	        	deals.parseReddit();
	        	listModel.removeAllElements();
	        	
	        	for(String txt : deals.getReddit())
	        		listModel.addElement(txt);	
	        }
	    });

		// action when "CheapAssGamer" button is pressed
		cheap.addActionListener(new ActionListener()
		{
	        public void actionPerformed(ActionEvent e)
	        {
	        	headerText.setText("Displaying deals from CheapAssGamer's front page.");
	        	deals.parseCheap();
	        	listModel.removeAllElements();
	        	
	        	for(String txt : deals.getCheap())
	        		listModel.addElement(txt);
	        }
	    });
		
		// action when "Gamesdeal" button is pressed
		games.addActionListener(new ActionListener()
		{
	        public void actionPerformed(ActionEvent e)
	        {
	        	headerText.setText("Displaying deals from Gamesdeal's \"Top Deals\" and \"Best Sellers\" pages.");
	        	deals.parseGamesdeal();
	        	listModel.removeAllElements();
	        	
	        	for(String txt : deals.getGamesdeal())
	        		listModel.addElement(txt);
	        }
	    });
		
		// action when "GreenManGaming" button is pressed
		green.addActionListener(new ActionListener()
		{
	        public void actionPerformed(ActionEvent e)
	        {
	        	headerText.setText("Displaying deals from GreenManGaming's \"Deal of the Day\" and \"Hot Deals\" pages.");
	        	deals.parseGreen();
	        	listModel.removeAllElements();
	        	
	        	for(String txt : deals.getGreen())
	        		listModel.addElement(txt);
	        }
	    });
		
		// action when "Steam" button is pressed
		steam.addActionListener(new ActionListener()
		{
	        public void actionPerformed(ActionEvent e)
	        {
	        	headerText.setText("Displaying the top 100 relevant deals from Steam's list of specials.");
	        	deals.parseSteam();
	        	listModel.removeAllElements();
	        	
	        	for(String txt : deals.getSteam())
	        		listModel.addElement(txt);
	        }
	    });
		
		// action when "Exit" button is pressed
		exit.addActionListener(new ActionListener()
		{
	        public void actionPerformed(ActionEvent e)
			{
	        	// Exit this application
	        	System.exit(0);
	        	
	        	if(num == 1)
	        		AudioPlayer.player.stop(sound);
	        }
	    });
		
	    // add the JPanels and JList to pane's BorderLayout
		pane.add(header, BorderLayout.NORTH);
		pane.add(listScroller, BorderLayout.CENTER);
		pane.add(controls, BorderLayout.SOUTH);
	}
}
