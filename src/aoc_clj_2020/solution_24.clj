(ns aoc-clj-2020.solution-24
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]))

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

(defn setup-grid
  [instrs]
  (reduce
   (fn [grid instr]
     (grid-flip grid (instr-to-pos instr)))
   #{}
   instrs))

(defn count-flipped
  [grid]
  (count grid))

(defn neigbours
  [pos]
  (map
   (partial move pos)
   (keys directions)))

(defn count-flipped-neighbours
  [grid pos]
  (count
   (filter
    (partial contains? grid)
    (neigbours pos))))

(defn flip-rule-1
  "Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white."
  [grid]
  (into #{}
        (filter (fn [pos]
                  (< 0 (count-flipped-neighbours grid pos) 3)))
        grid))

(defn flip-rule-2
  "Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black."
  [grid]
  (into #{}
        (comp
         (mapcat neigbours)
         (remove (partial contains? grid))
         (filter (fn [pos]
                   (= 2 (count-flipped-neighbours grid pos)))))
        grid))

(defn conway-step
  [grid]
  (into (flip-rule-1 grid)
        (flip-rule-2 grid)))

(defn run
  [grid steps]
  (if (zero? steps)
    grid
    (recur (conway-step grid) (dec steps))))

(defn part-1
  []
  (-> (li/read-input "24.txt")
       (parse)
       (setup-grid)
       (count-flipped)))

(defn part-2
  []
  (-> (li/read-input "24.txt")
      (parse)
      (setup-grid)
      (run 100)
      (count-flipped)))
