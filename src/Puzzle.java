import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;

public class Puzzle extends JFrame {
	//=============================== panels
	private static JPanel puzzelArea;
	private JPanel timeMoves;
	//=============================== images and icons
	private Image windowIcon = ImageLoader.loadImage("/puzzleIcon.png");
	private BufferedImage playIcon, stopIcon, retryIcon, newGameIcon; 
	private static BufferedImage def = ImageLoader.loadImage("/default.jpg");
	//=============================== toolbar and its items
	private JToolBar toolbar = new JToolBar(); //horizontal aligment by default
	private JButton playStopButton, retryButton, newGameButton;
	//=============================== labels
    private JLabel time = new JLabel(" 0 : 0 : 0");
	private JLabel owner = new JLabel("Puzzle Project");
	private static int moveCount = 0;
	private static JLabel moves = new JLabel(" "+moveCount+" ");
	private JLabel timeTitle = new JLabel(" Time ");
	private JLabel movesTitle = new JLabel("      Moves      ");
	//=============================== final values
	private final int width = 670;
	private final int height = 485;
	private final int iconSize = 30;
	private final int fontSize = 35;
	private final int delay = 1000;
	//=============================== chronometer values
	private int seconds = 0;
	private int minutes = 0;
	private int hours = 0;
	//=============================== board, miniImage and timer (statics)
	private static JButton miniImage = new JButton(new ImageIcon(def.getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
    
	private static Board board = null;
	private static Timer chronometer;
	private static Container container;
	
	public Puzzle(){
		//=============================== set window values
		this.setTitle("Puzzle");
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setResizable(false);
		this.setIconImage(windowIcon);
		container = this.getContentPane();
		chronometer = new Timer(delay, new IconTimerLitener());
		//=============================== load all images and icons
		playIcon = ImageLoader.loadImage("/playIcon.png");
		stopIcon = ImageLoader.loadImage("/stopIcon.png");
		retryIcon = ImageLoader.loadImage("/retryIcon.png");
		newGameIcon = ImageLoader.loadImage("/newIcon.png");	
		//=============================== Initialize all toolbar items
		playStopButton = new JButton(new ImageIcon(stopIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT)));
		playStopButton.setName("stop");
		playStopButton.addActionListener(new IconTimerLitener());
		
		retryButton = new JButton(new ImageIcon(retryIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT)));
		retryButton.setName("retry");
		retryButton.addActionListener(new IconTimerLitener());
		
		newGameButton = new JButton(new ImageIcon(newGameIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT)));
		newGameButton.setName("new");
		newGameButton.addActionListener(new IconTimerLitener());
		//=============================== initialize panels
		puzzelArea = new JPanel();
		puzzelArea.setOpaque(true);
		puzzelArea.setBackground(Color.BLACK);
		
		timeMoves = new JPanel();
		timeMoves.setLayout(new FlowLayout(FlowLayout.CENTER));
		timeMoves.setOpaque(true);
		timeMoves.setBackground(Color.BLACK);
		
		//=============================== add buttons to the toolbar
		toolbar.add(newGameButton);
		toolbar.add(playStopButton);
		toolbar.add(retryButton);
		
		owner.setFont(new Font("Times", Font.BOLD, fontSize));
		owner.setForeground(Color.BLUE);
		
		toolbar.add(owner);
		//=============================== set labels properties
		time.setForeground(Color.WHITE);
		time.setFont(new Font("Times", Font.BOLD, fontSize));
		
		moves.setForeground(Color.WHITE);
		moves.setFont(new Font("Times", Font.BOLD, fontSize));
		
		timeTitle.setFont(new Font("Times", Font.BOLD, fontSize));
		timeTitle.setForeground(Color.WHITE);
		
		movesTitle.setFont(new Font("Times", Font.BOLD, fontSize));
		movesTitle.setForeground(Color.WHITE);
		
		//=============================== add components to panels
		puzzelArea.add(new JLabel(new ImageIcon(def)));
		
		timeMoves.add(miniImage);

		timeMoves.add(timeTitle);
		timeMoves.add(time);
		timeMoves.add(movesTitle);
		timeMoves.add(moves);
		
		container.add(toolbar, BorderLayout.NORTH);
		container.add(puzzelArea, BorderLayout.WEST);
		container.add(timeMoves, BorderLayout.CENTER);
	}
	class IconTimerLitener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object comp = e.getSource();
			if(comp instanceof JButton){
				JButton button = (JButton)comp;
				if(button.getName().equals("stop")){
					if(board != null){
						chronometer.stop();
						button.setIcon(new ImageIcon(playIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT)));
						button.setName("play");
						board.setVisible(false);
					}
					

				}
                else if(button.getName().equals("play"))
                {
					if(board != null){
						chronometer.start();
						button.setIcon(new ImageIcon(stopIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT)));
						button.setName("stop");
						board.setVisible(true);
					}

				}
                else if(button.getName().equals("retry"))
                {
					moveCount = 0;
					seconds = 0;
					minutes = 0;
					hours = 0;
					time.setText(" "+hours+" : "+minutes+" : "+seconds+" ");
					moves.setText(" "+moveCount+" ");
					if(board != null)
						board.messBoard();
					
				}
                else if(button.getName().equals("new"))
                {
                    chronometer.stop();
               
                    StartPuzzle start = new StartPuzzle();
                   // chronometer.stop();
                    moveCount = 0;
                    seconds = 0;
                    minutes = 0;
                    hours = 0;
                        
				}
			}else if(comp instanceof Timer){
				seconds ++;
				if(seconds == 60){
					seconds = 0;
					minutes ++;
					if(minutes == 60){
						minutes = 0;
						hours ++;
					}
				}
				time.setText(" "+hours+" : "+minutes+" : "+seconds+" ");
				
			}
		}
		
	}
	public static void start(BufferedImage img, int level, BufferedImage mini){
		miniImage.setIcon(new ImageIcon(mini));
		if(board != null)
			container.remove(board);
		board = new Board(level, img);
		chronometer.start();
		container.remove(puzzelArea);
		container.add(board, BorderLayout.WEST);
		container.validate();
	}
	public static Board getBoard() {
		return board;
	}
	public static void add(){
		moveCount ++;
		moves.setText(" "+moveCount+" ");
	}
	public static Timer getTimer(){
		return chronometer;
	}
	public static Container getContainer(){
		return container;
	}
}
