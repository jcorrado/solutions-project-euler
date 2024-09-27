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
