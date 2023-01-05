package org.countdownJava.Current;

import java.util.*;
import java.util.concurrent.*;

public class Permutations {
	private final Map<List<Integer>, List<List<Integer>>> temp = new HashMap<>();
	private final Map<int[], int[][]> mapCombinationsPermutations = new HashMap<>();

	public void permutations(List<List<Integer>> combinations) throws ExecutionException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(12);
		Map<List<Integer>, Future<List<List<Integer>>>> futures = new HashMap<>();

		for (List<Integer> combination : combinations) {
			futures.put(combination, executor.submit(() -> computePermutations(combination)));
		}

		for (List<Integer> combination : futures.keySet()) {
			temp.put(combination, futures.get(combination).get());
		}

		executor.shutdownNow();

		convertToArray();
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

	private void convertToArray() {
		int[][] tempArray;
		for (Map.Entry<List<Integer>, List<List<Integer>>> entry : temp.entrySet()) {
			tempArray = new int[entry.getValue().size()][entry.getKey().size()];
			for (int i = 0; i < entry.getValue().size(); i++) {
				for (int j = 0; j < entry.getKey().size(); j++) {
					tempArray[i][j] = entry.getValue().get(i).get(j);
				}
			}
			mapCombinationsPermutations.put(entry.getKey().stream().mapToInt(i -> i).toArray(), tempArray);
		}

		for (Map.Entry<int[], int[][]> entry : mapCombinationsPermutations.entrySet()) {
			System.out.println(Arrays.toString(entry.getKey()) + " -> " + Arrays.deepToString(entry.getValue()));
		}
	}

	public Map<int[], int[][]> getMapCombinationsPermutations() {
		return mapCombinationsPermutations;
	}
}
