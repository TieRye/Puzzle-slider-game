import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel{

	public static Cell[][] board;
	
	private ArrayList<Cell> completeBoard = new ArrayList<Cell>(); 
	public final int dimension;
	private int x, y;
	private final int figureWidth, figureHeight;
	private JLabel label;
	public Board(int dimension, BufferedImage puzzle){
		this.setPreferredSize(new Dimension(410, 0));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		this.setBackground(Color.BLACK);
		this.dimension = dimension;
		board = new Cell[dimension][dimension];
		x = 0;
		y = 0;
		figureWidth = puzzle.getWidth()/dimension;
		figureHeight = puzzle.getHeight()/dimension;

		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		for(int i=0; i<dimension; i++){
			for(int j=0; j<dimension; j++){
				if(i == dimension - 1 && j == dimension -1){			
					continue;
				}
				completeBoard.add(new Cell(i, j, new Figure(i, j, new ImageIcon(puzzle.getSubimage(x, y, figureWidth, figureHeight)), dimension)));
				
				x += figureWidth;
			}
			x = 0;
			y += figureHeight;
		}
		messBoard();
		
		remover();
		
	}
	public void messBoard(){
		
		Random randomGenerator = new Random();
		ArrayList<Cell> cellStore = new ArrayList<Cell>(completeBoard);
		
		for(int i = 0; i<dimension; i++){
			for(int j = 0; j<dimension; j++){
				if(i == dimension-1 && j == dimension-1){
					board[i][j] = new Cell(i, j);
					continue;
				}
				int randomIndex = randomGenerator.nextInt(completeBoard.size());
				completeBoard.get(randomIndex).getFigure().setPos(i, j);
				board[i][j] = new Cell(i, j, completeBoard.get(randomIndex).getFigure());
				completeBoard.remove(randomIndex);

			}
		}
		completeBoard = cellStore;
		remover();
	}
	public void updateBoard(){
		
		for(int i = 0; i<dimension; i++){
			for(int j = 0; j<dimension; j++){	
				if(board[i][j].getFigure() == null){
					label = new JLabel();
					label.setPreferredSize(new Dimension(figureWidth, figureHeight));
					this.add(label);
					continue;
				}
				this.add(board[i][j].getFigure());
			}
		}
		Puzzle.getContainer().validate();
	}
	public void remover(){
		this.removeAll();
		updateBoard();
	}
}
