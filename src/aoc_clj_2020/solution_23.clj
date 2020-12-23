(ns aoc-clj-2020.solution-23
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [clojure.string :as str]))

; should be done with a linked ring

(defn parse
  [s]
  (map lp/atoi (re-seq #"\d" s)))

(defn shift-to
  [xs fst]
  (let [[p1 p2] (split-with (partial not= fst) xs)]
    (into (vec p2) p1)))

(defn shift-list-1
  [xs]
  (conj (vec (rest xs))
        (first xs)))

(defn next-start
  [start remaining]
  (let [search (into (sorted-set start) remaining)
        [mn mx] ((juxt first last) search)]
    (loop [target (dec start)]
      (if (contains? search target)
        target
        (recur  (if (< target mn) mx (dec target)))))))

(defn step
  [[start h1 h2 h3 & remaining]]
  (let [next-start (next-start start remaining)
        result (concat [next-start]
                       [h1 h2 h3]
                       (rest (shift-to (conj remaining start) next-start)))]
    (-> result
        (shift-to start)
        (shift-list-1))))

(defn run
  [game nr]
  (if (zero? nr)
    game
    (recur (step game) (dec nr))))

(defn solution-1
  [game]
  (->> (shift-to game 1)
       (rest)
       (str/join "")))

(defn part-1
  []
  (-> "942387615"
      (parse)
      (run 100)
      (solution-1)))

(defn part-2
  []
  nil)
