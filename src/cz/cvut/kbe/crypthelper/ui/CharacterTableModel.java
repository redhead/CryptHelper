/*
 */
package cz.cvut.kbe.crypthelper.ui;

import javax.swing.table.AbstractTableModel;
import cz.cvut.kbe.crypthelper.CharMap.CharEntry;


/**
 *
 * @author Radek Ježdík
 */
public class CharacterTableModel extends AbstractTableModel implements AlphabetEntryModel {

	private CharEntry[] entries = new CharEntry[0];

	private boolean percentView = true;


	public void setData(CharEntry[] entries) {
		this.entries = entries;
		fireTableDataChanged();
	}


	public void setPercentView(boolean percent) {
		percentView = percent;
		fireTableDataChanged();
	}


	@Override
	public int getRowCount() {
		return entries.length;
	}


	@Override
	public int getColumnCount() {
		return 2;
	}


	@Override
	public String getColumnName(int columnIndex) {
		return (columnIndex == 0 ? "Znak" : "Četnost");
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return (columnIndex == 0 ? Character.class : Integer.class);
	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0) {
			return entries[rowIndex].character;
		} else {
			return getValue(rowIndex);
		}
	}


	private Object getValue(int i) {
		if(percentView) {
			return entries[i].frequency;
		}
		return entries[i].count;
	}


	@Override
	public int size() {
		return entries.length;
	}


	@Override
	public double getFrequency(int i) {
		return entries[i].frequency;
	}


	@Override
	public Character getCharacter(int i) {
		return entries[i].character;
	}


	@Override
	public void setOffset(int n) {
		throw new UnsupportedOperationException("Not supported");
	}


	@Override
	public int getOffset() {
		throw new UnsupportedOperationException("Not supported");
	}

}
