(ns aoc-clj-2020.solution-10
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]))

(defn parse
  [s]
  (lp/lines-to lp/atoi s))

(defn prepare
  [lines]
  (let [sorted (sort lines)]
    (concat
     (conj sorted 0)
     [(+ 3 (last sorted))])))

(defn differences
  [prepared]
  (-> (map (fn [a b]
             (- b a))
           prepared
           (rest prepared))
      (frequencies)))

(defn valid-distance?
  [a b]
  (<= 1 (- b a) 3))

(defn combinations
  [prepared]
  (let [goal (last prepared)
        adj-map (loop [xs prepared
                       r {}]
                  (if (seq xs)
                    (recur (rest xs)
                           (let [f (first xs)
                                 children (->> (take 3 (rest xs))
                                               (filter (partial valid-distance? f))
                                               (set))]
                             (if (pos? (count children))
                               (assoc r f children)
                               r)))
                    r))
        total* (fn [total k]
                 (if (= goal k)
                   1
                   (apply + (map total (get adj-map k)))))
        total (let [t (memoize total*)]
                (fn s [n] (t s n)))]
    (total 0)))

(defn result
  [freqs]
  (* (get freqs 1) (get freqs 3)))

(defn part-1
  []
  (-> (li/read-input "10.txt")
      (parse)
      (prepare)
      (differences)
      (result)))

(defn part-2
  []
  (-> (li/read-input "10.txt")
      (parse)
      (prepare)
      (combinations)))
