/*
 */
package kbe;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 *
 * @author Radek Ježdík
 */
public class CharMap {

	private int count;

	private final Map<Character, Integer> map = new TreeMap<>();


	public static CharMap.CharEntry[] getEnglishEntries() {
		int[] alpha = new int[] {
			834, 154, 273, 414, 1260, 203, 192, 611, 671, 23, 87, 424, 253,
			680, 770, 166, 9, 568, 611, 937, 258, 106, 234, 20, 204, 6
		};
		return toEntries(alpha, 9968);
	}


	public static CharMap.CharEntry[] getCzechEntries() {
		int[] alpha = new int[] {
			7502, 1627, 2434, 3336, 9827, 173, 307, 2511, 5328, 2170, 2242, 4264, 3178,
			5363, 6730, 2079, 0, 3689, 4272, 4601, 2721, 2845, 2, 6, 2149, 2092
		};
		return toEntries(alpha, 81448);
	}


	private static CharEntry[] toEntries(int[] alpha, int count) {
		CharEntry[] entries = new CharEntry[26];
		for(int i = 0; i < 26; i++) {
			double freq = formatDouble(alpha[i] * 100 / (double) count);
			entries[i] = new CharEntry((char) ('A' + i), alpha[i], freq);
		}
		return entries;
	}


	public static CharMap fromString(String str) {
		CharMap map = new CharMap();

		for(int i = 0; i < str.length(); i++) {
			char chr = str.charAt(i);
			chr = Character.toUpperCase(chr);

			if(chr >= 'A' && chr <= 'Z') map.add(chr);
		}

		return map;
	}


	public CharMap() {
		fill();
	}


	private void fill() {
		for(char c = 'A'; c <= 'Z'; c++) {
			map.put(c, 0);
		}
	}


	public void add(char chr) {
		chr = Character.toUpperCase(chr);
		if(chr < 'A' && chr > 'Z') return;

		int x = map.get(chr) + 1;
		map.put(chr, x);

		count++;
	}


	public double getIndexOfCoincidence() {
		double sum = 0;
		for(int i = 0; i < 26; i++) {
			int cnt = map.get((char) ('A' + i));
			sum += cnt * (cnt - 1);
		}
		return sum / (double) (count * (count - 1));
	}


	public CharMap.CharEntry[] getEntries() {
		return toCharEntries(map);
	}


	private CharMap.CharEntry[] toCharEntries(Map<Character, Integer> map) {
		CharMap.CharEntry[] entries = new CharMap.CharEntry[map.size()];

		Iterator<Entry<Character, Integer>> it = map.entrySet().iterator();
		int i = 0;
		while(it.hasNext()) {
			Entry<Character, Integer> mapEnt = it.next();
			Character chr = mapEnt.getKey();
			Integer cnt = mapEnt.getValue();
			double freq = formatDouble(cnt * 100 / (double) count);
			entries[i++] = new CharMap.CharEntry(chr, cnt, freq);
		}

		return entries;
	}


	private static double formatDouble(double d) {
		long x = Math.round(d * 100);
		return x / 100d;
	}


	public static class CharEntry {

		public Character character;

		public Integer count;

		public double frequency;


		public CharEntry(Character character, Integer count, double frequency) {
			this.character = character;
			this.count = count;
			this.frequency = frequency;
		}

	}

}
