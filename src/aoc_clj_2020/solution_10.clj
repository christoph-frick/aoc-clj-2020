(ns aoc-clj-2020.solution-10
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]))

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

; failed attempts
; - just counting the possible branches: fails due to following branches depend on previous ones
; - using a zipper to count the end nodes: works, but no result after minutes for the real data
; - using a tree-seq to count the end nodes: dito zipper
; giving up for now:
; need some insipration for this kind of graph (one start, one end, many paths)
(defn combinations
  [prepared]
  (let [adj-map (loop [xs prepared
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
        ]
        (println (frequencies (map count (vals adj-map))))
    (->> (tree-seq adj-map adj-map 0)
         (filter (partial = (last prepared)))
         (count))))

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
