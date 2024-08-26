from functools import reduce
import operator

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

    # Iterate from max palindrome looking for one that has two
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


# print(problem_5(20))
# # (venv) jereme@peanut python % time python problems.py
# # 232792560
# # python problems.py  93.56s user 0.08s system 99% cpu 1:34.12 total


# Problem 6
# Sum Square Difference
def problem_6(n):
    r = range(1, n + 1)
    return sum(r) ** 2 - sum([x * x for x in r])


# print(problem_6(100))
# # 25164150


# Problem 7
# 10001st Prime
def problem_7(target_primes_cnt):
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

    prime_cnt = 0
    i = 1
    while True:
        if is_prime(i):
            prime_cnt += 1
            if prime_cnt == target_primes_cnt:
                return i
        i += 1


# print(problem_7(10001))
# # 104743


# Problem 8
# Largest Product in a Series
def problem_8(n, k):
    digits = [int(x) for x in str(n)]
    maximum = 0
    i, length = 0, len(digits)
    while k <= length:
        maximum = max(maximum, reduce(operator.mul, digits[i:k]))
        i += 1
        k += 1
    return maximum


n = 7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450


# # print(problem_8(n, 4))
# # # 5832
# print(problem_8(n, 13))
# # 23514624000


# Problem 9
# Special Pythagorean Triplet
def problem_9(target):
    for a in range(2, target + 1):
        bc = target - a
        for b in range(1, (bc // 2) + 1):
            c = bc - b
            # print(f"DEBUG: a: {a}, b: {b}, c: {c}")
            if not a < b < c:
                continue

            if a**2 + b**2 == c**2:
                return (a * b * c, a, b, c)


problem_9(1000)
# (31875000, 200, 375, 425)
