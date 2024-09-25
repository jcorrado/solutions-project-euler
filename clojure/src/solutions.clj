(ns solutions)

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
