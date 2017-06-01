(ns project-euler.core
  (:gen-class))

;; Problem 1

;; If we list all the natural numbers below 10 that are multiples of 3
;; or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
;;
;; Find the sum of all the multiples of 3 or 5 below 1000.

(defn problem-1 [coll]
  (reduce + (filter #(or (zero? (mod % 3))
                         (zero? (mod % 5))) coll)))

(problem-1 (range 3 1000))
;; => 233168


(defn problem-1_variation-1 [coll]
  (loop [i (first coll) coll (rest coll) sum 0]
    (if (nil? i)
      sum
      (if (or (zero? (mod i 3))
              (zero? (mod i 5)))
        (recur (first coll) (rest coll) (+ sum i))
        (recur (first coll) (rest coll) sum)))))

(problem-1_variation-1 (range 3 1000))


(defn problem-1_variation-2 [coll]
  (reduce + (set (concat (take-nth 3 coll)
                         (take-nth 5 coll)))))

(problem-1_variation-2 (range 3 1000))


;; Problem 2

;; Each new term in the Fibonacci sequence is generated by adding the
;; previous two terms. By starting with 1 and 2, the first 10 terms
;; will be:
;; 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
;;
;; By considering the terms in the Fibonacci sequence whose values do
;; not exceed four million, find the sum of the even-valued terms.
(defn problem-2 [cnt]
  (loop [curr 2 coll [1] num 1]
    (if (= num cnt)
      coll
      (recur (+ curr (last coll)) (conj coll curr) (inc num)))))

(problem-2 10)
