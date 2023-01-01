# Countdown/Letters and Numbers

Code Structure
- Using numbers: 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100
- Using operators: +, -, , /

Note: I encode the numbers/operators to work for char arrays (1 = a, 10 = j, = q)

All examples of data are a reduced form of what I actually will be working with.
This is the usual form of the data: [[100, 4, -, 3, 4, 1, /, *, *, 9, /], [100, 4, -, 3, 4, 1, /, *, /, 9, +]...]

1. I generate all 24c6 combinations (removing duplicates). This generates 13,243 combinations.
2. I then generate all permutations for each combination (also removing duplicates): This generates 5,281,560 permutations
3. I store the results in a Map (key = combination i.e. [1, 2, 3], value = permutations i.e. [[1, 2, 3], [3, 2, 1], [2, 1, 3]...])
   1. 3.1 Map<List<String>, List<List<String>>>

Up until here the code runs in milliseconds

4. For each permutation I generate all possible equations (in postfix form). Store the results in a map, similar to the combination-permutation map. (key = [3, 1, 2] value = [[3, 1, 2, +, +], [3, 1, +, 2, +]...]): Each permutation has around 40,000 equations
    1. Map<List<String>, char[][]>

Without evaluating, generating all possible equations takes around 6-7 hours

5. After each permutation has postfix generated I evaluate the equations, then clear the permutation-postfix map to save on memory
   1. There will be ~200 billion equations total (I have never gotten here as it would takes days currently)

Evaluating will take an estimated 6-7 days
