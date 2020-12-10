(ns aoc-clj-2020.solution-10
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]))

(defn parse
  [s]
  (lp/lines-to lp/atoi s))

(defn differences
  [lines]
  ; prefix 0 for the beginning
  ; always add one 3 for the end
  (let [sorted (conj (sort lines) 0)]
    (-> (map (fn [a b]
               (- b a))
             sorted
             (rest sorted))
        (frequencies)
        (update 3 inc))))

(defn result
  [freqs]
  (* (get freqs 1) (get freqs 3)))

(defn part-1
  []
  (-> (li/read-input "10.txt")
      (parse)
      (differences)
      (result)))

(defn part-2
  []
  nil)
