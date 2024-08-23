# Problem 1
# Multiples of 3 or 5
# https://projecteuler.net/problem=1


def problem_1(n):
    tot = 0
    for i in range(1, n):
        if (i % 3 == 0) or (i % 5 == 0):
            tot += i
    return tot


# print(problem_1(10))
# # 23
# print(problem_1(1000))
# # 233168

# Problem 2
# Even Fibonacci Numbers


def problem_2(n):
    tot = 0
    i = 1
    last = [0, i]
    while i < n:
        i = last[0] + last[1]
        last[0] = last[1]
        last[1] = i
        if i % 2 == 0:
            tot += i
    return tot


# print(problem_2(4000000))
# # 4613732


# Problem 3
# Largest Prime Factor


def problem_3(n):
    def is_prime(n):
        if n <= 1:
            return False
        if n <= 3:
            return True
        if n % 2 == 0 or n % 3 == 0:
            return False
        i = 5
        while i <= n**0.5:
            if n % i == 0 or n % (i + 2) == 0:
                return False
            i += 6
        return True

    for i in range(2, n + 1):
        if n % i == 0:
            quotient = n // i
            if is_prime(quotient):
                return quotient


# print(problem_3(13195))
# # 29
# print(problem_3(600851475143))
# # 6857


# Problem 4
# Largest Palindrome Product


def problem_4():
    # Generate palindromic numbers that are the product of two
    # three-digit numbers.
    # 100*100 -> 999*999 = 10,000 -> 998,001
    #
    # This produces 1800 palindromic numbers in the required range.
    def gen_palindromes():
        fives, sixes = [], []
        for i in range(1, 10):
            for j in range(10):
                for k in range(10):
                    for accum, compositor in (
                        (fives, lambda i, j, k: [i, j, k, j, i]),
                        (sixes, lambda i, j, k: [i, j, k, k, j, i]),
                    ):
                        accum.append(
                            int("".join([str(x) for x in compositor(i, j, k)]))
                        )
        return fives + sixes

    def has_three_digit_factors(n):
        for i in range(100, 1000):
            if n % i == 0:
                quotient = n // i
                if 100 <= quotient <= 999:
                    return (i, quotient)
        return False

    # Iterator from max palindrome looking for one that has two
    # three-digit factors.
    palindromes = gen_palindromes()
    for i in range(len(palindromes) - 1, -1, -1):
        n = palindromes[i]
        factors = has_three_digit_factors(n)
        if factors:
            return (n, factors)


# print(problem_4())
# # (906609, (913, 993))


# Problem 5
# Smallest Multiple
def problem_5(factor_cnt):
    n = factor_cnt + 1
    while True:
        cnt = 0
        for i in range(factor_cnt, 0, -1):
            if n % i == 0:
                cnt += 1
        if cnt == factor_cnt:
            return n
        n += 1


print(problem_5(20))
# (venv) jereme@peanut python % time python problems.py
# 232792560
# python problems.py  93.56s user 0.08s system 99% cpu 1:34.12 total
