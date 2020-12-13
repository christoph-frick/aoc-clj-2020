(ns aoc-clj-2020.solution-13
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn parse
  [s]
  (let [[target lines] (str/split-lines s)]
    {:target (lp/atoi target)
     :lines (mapv lp/atoi (re-seq #"\d+" lines))}))

(defn distance
  [target line]
  (- (* line (int (Math/ceil (/ target line))))
     target))

(defn solve
  [{:keys [target lines]}]
  (let [tt (into (sorted-map)
                 (map (juxt (partial distance target) identity) lines))
        [line wait] (first tt)]
    (* line wait)))

(defn part-1
  []
  (->> (li/read-input "13.txt")
       (parse)
       (solve)))

(defn part-2
  []
  nil)
