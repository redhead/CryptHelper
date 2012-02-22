/*
 */
package cz.cvut.kbe.crypthelper.ui;

import cz.cvut.kbe.crypthelper.CharMap.CharEntry;


/**
 *
 * @author Radek Ježdík
 */
public class DefaultAlphabetEntryModel implements AlphabetEntryModel {

	private CharEntry[] entries;

	private int offset = 0;


	public DefaultAlphabetEntryModel(CharEntry[] entries) {
		this.entries = entries;
	}


	@Override
	public void setOffset(int n) {
		this.offset = n;
	}


	@Override
	public int getOffset() {
		return offset;
	}


	@Override
	public int size() {
		return entries.length;
	}


	private int getIndex(int n) {
		int i = (n + offset) % entries.length;
		if(i < 0) i += entries.length;
		return i;
	}


	@Override
	public double getFrequency(int i) {
		return entries[getIndex(i)].frequency;
	}


	@Override
	public Character getCharacter(int i) {
		return entries[getIndex(i)].character;
	}

}
