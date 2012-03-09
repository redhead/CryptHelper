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

import cz.cvut.kbe.crypthelper.CharMap.CharEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 * @author Radek Ježdík <jezdik.radek@gmail.com>
 */
public class Alphabets {

	private final static Map<String, CharEntry[]> map = new HashMap<>();


	static {
		File dir = new File("alphabets");
		File[] files = dir.listFiles();
		if(files != null) {
			for(File f : files) {
				try {
					BufferedReader bf = new BufferedReader(new FileReader(f));

					String name = bf.readLine();

					int count = 0;
					Map<Character, Integer> mmap = new TreeMap<>();

					String line;
					while((line = bf.readLine()) != null) {
						String[] strs = line.split(":");
						char chr = strs[0].charAt(0);
						int cnt = Integer.parseInt(strs[1]);

						mmap.put(chr, cnt);
						count += cnt;
					}

					CharEntry[] entries = new CharEntry[mmap.size()];
					int i = 0;
					for(Map.Entry<Character, Integer> entry : mmap.entrySet()) {
						int cnt = entry.getValue();

						long x = Math.round((cnt * 100 / (double) count) * 100);
						double freq = x / 100d;

						entries[i++] = new CharMap.CharEntry(entry.getKey(), cnt, freq);
					}
					map.put(name, entries);
				} catch(Exception ex) {
				}
			}
		}
	}


	public static String[] getNames() {
		return map.keySet().toArray(new String[map.size()]);
	}


	public static CharEntry[] getEntriesFor(String name) {
		return map.get(name);

	}

}
