package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyExecutor {
	private final Map<List<Integer>, List<List<Integer>>> mapCombinationsPermutations = new HashMap<>();
	private final Map<List<Integer>, Integer[][]> mapPermutationsPostfix = new HashMap<>();

	public void permutations(List<List<Integer>> combinations) {
		ExecutorService executor = Executors.newFixedThreadPool(12);
		List<Future<List<List<Integer>>>> futures = new ArrayList<>();

		for (List<Integer> combination : combinations) {
			Permutations permutations = new Permutations();
			futures.add(executor.submit(() -> permutations.generate(combination)));
		}

		for (Future<List<List<Integer>>> future : futures) {
			try {
				List<List<Integer>> permutations = future.get();
				mapCombinationsPermutations.put(combinations.get(futures.indexOf(future)), permutations);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		executor.shutdownNow();

//		ExecutorService executor = Executors.newCachedThreadPool();
//		ExecutorService executor = Executors.newFixedThreadPool(12);
//		Map<List<Integer>, Future<List<List<Integer>>>> futures = new HashMap<>();
//
//		for (List<Integer> combination : combinations) {
//			futures.put(combination, executor.submit(() -> computePermutations(combination)));
//		}
//
//		try {
//			for (List<Integer> combination : futures.keySet()) {
//				List<List<Integer>> permutations = futures.get(combination).get();
//				mapCombinationsPermutations.put(combination, permutations);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		executor.shutdownNow();
	}

	private List<List<Integer>> computePermutations(List<Integer> combination) {
		List<Integer> newList = new ArrayList<>(combination);

		if (newList.size() == 0) {
			Set<List<Integer>> result = new HashSet<>();
			result.add(new ArrayList<>());
			return new ArrayList<>(result);
		}

		Set<List<Integer>> returnSet = new HashSet<>();
		Integer firstElement = newList.remove(0);
		List<List<Integer>> recursiveReturn = computePermutations(newList);

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
