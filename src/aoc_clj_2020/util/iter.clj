(ns aoc-clj-2020.util.iter)

(defn stabilize
  [f init]
  (loop [last init]
    (let [current (f last)]
      (if (= current last)
        current
        (recur current)))))
