(ns aoc-clj-2020.solution-5
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]))

; TODO: is there a way to do this just with bits?
(defn partition
  [[lo hi] pick]
  (let [d (bit-shift-right (- hi lo) 1)
        c (+ lo d)]
    (if (= pick :lo)
      [lo c]
      [(inc c) hi])))

(def row-col-offsets
  {\F 0
   \B 0
   \L 1
   \R 1})

(def upper-lower-config
  {\F :lo
   \B :hi
   \L :lo
   \R :hi})

(defn parse-boarding-pass
  [boarding-pass-s]
  (loop [[c & cs] boarding-pass-s
         row-col [[0 127] [0 7]]]
    (let [row-col' (update row-col (row-col-offsets c) partition (upper-lower-config c))]
      (if (seq cs)
        (recur cs row-col')
        (mapv first row-col')))))

(defn seat-id
  [[row col]]
  (+ (* row 8) col))

(defn part-1
  []
  (->> (li/read-lines "5.txt")
       (map (comp seat-id parse-boarding-pass))
       (apply max)))

(defn part-2
  []
  nil)
