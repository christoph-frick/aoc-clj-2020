(ns aoc-clj-2020.solution-6
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn parse-answers
  [s]
  (->> (str/split s #"\n\n")
       (mapv (comp
              (partial into #{})
              #(str/replace % "\n" "")))))

(defn sum-answers
  [s]
  (->> s
       (parse-answers)
       (map count)
       (apply +)))

(defn part-1
  []
  (->> (li/read-input "6.txt")
       (sum-answers)))

(defn part-2
  []
  nil)
