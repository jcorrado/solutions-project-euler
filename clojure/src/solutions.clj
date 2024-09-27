(ns solutions
  (:require [clojure.math :as math]))


;;
;; Problem 1
;;
(defn problem-1 [n]
  (->> (range 1 n)
       (filter (fn [n] (or (zero? (mod n 3))
                          (zero? (mod n 5)))))
       (reduce +)))

(comment
  (problem-1 10)
  ;; 23
  (problem-1 1000)
  ;; 233168
  )


;;
;; Problem 2
;;
(defn problem-2 [n]
  (letfn [(fib-until [n]
            (loop [accum '(1 0)]
              (let [i (apply + (take 2 accum))]
                (if (>= i n)
                  accum
                  (recur (conj accum i))))))]
    (->> (fib-until n)
         (filter even?)
         (reduce +))))

(comment
  (problem-2 4000000)
  ;; 4613732
  )


;;
;; Problem 3
;;
(defn prime? [n]
  (cond
    (<= n 1) false
    (<= n 3) true
    (or (zero? (mod n 2)) (zero? (mod n 3))) false
    :else (loop [i 5]
            (if (> i (Math/sqrt n))
              true
              (if (or (zero? (mod n i)) (zero? (mod n (+ i 2))))
                false
                (recur (+ i 6)))))))

(defn problem-3 [n]
  (letfn [(get-prime-factor [x]
            (when (zero? (mod n x))
              (if-let [quotient (quot n x)]
                (when (prime? quotient)
                  quotient))))]
    (loop [i 2]
      (when (<= i n)
        (if-let [max-prime (get-prime-factor i)]
          max-prime
          (recur (inc i)))))))

(comment
  (problem-3 13195)
  ;; 29
  (problem-3 600851475143)
  ;; 6857
  )


;;
;; Problem 4
;;
(defn problem-4 []
  (letfn [(gen-palindrome [compositor]
            (flatten (for [i (range 1 10)]
                       (for [j (range 10)]
                         (for [k (range 10)]
                           (compositor i j k))))))

          (combine-digits [ds]
            (Integer/parseInt (apply str ds)))

          (get-three-digit-factors [n]
            (letfn [(three-digit-complements [i]
                      (when (= 0 (mod n i))
                        (let [quotient (quot n i)]
                          (when (<= 100 quotient 999)
                            [i quotient]))))]
              (loop [[i & rest] (range 100 1000)]
                (when (some? i)
                  (if-let [factors (three-digit-complements i)]
                    [n factors]
                    (recur rest))))))]
    (->>
     (concat (gen-palindrome (fn [i j k] (combine-digits [i j k j i])))
             (gen-palindrome (fn [i j k] (combine-digits [i j k k j i]))))
     (reverse)
     (map get-three-digit-factors)
     (filter some?)
     ;;(first)
     )))

(comment
  (problem-4)
  ;; [906609 [913 993]]
  )

(comment
  ;; I was curious which of these approaches would be faster.  I'm
  ;; surprised to learn that the string-based approach is faster... at
  ;; least in my simple tests.

  (defn combine-digits-str [ds]
    (Integer/parseInt (apply str ds)))

  (defn combine-digits-math
    [ds]
    (loop [tot 0 ds ds]
      (if (empty? ds)
        tot
        (recur (+ (* tot 10) (first ds))
               (rest ds)))))

  (time
   (combine-digits-str (range 10)))
  ;; 123456789
  ;; "Elapsed time: 0.032792 msecs"
  ;; "Elapsed time: 0.059667 msecs"
  ;; "Elapsed time: 0.033167 msecs"

  (time
   (combine-digits-math (range 10)))
  ;; 123456789
  ;; "Elapsed time: 0.058084 msecs"
  ;; "Elapsed time: 0.058292 msecs"
  ;; "Elapsed time: 0.037666 msecs"
  )


;;
;; Problem 5
;;
(defn problem-5-brute-force [n]
  (letfn [(divisible-by-all? [x nums]
            (= (count nums)
               (->> nums
                    (map #(mod x %))
                    (filter zero?)
                    (count))))]
    (loop [i 21]
      (if (divisible-by-all? i (range 1 (inc n)))
        i
        (recur (inc i))))))

(comment
  (problem-5-brute-force 10)
  ;; 2520
  (time (problem-5-brute-force 20))
  ;; 232792560
  ;; "Elapsed time: 108808.447625 msecs"
  )


(defn prime-factors
  "Return coll of prime factors. eg: 20 => [2 2 5]"
  [n]
  (loop [n n accum [] divisor 2]
    (cond
      (= n 1) accum
      (zero? (mod n divisor)) (recur (/ n divisor) (conj accum divisor) divisor)
      :else (recur n accum (inc divisor)))))

(comment
  (prime-factors 20)
  ;; [2 2 5]
  (prime-factors 60)
  ;; [2 2 3 5]
  (prime-factors 68985)
  ;; [3 3 3 5 7 73]
  )

(defn problem-5 [nums]
  (->> nums
       (map prime-factors)
       (map frequencies)
       (apply merge-with max)
       (reduce (fn [accum [base exp]]
                 (* accum (int (math/pow base exp))))
               1)))

(comment
  (problem-5 [20 60 68985])
  ;; ({2 2, 5 1} {2 2, 3 1, 5 1} {3 3, 5 1, 7 1, 73 1})
  ;; {2 2, 5 1, 3 3, 7 1, 73 1}
  ;; 275940

  (time (problem-5 (range 1 21)))
  ;; 232792560
  ;; "Elapsed time: 0.216833 msecs"
  )


;;
;; Problem 6
;;
(defn problem-6 [nums]
  (let [sum (reduce + nums)
        sum-of-square (reduce #(+ % (* %2 %2)) 0 nums)]
    (- (* sum sum) sum-of-square)))

(comment
  (problem-6 (range 1 (inc 10)))
  ;; 2640
  (problem-6 (range 1 (inc 100)))
  ;; 25164150
  )


;;
;; Problem 7
;;
(defn problem-7 [cnt]
  (->> (range)
       (filter prime?)
       (take cnt)
       last))

(comment
  (problem-7 10001)
  ;; 104743
  )


;;
;;  Problem 8
;;
(defn problem-8 [n k]
  (->> (str n)
       (map Character/getNumericValue)
       (partition k 1)
       (map #(reduce * %))
       (reduce max)))

(comment
  (def n 7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450)

  (problem-8 n 4)
  ;; 5832
  (problem-8 n 13)
  ;; 23514624000
  )


;;
;; Problem 9
;;
(defn problem-9 [target]
  (for [a (range 1 (inc target))
        :let [bc (- target a)]
        b (range 1 (inc (quot bc 2)))
        :let [c (- bc b)]
        :when (and (= target (+ a b c))
                   (< a b c)
                   (= (* c c) (+ (* a a) (* b b))))]
    [(* a b c) a b c]))

(comment
  (problem-9 1000)
  ;; ([31875000 200 375 425])
  )
