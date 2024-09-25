(ns solutions)

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
