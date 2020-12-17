(ns aoc-clj-2020.solution-17
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.transform :as transform]
            [clojure.string :as str]))

(defn parse
  [s]
  (into #{}
        (for [[y row] (transform/indexed (str/split-lines s))
              [x c] (transform/indexed row)
              :when (= c \#)]
          [x y 0])))

(def ONE [1 1 1])

(defn coord-op
  [op [ax ay az] [bx by bz]]
  [(op ax bx) (op ay by) (op az bz)])

(defn bounding-box
  [game]
  (let [[mn mx] (reduce (fn [[mn mx] x]
                          [(coord-op min mn x) (coord-op max mx x)])
                        [(first game) (first game)]
                        game)]
    [(coord-op - mn ONE) (coord-op + mx ONE)]))

(defn count-active-neighbours
  [game coord]
  (count
   (filter (partial contains? game)
           (let [r (range -1 2)]
             (for [x r, y r, z r
                   :when (not (= 0 x y z))]
               (coord-op + coord [x y z]))))))

(defn step
  [game]
  (let [[[min-x min-y min-z] [max-x max-y max-z]] (bounding-box game)]
    (into #{}
          (for [x (range min-x (inc max-x))
                y (range min-y (inc max-y))
                z (range min-z (inc max-z))
                :let [coord [x y z]
                      active? (contains? game coord)
                      neighbour-count (count-active-neighbours game coord)]
                :when (or (and active? (<= 2 neighbour-count 3))
                          (and (not active?) (= neighbour-count 3)))]
            coord))))

(defn run
  [game steps]
  (if (zero? steps)
    game
    (recur (step game) (dec steps))))

(defn part-1
  []
  (-> (li/read-input "17.txt")
      (parse)
      (run 6)
      (count)))

(defn part-2
  []
  nil)
