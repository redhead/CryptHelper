/*
 */
package kbe.ui;


/**
 *
 * @author Radek Ježdík
 */
public interface AlphabetEntryModel {

	void setOffset(int n);


	int getOffset();


	int size();


	double getFrequency(int i);


	Character getCharacter(int i);

}
