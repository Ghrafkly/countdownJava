# Countdown/Letters and Numbers

Code Structure
- Using numbers: 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100
- Using operators: +, -, , /

This is the usual form of the data: [[100, 4, -, 3, 4, 1, /, *, *, 9, /], [100, 4, -, 3, 4, 1, /, *, /, 9, +]...]

1. I generate all 24c6 combinations (removing duplicates). This generates 13,243 combinations of unique number sets.
2. I then generate all permutations for each combination (also removing duplicates): This generates 5,281,560 permutations.
3. I store the results in a Map (key = combination i.e. [1, 2, 3], value = permutations i.e. [[1, 2, 3], [3, 2, 1], [2, 1, 3]...])

I keep track of each combination and its permuations to eliminate duplicate equations from the same numberset contributing to the finay tally

Up until here the code runs in milliseconds

4. For each permutation I generate all possible equations (in postfix form). Store the results in a map, similar to the combination-permutation map. (key = [3, 1, 2] value = [[3, 1, 2, +, +], [3, 1, +, 2, +]...]): Each permutation has around 40,000 equations

5. After each permutation has postfix generated I evaluate the equations, then clear the permutation-postfix map to save on memory
   1. There will be ~200 billion equations that are procesed

Final evaluation time is 90 minutes with multi-threading
