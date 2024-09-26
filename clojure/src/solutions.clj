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
  (loop [i 2]
    (when (<= i n)
      (if (= 0 (mod n i))
        (let [quotient (quot n i)]
          (if (prime? quotient)
            quotient
            (recur (inc i))))
        (recur (inc i))))))

(comment
  (problem-3 13195)
  ;; 29
  (problem-3 600851475143)
  ;; 6857
  )
