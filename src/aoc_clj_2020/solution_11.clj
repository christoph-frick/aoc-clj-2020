(ns aoc-clj-2020.solution-11
  (:require [aoc-clj-2020.util.input :as li]
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

(defn map-grid
  [coord-fn grid]
  (into {} (map (juxt identity coord-fn) (keys grid))))

(defn to-string
  [{:keys [width height grid]}]
  (str/join "\n"
            (for [y (range height)]
              (str/join (for [x (range width)]
                          (grid [x y]))))))

(defn seat?
  ([maybe-seat]
   (not (contains? #{no-seat nil} maybe-seat)))
  ([grid coord]
   (seat? (grid coord))))

(def directions
  (for [x (range -1 2)
        y (range -1 2)
        :when (not (and (zero? x) (zero? y)))]
    [x y]))

(defn add-coord
  [[x y] [x-offs y-offs]]
  [(+ x x-offs) (+ y y-offs)])

(defn make-calculate-neighbour
  [direction-fn grid]
  (fn [coord]
    (into #{}
          (comp
           (map (partial direction-fn coord))
           (filter (partial seat? grid)))
          directions)))

(defn calculate-direct-neighbours
  [grid]
  (map-grid
   (make-calculate-neighbour
    add-coord
    grid)
   grid))

(defn find-visible-neighbour
  [grid coord offs]
  (loop [curr (add-coord coord offs)]
    (when (contains? grid curr)
      (if (seat? grid curr)
        curr
        (recur (add-coord curr offs))))))

(defn calculate-visible-neighbours
  [grid]
  (map-grid
   (make-calculate-neighbour
    (partial find-visible-neighbour grid)
    grid)
   grid))

(defn surrounding
  [grid neighbours coord]
  (frequencies (map grid (neighbours coord ()))))

(defn rule-occupy
  [init surr]
  (when (and (= empty-seat init)
             (zero? (surr occupied-seat 0)))
    occupied-seat))

(defn make-rule-empty
  [limit]
  (fn [init surr]
    (when (and (= occupied-seat init)
               (<= limit (surr occupied-seat 0)))
      empty-seat)))

(defn setup
  [neighbours-fn rules plan]
  (assoc plan
         :neighbours (neighbours-fn (:grid plan))
         :rules rules))

(defn step-cell
  [{:keys [grid neighbours rules]} coord]
  (let [init (grid coord)
        surr (surrounding grid neighbours coord)]
    (reduce (fn [last rule]
              (if-let [next (rule init surr)]
                (reduced next)
                last))
            init
            rules)))

(defn step
  [{:keys [grid] :as plan}]
  (let [grid' (map-grid (partial step-cell plan) grid)]
    (assoc plan :grid grid')))

(defn run
  [plan]
  (liter/stabilize step plan))

(defn count-occupied
  [{:keys [grid]}]
  (->> grid
       (vals)
       (filter (partial = occupied-seat))
       (count)))

(def rules-part-1
  [rule-occupy
   (make-rule-empty 4)])

(def rules-part-2
  [rule-occupy
   (make-rule-empty 5)])

(defn run-part
  [neighbours-fn rules]
  (->> (li/read-input "11.txt")
       (parse)
       (setup
        neighbours-fn
        rules)
       (run)
       (count-occupied)))

(defn part-1
  []
  (run-part
   calculate-direct-neighbours
   rules-part-1))

(defn part-2
  []
  (run-part
   calculate-visible-neighbours
   rules-part-2))
