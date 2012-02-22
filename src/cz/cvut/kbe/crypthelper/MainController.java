/*
 */
package cz.cvut.kbe.crypthelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import cz.cvut.kbe.crypthelper.CharMap.CharEntry;
import cz.cvut.kbe.crypthelper.ui.CharacterTableModel;
import cz.cvut.kbe.crypthelper.ui.MainWindow;


/**
 *
 * @author Radek Ježdík
 */
class MainController {

	private MainWindow window;

	private CharacterTableModel tableModel;


	public MainController() {
		window = new MainWindow();

		tableModel = new CharacterTableModel();
		window.setTableModel(tableModel);

		setupListeners();
	}


	private void setupListeners() {
		window.getCharTable().getModel().addTableModelListener(new TableListener());

		window.getProcessButton().addActionListener(new ProcessButtonListener());
		window.getIndexButton().addActionListener(new IndexButtonListener());

		window.getPercentOption().addChangeListener(new ViewChangeListener());
		window.getPerCountOption().addChangeListener(new ViewChangeListener());

		window.getOffsetSlider().addChangeListener(new OffsetChangeListener());
	}


	private void processText() {
		CharMap map = getMap();
		CharEntry[] entries = map.getEntries();
		setData(entries);
	}


	private void setData(CharEntry[] entries) {
		tableModel.setData(entries);
	}


	void run() {
		window.setVisible(true);
		window.setLocationRelativeTo(null);
	}


	private CharMap getMap() {
		String inputText = window.getInputText().getText();
		return CharMap.fromString(inputText);
	}


	private class ProcessButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			processText();
		}

	}


	private class IndexButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CharMap map = getMap();
			double index = map.getIndexOfCoincidence();
			JOptionPane.showMessageDialog(window, "IC = " + index);
		}

	}


	private class ViewChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			boolean percent = window.getPercentOption().isSelected();
			tableModel.setPercentView(percent);
		}

	}


	private class TableListener implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent e) {
			window.setChartData();
		}

	}


	private class OffsetChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = window.getOffsetSlider().getValue();
			window.setOffset(value);
		}

	}

}
