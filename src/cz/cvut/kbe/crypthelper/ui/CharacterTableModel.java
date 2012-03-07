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

import cz.cvut.kbe.crypthelper.CharMap.CharEntry;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author Radek Ježdík
 */
public class CharacterTableModel extends AbstractTableModel implements AlphabetEntryModel {

	private CharEntry[] entries = new CharEntry[0];

	private boolean percentView = true;

	private int offset;


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
		return entries[getIndex(i)].frequency;
	}


	@Override
	public Character getCharacter(int i) {
		return entries[getIndex(i)].character;
	}


	private int getIndex(int n) {
		int i = (n + offset) % entries.length;
		if(i < 0) i += entries.length;
		return i;
	}


	@Override
	public void setOffset(int n) {
		offset = n;
	}


	@Override
	public int getOffset() {
		return offset;
	}

}
