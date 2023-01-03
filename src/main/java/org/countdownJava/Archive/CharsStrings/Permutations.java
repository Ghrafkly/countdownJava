package org.countdownJava.Archive.CharsStrings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutations {
	public List<List<String>> generate(List<String> list) {
		List<String> newList = new ArrayList<>(list);

		if (newList.size() == 0) {
			Set<List<String>> result = new HashSet<>();
			result.add(new ArrayList<>());
			return new ArrayList<>(result);
		}

		Set<List<String>> returnSet = new HashSet<>();

		String firstElement = newList.remove(0);

		List<List<String>> recursiveReturn = generate(newList);
		for (List<String> li : recursiveReturn) {
			for (int index = 0; index <= li.size(); index++) {
				List<String> temp = new ArrayList<>(li);
				temp.add(index, firstElement);
				returnSet.add(temp);
			}
		}

		return new ArrayList<>(returnSet);
	}
}
