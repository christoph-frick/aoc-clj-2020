(ns aoc-clj-2020.solution-11
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.iter :as liter]
            [clojure.string :as str]))

(def empty-seat \L)
(def occupied-seat \#)
(def no-seat \.)

(defn parse
  [s]
  (let [rows (str/split-lines s)]
    {:height (count rows)
     :width (count (first rows))
     :grid (into {}
                 cat
                 (map-indexed (fn [y row]
                                (map-indexed (fn [x cell]
                                               [[x y] cell])
                                             row))
                              rows))}))

(defn to-string
  [{:keys [width height grid]}]
  (str/join "\n"
            (for [y (range height)]
              (str/join (for [x (range width)]
                          (grid [x y]))))))

(defn surrounding
  [grid [cx cy]]
  (frequencies
   (for [x (range -1 2)
         y (range -1 2)
         :when (not (and (zero? x) (zero? y)))
         :let [cell (grid [(+ cx x) (+ cy y)])]
         :when cell]
     cell)))

(defn rule-occupy
  [init surr]
  (when (and (= empty-seat init)
             (zero? (surr occupied-seat 0)))
    occupied-seat))

(defn rule-empty
  [init surr]
  (when (and (= occupied-seat init)
             (<= 4 (surr occupied-seat 0)))
    empty-seat))

(def rules
  [rule-occupy
   rule-empty])

(defn step-1
  [rules grid coord]
  (let [init (grid coord)
        surr (surrounding grid coord)]
    (reduce (fn [last rule]
              (if-let [next (rule init surr)]
                (reduced next)
                last))
            init
            rules)))

(defn step
  [rules {:keys [grid] :as plan}]
  (let [grid' (into {}
                    (map (fn [coord]
                           [coord (step-1 rules grid coord)])
                         (keys grid)))]
    (assoc plan :grid grid')))

(defn run
  [rules grid]
  (liter/stabilize (partial step rules) grid))

(defn count-occupied
  [{:keys [grid]}]
  (->> grid
       (vals)
       (filter (partial = occupied-seat))
       (count)))

(defn part-1
  []
  (->> (li/read-input "11.txt")
       (parse)
       (run rules)
       (count-occupied)))

(defn part-2
  []
  nil)
