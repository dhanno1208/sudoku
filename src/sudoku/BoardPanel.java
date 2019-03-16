package sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.color.*;

public class BoardPanel extends JPanel
{
	private GameState state;

	void setup()
	{
		setPreferredSize(new Dimension(648, 648));
		this.setBackground(Color.white);

		this.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {
				int col = e.getX()/Cell.cellSide;
				int row = e.getY()/Cell.cellSide;

				int c = e.getX()/(Cell.cellSide/3);
				int r = e.getY()/(Cell.cellSide/3);

				Cell clone = (Cell) state.getCell(row, col).clone();
				clone.click(r-(row*3), c-(col*3), true);
				state.setCell(clone, row, col);

				repaint();
			}
		});
	}

	BoardPanel()
	{
		state = new GameState(null);
		setup();
	}

	BoardPanel(int[][] generatedBoard)
	{
		state = new GameState(generatedBoard);
		setup();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for(int r=0; r<9; r++) {
			for(int c=0; c<9; c++) {
				state.getCell(r, c).draw(g, r, c);
			}
		}
		drawGrid(g);
	}

	private void drawGrid(Graphics g) {
		for(int x=0; x<648; x+=72) {
			if(x%216==0) {
				g.fillRect(x-2, 0, 5, 648);
			}else {
				g.fillRect(x-1, 0, 3, 648);
			}
		}
		for(int y=0; y<648; y+=72) {
			if(y%216==0) {
				g.fillRect(0, y-2, 648, 5);
			}else {
				g.fillRect(0, y-1, 648, 3);
			}
		}
	}

	public static void main(String[] str) {
		JFrame frame = new JFrame("Test");
		BoardPanel b = new BoardPanel();
		frame.setContentPane(b);
		frame.pack();
		frame.setVisible(true);
	}
}