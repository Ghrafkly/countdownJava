package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.*;

public class Permutations {
	private final Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations = new HashMap<>();

	public void execute(List<List<Integer>> combinations) throws ExecutionException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(12);
		Map<List<Integer>, Future<List<List<Integer>>>> futures = new HashMap<>();

		for (List<Integer> combination : combinations) {
			futures.put(combination, executor.submit(() -> computePermutations(combination)));
		}

		for (List<Integer> combination : futures.keySet()) {
			mapCombinationsPermutations.put(combination, futures.get(combination).get());
		}

		executor.shutdownNow();
	}

	public List<List<Integer>> computePermutations(List<Integer> combination) {
		if (combination.size() == 0) {
			Set<List<Integer>> result = new HashSet<>();
			result.add(new ArrayList<>());
			return new ArrayList<>(result);
		}

		Set<List<Integer>> returnSet = new HashSet<>();
		Integer firstElement = combination.get(0);
		List<List<Integer>> recursiveReturn = computePermutations(combination.subList(1, combination.size()));

		for (List<Integer> li : recursiveReturn) {
			for (int index = 0; index <= li.size(); index++) {
				List<Integer> temp = new ArrayList<>(li);
				temp.add(index, firstElement);
				returnSet.add(temp);
			}
		}

		return new ArrayList<>(returnSet);
	}

	public Map<List<Integer>, List<List<Integer>>> getMapCombinationsPermutations() {
		return mapCombinationsPermutations;
	}
}
