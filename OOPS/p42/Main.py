class Fibonacci:
    def __init__(self):
        self.memo = {}

    def calculate(self, n):
        if n in self.memo:
            return self.memo[n]

        if n <= 1:
            result = n
        else:
            result = self.calculate(n - 1) + self.calculate(n - 2)

        self.memo[n] = result
        return result


def generate_fibonacci_sequence(n):
    fibonacci = Fibonacci()
    return [fibonacci.calculate(i) for i in range(n)]


sequence_length = 10
fibonacci_sequence = generate_fibonacci_sequence(sequence_length)
print(f"Fibonacci Sequence (first {sequence_length} numbers): {fibonacci_sequence}")
