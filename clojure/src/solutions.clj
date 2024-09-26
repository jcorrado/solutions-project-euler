(ns solutions
  (:require [clojure.math :as math]))

;; Problem 1
(defn problem-1 [n]
  (->> (range 1 n)
       (filter (fn [n] (or (= 0 (mod n 3))
                          (= 0 (mod n 5)))))
       (reduce +)))

(comment
  (problem-1 10)
  ;; 23
  (problem-1 1000)
  ;; 233168
  )

;; Problem 2
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

;; Problem 3
(defn prime? [n]
  (cond
    (<= n 1) false
    (<= n 3) true
    (or (= 0(mod n 2)) (= 0 (mod n 3))) false
    :else (loop [i 5]
            (if (> i (Math/sqrt n))
              true
              (if (or (= 0 (mod n i)) (= 0 (mod n (+ i 2))))
                false
                (recur (+ i 6)))))))

(defn problem-3 [n]
  (letfn [(get-prime-factor [x]
            (when (= 0 (mod n x))
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

;; Problem 4
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
