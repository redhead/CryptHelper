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

import cz.cvut.kbe.crypthelper.ui.MainWindow;
import cz.cvut.kbe.crypthelper.ui.NGramDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Radek Ježdík <jezdik.radek@gmail.com>
 */
class NGramController {

	private NGramDialog dialog;

	private String text;


	public NGramController(MainWindow window, String text) {
		this.dialog = new NGramDialog(window, false);
		this.text = text;

		setupListeners();
	}


	private void setupListeners() {
		dialog.getFindButton().addActionListener(new FindButtonListener());
	}


	private void find() {
		int min = (int) dialog.getMinimumSpinner().getValue();
		int max = (int) dialog.getMaximumSpinner().getValue();

		NGramFinder finder = new NGramFinder(min, max);
		Map<String, List<Integer>> distances = finder.find();

		Object[][] data = new Object[finder.size()][3];

		int i = 0;
		for(String ngram : distances.keySet()) {
			List<Integer> dists = distances.get(ngram);
			for(int d : dists) {
				data[i][0] = ngram;
				data[i][1] = d;
				data[i][2] = finder.getPrimeFactors(d);
				i++;
			}
		}

		NGramTableModel model = new NGramTableModel(data);
		dialog.getTable().setModel(model);
		dialog.getTable().setRowSorter(new TableRowSorter<>(model));
	}


	private class FindButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			find();
		}

	}


	private class NGramFinder {

		private Map<String, List<Integer>> map = new HashMap<>();

		private int min;

		private int max;

		private int size;

		private Map<Integer, String> primeFactors = new HashMap<>();


		public NGramFinder(int min, int max) {
			this.min = Math.max(min, 1);
			this.max = Math.max(max, this.min);
		}


		private Map<String, List<Integer>> find() {
			findIndices();
			return getDistances();
		}


		private void findIndices() {
			for(int i = 0; i < text.length(); i++) {
				char c = charAt(i);
				if(!isValid(c)) continue;

				getNGrams(i);
			}
		}


		private void getNGrams(int index) {
			StringBuilder sb = new StringBuilder();

			for(int i = 0, len = 0; len < max && (index + len) < text.length(); i++) {
				char x = charAt(index + i);

				if(!isValid(x)) continue;

				sb.append(x);
				len++;

				if(len >= min && len <= max) {
					String ngram = sb.toString();
					addNGram(ngram, index);
				}
			}
		}


		private Map<String, List<Integer>> getDistances() {
			Map<String, List<Integer>> distances = new HashMap<>();

			// iterate over all n-grams
			for(String ngram : map.keySet()) {
				List<Integer> distList = distances.get(ngram);
				if(distList == null) {
					distList = new ArrayList<>();
				}

				// compute distances between the same n-grams
				int lastDist = 0;
				List<Integer> indices = map.get(ngram);
				for(int i = 1; i < indices.size(); i++) {
					int idx0 = indices.get(i - 1);
					int idx1 = indices.get(i);
					int dist = idx1 - idx0;
					lastDist += dist;
					distList.add(lastDist);
					size++;
				}

				if(!distList.isEmpty()) {
					distances.put(ngram, distList);
				}
			}

			return distances;
		}


		private void addNGram(String ngram, int index) {
			List<Integer> indexes = map.get(ngram);
			if(indexes == null) {
				indexes = new ArrayList<>();
				map.put(ngram, indexes);
			}
			indexes.add(index);
		}


		private char charAt(int i) {
			char c = text.charAt(i);
			return Character.toUpperCase(c);
		}


		private boolean isValid(char c) {
			return (c >= 'A' && c <= 'Z');
		}


		private int size() {
			return size;
		}


		private String getPrimeFactors(final int n) {
			if(primeFactors.containsKey(n)) {
				return primeFactors.get(n);
			}

			Set<Integer> factors = new HashSet<>();
			int d = 2, x = n;
			while(x > 1) {
				while(x % d == 0) {
					factors.add(d);
					x /= d;
				}
				d++;
			}

			String str = "";
			for(int p : factors) {
				str += (str.isEmpty() ? "" : " ") + p;
			}

			primeFactors.put(n, str);
			return str;
		}

	}


	private static class NGramTableModel extends AbstractTableModel {

		private Object[][] data;

		private String[] colNames = new String[] {
			"N-Gramy",
			"Vzdálenost",
			"Prvočíselný rozklad"
		};


		public NGramTableModel(Object[][] data) {
			this.data = data;
		}


		@Override
		public int getRowCount() {
			return data.length;
		}


		@Override
		public String getColumnName(int column) {
			return colNames[column];
		}


		@Override
		public int getColumnCount() {
			return 3;
		}


		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}


		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if(columnIndex == 1) return Integer.class;
			return Integer.class;
		}

	}

}
