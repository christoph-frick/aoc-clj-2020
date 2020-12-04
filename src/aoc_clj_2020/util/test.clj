(ns aoc-clj-2020.util.test)

(defn count-valid
  [pred xs]
  (->> xs
       (filter pred)
       (count)))
