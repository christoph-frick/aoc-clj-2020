(ns aoc-clj-2020.solution-24
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.transform :as transform]))

; https://www.redblobgames.com/grids/hexagons/

(defn parse
  [s]
  (lp/lines-to
   (partial re-seq #"(?:se|sw|ne|nw|e|w)")
   s))

(defn hex
  ([q r]
   (hex q r (- (- q) r)))
  ([q r s]
   (assert (zero? (+ q r s)))
   [q r s]))

(defn hex-op
  ([op a]
   (mapv op a))
  ([op a b]
   (mapv op a b)))

(def ZERO (hex 0 0 0))

(def directions
  {"e" (hex 1 0)
   "se" (hex 0 1)
   "sw" (hex -1 1)
   "w" (hex -1 0)
   "nw" (hex 0 -1)
   "ne" (hex 1 -1)})

(defn grid-flip
  [grid pos]
  ((if (contains? grid pos) disj conj) grid pos))

(defn move
  [pos dir]
  (hex-op + pos (directions dir)))

(defn instr-to-pos
  [instr]
  (reduce move ZERO instr))

(defn solution1
  [instrs]
  (reduce
   (fn [grid instr]
     (grid-flip grid (instr-to-pos instr)))
   #{}
   instrs))

(defn count-flipped
  [grid]
  (count grid))

(defn part-1
  []
  (->> (li/read-input "24.txt")
       (parse)
       (solution1)
       (count-flipped)))

(defn part-2
  []
  nil)
