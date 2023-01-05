package org.countdownJava.Current;

import java.util.*;

public class Permutations implements Runnable {
	private List<Integer> combinations;
	private List<List<Integer>> permutations;
	private final Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations = new HashMap<>();

	public List<List<Integer>> generate(List<Integer> combination) {
		List<Integer> newList = new ArrayList<>(combination);

		if (newList.size() == 0) {
			Set<List<Integer>> result = new HashSet<>();
			result.add(new ArrayList<>());
			return new ArrayList<>(result);
		}

		Set<List<Integer>> returnSet = new HashSet<>();
		Integer firstElement = newList.remove(0);
		List<List<Integer>> recursiveReturn = generate(newList);

		for (List<Integer> li : recursiveReturn) {
			for (int index = 0; index <= li.size(); index++) {
				List<Integer> temp = new ArrayList<>(li);
				temp.add(index, firstElement);
				returnSet.add(temp);
			}
		}

		return new ArrayList<>(returnSet);
	}

	public List<List<Integer>> getPermutations() {
		return permutations;
	}

	public Map<List<Integer>, List<List<Integer>>> getMapCombinationsPermutations() {
		return mapCombinationsPermutations;
	}

	@Override
	public void run() {
//		generate(combinations);
	}
}
