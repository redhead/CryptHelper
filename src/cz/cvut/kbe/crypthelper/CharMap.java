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
