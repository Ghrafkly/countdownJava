package org.countdownJava.Archive.Bytes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutations {
	public List<List<Byte>> generate(List<Byte> list) {
		List<Byte> newList = new ArrayList<>(list);

		if (newList.size() == 0) {
			Set<List<Byte>> result = new HashSet<>();
			result.add(new ArrayList<>());
			return new ArrayList<>(result);
		}

		Set<List<Byte>> returnSet = new HashSet<>();

		Byte firstElement = newList.remove(0);

		List<List<Byte>> recursiveReturn = generate(newList);
		for (List<Byte> li : recursiveReturn) {
			for (int index = 0; index <= li.size(); index++) {
				List<Byte> temp = new ArrayList<>(li);
				temp.add(index, firstElement);
				returnSet.add(temp);
			}
		}

		return new ArrayList<>(returnSet);
	}
}
