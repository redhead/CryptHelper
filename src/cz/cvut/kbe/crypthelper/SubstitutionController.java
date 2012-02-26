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

import cz.cvut.kbe.crypthelper.ui.SubstitutionWindow;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.*;


/**
 *
 * @author Radek Ježdík <jezdik.radek@gmail.com>
 */
public class SubstitutionController {

	SubstitutionWindow window;

	private boolean updating = false;

	private JTextArea inputText;

	private JTextPane outputText;

	private JTable table;


	public SubstitutionController(String text) {
		window = new SubstitutionWindow();

		inputText = window.getInputText();
		outputText = window.getOutputText();
		table = window.getTable();

		setupListeners();

		inputText.setText(text);
	}


	private void setupListeners() {
		table.getModel().addTableModelListener(new SubstitutionTableListener());

		inputText.getDocument().addDocumentListener(new InputTextListener());
		outputText.addCaretListener(new OutputCaretListener());

	}


	private void updateOutput() {
		if(!isValidSubstitution()) {
			return;
		}
		updating = true;

		outputText.setText("");

		String en = inputText.getText();

		for(int i = 0; i < en.length(); i++) {
			char c = en.charAt(i);
			char uc = Character.toUpperCase(c);
			char append = c;

			boolean encrypted = true;

			if(uc >= 'A' && uc <= 'Z') {
				int idx = table.getColumn(uc + "").getModelIndex();
				String value = (String) table.getModel().getValueAt(0, idx);

				if(value != null && value.length() == 1) {
					append = value.charAt(0);
					encrypted = false;
				}
			} else {
				encrypted = false;
			}
			window.appendChar(append, encrypted);
		}
		updating = false;
	}


	private boolean isValidSubstitution() {
		Set<Character> set = new HashSet<>();
		for(int i = 0; i < table.getColumnCount(); i++) {
			String s = (String) table.getValueAt(0, i);
			if(s == null || s.length() != 1) continue;

			char c = s.charAt(0);
			if(set.contains(c)) {
				JOptionPane.showMessageDialog(window, "Písmeno '" + c + "' se vyskytuje v tabulce vícekrát.");
				return false;
			}
			set.add(c);
		}
		return true;
	}


	private class SubstitutionTableListener implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent e) {
			updateOutput();
		}

	}


	private class InputTextListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateOutput();
		}


		@Override
		public void removeUpdate(DocumentEvent e) {
			updateOutput();
		}


		@Override
		public void changedUpdate(DocumentEvent e) {
			updateOutput();
		}

	}


	private class OutputCaretListener implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent e) {
			if(updating) return;

			int start = e.getDot();
			int end = e.getMark();

			if(start > end) {
				int temp = end;
				end = start;
				start = temp;
			}
			if(start == 0 && end == inputText.getText().length()) {
				// whole text selected, assume the user wants to copy the text
				return;
			}

			inputText.setSelectionStart(start);
			inputText.setSelectionEnd(end);
			inputText.requestFocus();
		}

	}

}
