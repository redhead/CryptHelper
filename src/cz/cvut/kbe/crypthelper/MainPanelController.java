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
import cz.cvut.kbe.crypthelper.ui.AlphabetEntryModel;
import cz.cvut.kbe.crypthelper.ui.CharacterTableModel;
import cz.cvut.kbe.crypthelper.ui.DefaultAlphabetEntryModel;
import cz.cvut.kbe.crypthelper.ui.MainPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 *
 * @author Radek Ježdík
 */
public class MainPanelController {

	private MainPanel panel;

	private CharacterTableModel tableModel;

	private AlphabetEntryModel alphaEntryModel;

	private boolean unmergable;

	private Substitution substitution;


	public MainPanelController(boolean unmergable) {
		this.unmergable = unmergable;

		panel = new MainPanel(this);
		tableModel = new CharacterTableModel();
		panel.setTableModel(tableModel);

		setupListeners();
	}


	public MainPanel getPanel() {
		return panel;
	}


	private void setupListeners() {
		panel.getCharTable().getModel().addTableModelListener(new TableListener());

		panel.getProcessButton().addActionListener(new ProcessButtonListener());

		panel.getPercentOption().addChangeListener(new ViewChangeListener());
		panel.getPerCountOption().addChangeListener(new ViewChangeListener());

		panel.getAlphabetSelect().addItemListener(new AlphaChangeListener());

		panel.getOffsetSlider().addChangeListener(new OffsetChangeListener());
	}


	public void processText() {
		CharMap map = getMap();
		CharEntry[] entries = map.getEntries();
		substitution = new Substitution(panel.getInputText().getText());
		tableModel.setData(entries);
		panel.setIndexOfCoincidence(map.getIndexOfCoincidence());
	}


	private void offsetSubstitution(int offset) {
		for(int i = 0; i < tableModel.size(); i++) {
			char from = tableModel.getCharacter(i);
			char to = from;
			if(alphaEntryModel != null) {
				to = alphaEntryModel.getCharacter(i);
			}
			substitution.set(from, to);
		}
	}


	public String getSubstituionText() {
		return substitution.toString();
	}


	private CharMap getMap() {
		String inputText = panel.getInputText().getText();
		return CharMap.fromString(inputText);
	}


	public void alphaOptionChanged(String name) {
		CharEntry[] entries = Alphabets.getEntriesFor(name);
		alphaEntryModel = new DefaultAlphabetEntryModel(entries);

		panel.setAlphaEntryModel(alphaEntryModel);
		panel.getOffsetSlider().setVisible(entries != null);
		panel.setChartData();
	}


	public boolean isUnmergable() {
		return unmergable;
	}


	private class ProcessButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			processText();
		}

	}


	private class ViewChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			boolean percent = panel.getPercentOption().isSelected();
			tableModel.setPercentView(percent);
		}

	}


	private class TableListener implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent e) {
			panel.setChartData();
		}

	}


	private class OffsetChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = 26 - panel.getOffsetSlider().getValue();
			offsetSubstitution(value);
			panel.setOffset(value);
		}

	}


	private class AlphaChangeListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				alphaOptionChanged((String) e.getItem());
			}
		}

	}

}
