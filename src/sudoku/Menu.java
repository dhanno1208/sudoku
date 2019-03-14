package sudoku;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Menu extends JPanel implements ActionListener{
	private JLabel fileName;
	private JButton hint, undo, redo, gen, solve, save, open;
	private JRadioButton a,b;
	private JLabel entry, solving;
	private PopUp p;
	public Menu() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		fileName = new JLabel();
		hint = new JButton("Hint");
		hint.setActionCommand("hint");
		hint.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(hint, c);

		undo = new JButton("Undo");
		undo.setActionCommand("undo");
		undo.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		this.add(undo, c);

		redo = new JButton("Redo");
		redo.setActionCommand("redo");
		redo.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		this.add(redo, c);

		c.insets = new Insets(0, 0, 0, 0);
		a = new JRadioButton();
		a.setActionCommand("entry");
		a.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 0;
		this.add(a, c);

		entry = new JLabel("Entry");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 0;
		this.add(entry, c);

		c.insets = new Insets(10, 10, 10, 10);
		gen = new JButton("Generate");
		gen.setActionCommand("gen");
		gen.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		this.add(gen, c);

		solve = new JButton("Solve");
		solve.setActionCommand("solve");
		solve.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		this.add(solve, c);

		save = new JButton("Save");
		save.setActionCommand("save");
		save.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		this.add(save, c);

		open = new JButton("Open");
		open.setActionCommand("open");
		open.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		this.add(open, c);

		c.insets = new Insets(0, 0, 0, 0);
		b = new JRadioButton();
		b.setActionCommand("solving");
		b.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 1;
		this.add(b, c);

		solving = new JLabel("Solving");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 1;
		this.add(solving, c);
		
		a.isSelected();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();
		if(eventName.equals("hint")) {
			p = new PopUp( "Are you sure you want a hint?", "Yes", "No");
		}
		if(eventName.equals("solve")) {
			p = new PopUp( "Are you sure you want to solve the puzzle?", "Yes", "No");
		}
		if(eventName.equals("gen")) {
			p = new PopUp( "Generate a new puzzle (All previous progress will be lost)", "Ok", "Cancel");
		}
		if(eventName.equals("save")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showSaveDialog(null);
			File f = fileChooser.getSelectedFile();
			try {
				Window.getGameState().save(f);
			} catch (Exception E) {
				new PopUp("Could not save file.", "Okay", "");
			}
		}

		if(eventName.equals("redo")) {
			if(Window.getGameState().redo_enabled()) {
				Window.getGameState().redo();
			}
		}
		if(eventName.equals("undo")) {
			if(Window.getGameState().undo_enabled()) {
				Window.getGameState().undo();
			}
		}
		if(eventName.equals("open")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(null);
			File f = fileChooser.getSelectedFile();
			try {
				Window.setGameState(GameState.load(f));
			} catch (Exception E) {
				new PopUp("Could not load file.", "Okay", "");
			}
		}

		if(eventName.equals("entry")) {
			//toggles the solving button (b) if it is selected already
			Window.entryMode();
			if(b.isSelected()) {
				b.setSelected(false);
			}
		}
		if(eventName.equals("solving")) {
			//toggles the entry button (a) if it is selected already
			Window.solvingMode();
			if(a.isSelected()) {
				a.setSelected(false);
			}
		}
	}
}
