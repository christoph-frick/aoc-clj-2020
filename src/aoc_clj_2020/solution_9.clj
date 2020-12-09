(ns aoc-clj-2020.solution-9
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]
            [clojure.math.combinatorics :as comb]))

(defn parse
  [s]
  (mapv lp/atol (str/split-lines s)))

(defn check
  [preamble xs]
  (lazy-seq
   (let  [ps (take preamble xs)
          n (first (drop preamble xs))]
     (when n
       ; optimization: make combinations lazy and find the first
       ; match instead of doing just a check against the set
       (let [p-sums (into #{}
                          (map (partial apply +))
                          (comb/combinations ps 2))]
         (cons
          {(if (contains? p-sums n)
             :ok
             :fail)
           n}
          (check preamble (rest xs))))))))

(defn attack
  [preamble xs]
  (->>
   xs
   (check preamble)
   (filter :fail)
   (map :fail)))

(defn find-weakness
  [target xs]
  (loop [xs xs]
    (when (seq xs)
      (let [result (loop [i 2]
                     (let [frame (take i xs)
                           sum (apply + frame)]
                       (if (= sum target)
                         (apply sorted-set frame)
                         (when (< sum target)
                           (recur (inc i))))))]

        (if result
          result
          (recur (rest xs)))))))

(defn sum-weakness
  [sum-set]
  (apply + ((juxt first last) sum-set)))

(defn part-1
  []
  (->> (li/read-input "9.txt")
       (parse)
       (attack 25)
       (first)))

(defn part-2
  []
  (->> (li/read-input "9.txt")
       (parse)
       (find-weakness (part-1))
       (sum-weakness)))
