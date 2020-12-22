(ns aoc-clj-2020.solution-22
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [clojure.string :as str])
  (:import [clojure.lang PersistentQueue]))

(defn parse
  [s]
  (->> s
       (lp/split-groups)
       (mapv (fn [line]
               (into (PersistentQueue/EMPTY) (map lp/atoi) (rest (str/split-lines line)))))))

(defn win-round
  [deck player cards]
  (update deck player into (if (zero? player) cards (reverse cards))))

(defn draw
  [deck]
  ((juxt
     (partial mapv pop)
     (partial mapv peek))
   deck))

(defn step
  [deck]
  (let [[deck cards] (draw deck)]
    (win-round deck (if (apply < cards) 1 0) cards)))

(defn winner
  [[p1 p2]]
  (cond
    (empty? p1) 1
    (empty? p2) 0))

(defn score
  [deck player]
  (apply +
         (map *
              (reverse (get deck player))
              (iterate inc 1))))

(defn run
  [deck]
  (if-let [winner (winner deck)]
    (score deck winner)
    (recur (step deck))))

(defn part-1
  []
  (->> (li/read-input "22.txt")
       (parse)
       (run)))

(defn part-2
  []
  nil)
