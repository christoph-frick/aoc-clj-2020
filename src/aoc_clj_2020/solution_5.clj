(ns aoc-clj-2020.solution-5
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]))

; TODO: is there a way to do this just with bits?
(defn pick
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
    (let [row-col' (update row-col (row-col-offsets c) pick (upper-lower-config c))]
      (if (seq cs)
        (recur cs row-col')
        (mapv first row-col')))))

(defn seat-id
  [[row col]]
  (+ (* row 8) col))

(defn load-seat-ids
  [file-name]
  (->> file-name
       (li/read-lines)
       (into (sorted-set) (map (comp seat-id parse-boarding-pass)))))

(defn part-1
  []
  (last (load-seat-ids "5.txt")))

(defn part-2
  []
  (let [ids (->> (load-seat-ids "5.txt"))
        [min-id max-id] ((juxt first last) ids)]
    (reduce (fn [_ s]
              (when (not (contains? ids s))
                (reduced s)))
            (range min-id (inc max-id)))))
