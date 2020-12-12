(ns aoc-clj-2020.solution-9
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [clojure.string :as str]))

(defn parse
  [s]
  (mapv lp/atol (str/split-lines s)))

(defn check
  [preamble xs]
  (lazy-seq
   (let  [ps (take preamble xs)
          n (first (drop preamble xs))]
     (when n
       (let [p-sums (let [indexed (map vector (range) ps)]
                      (for [a indexed
                            b indexed
                            :when (< (first a) (first b))]
                        (+ (second a) (second b))))]
         (cons
          {(if (first (filter (partial = n) p-sums))
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
      (let [result (loop [mn (first xs)
                          mx mn
                          sum mn
                          remaining (rest xs)]
                     (let [n (first remaining)
                           sum (+ sum n)
                           mn (min mn n)
                           mx (max mx n)]
                       (if (= sum target)
                         [mn mx]
                         (when (< sum target)
                           (recur mn mx sum (rest remaining))))))]
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
