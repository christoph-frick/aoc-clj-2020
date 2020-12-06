(ns aoc-clj-2020.solution-6
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-answers
  [s]
  (->> (str/split s #"\n\n")
       (mapv (fn [s]
               (mapv (partial into #{})
                     (str/split s #"\n"))))))

(defn group-answers
  [set-op answers]
  (mapv (partial apply set-op) answers))

(defn group-answers-union
  [answers]
  (group-answers set/union answers))

(defn group-answers-intersection
  [answers]
  (group-answers set/intersection answers))

(defn sum-answers
  [answers]
  (->> answers
       (map count)
       (apply +)))

(defn part-1
  []
  (->> (li/read-input "6.txt")
       (parse-answers)
       (group-answers-union)
       (sum-answers)))

(defn part-2
  []
  (->> (li/read-input "6.txt")
       (parse-answers)
       (group-answers-intersection)
       (sum-answers)))
