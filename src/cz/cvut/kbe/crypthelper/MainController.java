/*
 * Software License Agreement (BSD License)
 * 
 * Copyright (c) 2012, Radek Ježdík <jezdik.radek@gmail.com>
 * All rights reserved.
 * 
 * Redistribution and use of this software in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *   Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 * 
 *   Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 * 
 *   Neither the name of Radek Ježdík <jezdik.radek@gmail.com> nor the names of its
 *   contributors may be used to endorse or promote products
 *   derived from this software without specific prior
 *   written permission by Radek Ježdík <jezdik.radek@gmail.com>
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package cz.cvut.kbe.crypthelper;

import cz.cvut.kbe.crypthelper.CharMap.CharEntry;
import cz.cvut.kbe.crypthelper.ui.CharacterTableModel;
import cz.cvut.kbe.crypthelper.ui.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


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
