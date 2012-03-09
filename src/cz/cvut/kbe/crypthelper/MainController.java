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

import cz.cvut.kbe.crypthelper.helper.PolyalphabetSplitter;
import cz.cvut.kbe.crypthelper.ui.MainPanel;
import cz.cvut.kbe.crypthelper.ui.MainWindow;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author Radek Ježdík <jezdik.radek@gmail.com>
 */
public class MainController {

	private MainWindow window;


	public MainController() {
		window = new MainWindow();
		new MainMenuController(this);
	}


	public void run() {
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setupListeners();
	}


	private void setupListeners() {
		window.getTabbedPane().addChangeListener(new TabChanged());
	}


	public void splitText() {
		if(getView().getAlphabetSelect().getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(window, "Nejdřív nastavte abecedu, kterou chcete použít.", "Chyba", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String str = JOptionPane.showInputDialog(window, "Na kolik částí chcete text rozdělit?");
		try {
			int x = Integer.parseInt(str);

			String text = getView().getInputText().getText();
			String[] splits = PolyalphabetSplitter.split(text, x);

			String title = getViewTitle();
			for(int i = 0; i < x; i++) {
				String tabTitle = title + "." + (i + 1);
				MainPanel panel = createTab(tabTitle, true);

				panel.getInputText().setText(splits[i]);
				panel.getController().changeAlphabet((String) getView().getAlphabetSelect().getSelectedItem());
				panel.getController().processText();
			}
		} catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(window, "Nebylo zadáno celé číslo.", "Chyba", JOptionPane.ERROR_MESSAGE);
		}
	}


	public void mergeText() {
		List<String> splits = new ArrayList<>();

		String title = window.getViewTitle();
		for(int i = 0;; i++) {
			String tabTitle = title + "." + (i + 1);
			MainPanel panel = window.getView(tabTitle);
			if(panel == null) break;

			String text = panel.getSubstitutedText();
			splits.add(text);
		}

		if(splits.isEmpty()) return;

		String merge = "";
		for(int i = 0; i < splits.get(0).length(); i++) {
			for(int j = 0; j < splits.size(); j++) {
				merge += splits.get(j).charAt(i);
			}
		}
		JOptionPane.showMessageDialog(window, merge);
	}


	public MainWindow getWindow() {
		return window;
	}


	public MainPanel getView() {
		return window.getView();
	}


	public String getViewTitle() {
		return window.getViewTitle();
	}


	public MainPanel createTab() {
		return createTab(null, false);
	}


	private MainPanel createTab(String title, boolean unmergable) {
		return window.createTab(title, unmergable);
	}


	public MainPanelController getViewController() {
		return getView().getController();
	}


	private class TabChanged implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JTabbedPane pane = (JTabbedPane) e.getSource();
			MainPanel panel = (MainPanel) pane.getSelectedComponent();

			boolean mergable = !panel.getController().isUnmergable();
			window.getMergeMenuItem().setEnabled(mergable);
			window.getSplitMenuItem().setEnabled(mergable);
		}

	}

}
