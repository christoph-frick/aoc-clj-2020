(ns aoc-clj-2020.solution-17
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.transform :as transform]
            [clojure.math.combinatorics :as comb]
            [clojure.string :as str]))

(defn parse
  [s dim]
  (into #{}
        (let [filler (into [] (repeat (- dim 2) 0))]
          (for [[y row] (transform/indexed (str/split-lines s))
                [x c] (transform/indexed row)
                :when (= c \#)]
            (into [x y] filler)))))

(defn coord-op
  ([op a]
   (mapv op a))
  ([op a b]
   (mapv op a b)))

(defn coord
  [dim init]
  (into [] (repeat dim init)))

(defn coord-zero?
  [a]
  (apply = 0 a))

(defn bounding-box
  [game]
  (let [[mn mx]
        (reduce (fn [[mn mx] x]
                  [(coord-op min mn x) (coord-op max mx x)])
                [(first game) (first game)]
                game)]
    [(coord-op dec mn) (coord-op inc mx)]))

(defn coord-seq
  [min-coord max-coord]
  (apply comb/cartesian-product
         (map
           (fn [[a b]]
             (range a (inc b)))
           (transform/transpose [min-coord max-coord]))))

(def neighbour-coords
  (memoize (fn [dim]
             (remove coord-zero?
                     (coord-seq (coord dim -1) (coord dim 1))))))

(defn count-active-neighbours
  [game coord]
  (->> (neighbour-coords (count coord))
       (map (partial coord-op + coord))
       (filter (partial contains? game))
       (count)))

(defn activiate?
  [game coord]
  (let [active? (contains? game coord)
        neighbour-count (count-active-neighbours game coord)]
    (or (and active? (<= 2 neighbour-count 3))
        (and (not active?) (= neighbour-count 3)))))

(defn step
  [game]
  (into #{}
        (filter (partial activiate? game))
        (apply coord-seq (bounding-box game))))

(defn run
  [game steps]
  (if (zero? steps)
    game
    (recur (step game) (dec steps))))

(defn part-1
  []
  (-> (li/read-input "17.txt")
      (parse 3)
      (run 6)
      (count)))

(defn part-2
  []
  (-> (li/read-input "17.txt")
      (parse 4)
      (run 6)
      (count)))
