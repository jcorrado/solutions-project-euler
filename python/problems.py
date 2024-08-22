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
# https://projecteuler.net/problem=2


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
# https://projecteuler.net/problem=3

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
print(problem_3(600851475143))
# 6857
