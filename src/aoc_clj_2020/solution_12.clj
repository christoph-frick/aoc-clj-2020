(ns aoc-clj-2020.solution-12
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn parse
  [s]
  (mapv (fn [line]
          (let [[_ op amt] (re-find #"(.)(\d+)" line)]
            {:op (keyword op)
             :amt (lp/atoi amt)}))
        (str/split-lines s)))

(def directions
  [:E
   :S
   :W
   :N])

(def move-offsets
  {:N [0 -1]
   :E [1 0]
   :S [0 1]
   :W [-1 0]})

(def rotations
  (into {}
        (for [[d & rots] (take 4 (partition 4 1 (cycle directions)))
              r (range 1 4)]
          [[d (* r 90)] (nth rots (dec r))])))

(def left-to-right
  {90 270
   180 180
   270 90})

(defn pos-translate
  [[ax ay] [bx by]]
  [(+ ax bx) (+ ay by)])

(defn pos-scale
  [[ax ay] factor]
  [(* ax factor) (* ay factor)])

(defn rotate
  [{:keys [dir] :as state} {:keys [op amt]}]
  (assoc state
         :dir (rotations [dir (if (= op :L) (left-to-right amt) amt)])))

(defn move-abs
  [{:keys [pos] :as state} {:keys [op amt]}]
  (assoc state
         :pos (pos-translate
               pos
               (pos-scale
                (move-offsets op)
                amt))))

(defn move-rel
  [{:keys [pos dir] :as state} {:keys [op amt]}]
  (assoc state
         :pos (pos-translate
               pos
               (pos-scale
                (move-offsets dir)
                amt))))

(def initial-state
  {:pos [0 0]
   :dir :E})

(def rules
  {:N move-abs
   :S move-abs
   :E move-abs
   :W move-abs
   :F move-rel
   :R rotate
   :L rotate})

(defn step-1
  [state {:keys [op] :as instr}]
  ((rules op) state instr))

(defn run
  [instrs]
  (reduce step-1 initial-state instrs))

(defn distance
  [{:keys [pos] :as state}]
  (apply + pos))

(defn part-1
  []
  (->> (li/read-input "12.txt")
       (parse)
       (run)
       (distance)))

(defn part-2
  []
  nil)
