(ns aoc-clj-2020.solution-01
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [clojure.math.combinatorics :as comb]))

(defn read-numbers
  [file-name]
  (into []
        (map lp/atoi)
        (li/read-lines file-name)))

(defn sum=2020?
  [xs]
  (= 2020 (apply + xs)))

(defn solution'
  [file-name n]
  (->> (comb/combinations (read-numbers file-name) n)
       (filter sum=2020?)
       (map (partial apply *))
       (first)))

(defn part-1
  []
  (solution' "1.txt" 2))

(defn part-2
  []
  (solution' "1.txt" 3))
