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
package cz.cvut.kbe.crypthelper.ui;

import cz.cvut.kbe.crypthelper.CharMap;
import cz.cvut.kbe.crypthelper.CharMap.CharEntry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 *
 * @author Radek Ježdík
 */
public class MainWindow extends javax.swing.JFrame {

	private JFreeChart chart;

	private AlphabetEntryModel alphaEntryModel;


	public MainWindow() {
		initComponents();

		buttonGroup.add(percentOption);
		buttonGroup.add(perCountOption);
		percentOption.setSelected(true);

		alphaButtonGroup.add(noAlphaOption);
		alphaButtonGroup.add(englishAlphaOption);
		alphaButtonGroup.add(czechAlphaOption);
		noAlphaOption.setSelected(true);

		graphPanel.setLayout(new BorderLayout());

		noAlphaOption.addChangeListener(new AlphaChangeListener());
		englishAlphaOption.addChangeListener(new AlphaChangeListener());
		czechAlphaOption.addChangeListener(new AlphaChangeListener());

		offsetSlider.setVisible(false);
		bottomPanel.setVisible(false);
	}


	public void setChartData() {
		CategoryDataset dataset = getDataset();

		chart = ChartFactory.createBarChart("Četnost znaků", "Znak", "Četnost (%)",
				dataset, PlotOrientation.VERTICAL, false, false, false);

		styleChart(chart);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setMaximumDrawHeight(800);
		chartPanel.setMaximumDrawHeight(600);
		graphPanel.removeAll();
		graphPanel.add(chartPanel, BorderLayout.CENTER);
		graphPanel.updateUI();

		bottomPanel.setVisible(true);
	}


	private CategoryDataset getDataset() {
		AlphabetEntryModel model = (AlphabetEntryModel) charTable.getModel();

		if(model.size() == 0) return null;

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0; i < model.size(); i++) {
			double freq = model.getFrequency(i);
			String str = model.getCharacter(i) + "";
			if(alphaEntryModel != null) {
				str = ((char) ('A' + i)) + "" + alphaEntryModel.getCharacter(i);
			}
			dataset.addValue(freq, "Četnost", str);
			if(alphaEntryModel != null) {
				dataset.addValue(alphaEntryModel.getFrequency(i), "Četnost 2", str);
			}
		}
		return dataset;
	}


	private void styleChart(JFreeChart chart) {
		chart.getTitle().setPaint(Color.BLACK);
		chart.setBackgroundPaint(new Color(1f, 1f, 1f, 0f));

		CategoryPlot p = chart.getCategoryPlot();
		p.setRangeGridlinePaint(Color.red);
	}


	/** This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        alphaButtonGroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputText = new javax.swing.JTextArea();
        processButton = new javax.swing.JButton();
        bottomPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        charTable = new javax.swing.JTable();
        graphPanel = new javax.swing.JPanel();
        offsetSlider = new javax.swing.JSlider();
        percentOption = new javax.swing.JRadioButton();
        perCountOption = new javax.swing.JRadioButton();
        czechAlphaOption = new javax.swing.JRadioButton();
        englishAlphaOption = new javax.swing.JRadioButton();
        noAlphaOption = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        indexButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CryptHelper");

        inputText.setColumns(20);
        inputText.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        inputText.setLineWrap(true);
        inputText.setRows(5);
        jScrollPane1.setViewportView(inputText);

        processButton.setText("Zpracuj");

        charTable.setAutoCreateRowSorter(true);
        charTable.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        charTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Znak", "Četnost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        charTable.setIntercellSpacing(new java.awt.Dimension(1, 3));
        jScrollPane2.setViewportView(charTable);

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 244, Short.MAX_VALUE)
        );

        offsetSlider.setMajorTickSpacing(1);
        offsetSlider.setMaximum(13);
        offsetSlider.setMinimum(-12);
        offsetSlider.setMinorTickSpacing(1);
        offsetSlider.setPaintLabels(true);
        offsetSlider.setPaintTicks(true);
        offsetSlider.setToolTipText("Posunutí abecedy");
        offsetSlider.setValue(0);

        percentOption.setText("v procentech");

        perCountOption.setText("v počtu výskytů");

        czechAlphaOption.setText("Česká");

        englishAlphaOption.setText("Anglická");

        noAlphaOption.setText("Žádná");

        jLabel1.setText("Abeceda:");

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(perCountOption)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(percentOption))
                .addGap(18, 18, 18)
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(offsetSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addGroup(bottomPanelLayout.createSequentialGroup()
                        .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bottomPanelLayout.createSequentialGroup()
                                .addComponent(noAlphaOption)
                                .addGap(18, 18, 18)
                                .addComponent(englishAlphaOption)
                                .addGap(18, 18, 18)
                                .addComponent(czechAlphaOption))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(noAlphaOption)
                    .addComponent(englishAlphaOption)
                    .addComponent(czechAlphaOption))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(offsetSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addComponent(percentOption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(perCountOption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        indexButton.setText("Index koincidence");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(processButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(indexButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(processButton)
                    .addComponent(indexButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup alphaButtonGroup;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JTable charTable;
    private javax.swing.JRadioButton czechAlphaOption;
    private javax.swing.JRadioButton englishAlphaOption;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JButton indexButton;
    private javax.swing.JTextArea inputText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton noAlphaOption;
    private javax.swing.JSlider offsetSlider;
    private javax.swing.JRadioButton perCountOption;
    private javax.swing.JRadioButton percentOption;
    private javax.swing.JButton processButton;
    // End of variables declaration//GEN-END:variables


	public JTextArea getInputText() {
		return inputText;
	}


	public JButton getProcessButton() {
		return processButton;
	}


	public JTable getCharTable() {
		return charTable;
	}


	public JRadioButton getPerCountOption() {
		return perCountOption;
	}


	public JRadioButton getPercentOption() {
		return percentOption;
	}


	public JRadioButton getNoAlphaOption() {
		return noAlphaOption;
	}


	public JRadioButton getEnglishAlphaOption() {
		return englishAlphaOption;
	}


	public JRadioButton getCzechAlphaOption() {
		return czechAlphaOption;
	}


	public JSlider getOffsetSlider() {
		return offsetSlider;
	}


	public JButton getIndexButton() {
		return indexButton;
	}


	public void setTableModel(CharacterTableModel tableModel) {
		charTable.setModel(tableModel);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				return label;
			}

		};

		for(int i = 0; i < charTable.getColumnCount(); i++) {
			charTable.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
	}


	public void setOffset(int value) {
		if(alphaEntryModel != null) {
			alphaEntryModel.setOffset(value);
			setChartData();
		}
	}


	private class AlphaChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if(noAlphaOption.isSelected()) {
				alphaEntryModel = null;
				offsetSlider.setVisible(false);
			} else {
				if(englishAlphaOption.isSelected()) {
					alphaEntryModel = Alphabet.ENGLISH.getModel();
				} else {
					alphaEntryModel = Alphabet.CZECH.getModel();
				}
				alphaEntryModel.setOffset(offsetSlider.getValue());
				offsetSlider.setVisible(true);
			}
			setChartData();
		}

	}


	private enum Alphabet {

		ENGLISH(CharMap.getEnglishEntries()),
		CZECH(CharMap.getCzechEntries());

		private AlphabetEntryModel model;


		private Alphabet(CharEntry[] entries) {
			model = new DefaultAlphabetEntryModel(entries);
		}


		public AlphabetEntryModel getModel() {
			return model;
		}

	}

}
