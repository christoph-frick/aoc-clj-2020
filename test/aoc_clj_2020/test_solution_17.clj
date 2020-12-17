(ns ^:day-17 aoc-clj-2020.test-solution-17
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-17 :as t]))

(def example-1
  ".#.
..#
###")

(deftest test-parse
  (is (= #{[1 0 0]
           [2 1 0]
           [0 2 0] [1 2 0] [2 2 0]}
         (t/parse example-1 3))))

(deftest test-bounding-box
  (is (= [[-1 -1 -1] [3 3 1]] (-> example-1 (t/parse 3) t/bounding-box))))

(deftest test-count-active-neightbours
  (is (= 5 (-> example-1 (t/parse 3) (t/count-active-neighbours [1 1 0])))))

(deftest test-run
  (are [dim result] (= result (-> example-1 (t/parse dim) (t/run 6) (count)))
    3 112
    4 848))

(deftest test-coord-seq
  (is (= #{[0 0] [1 0] [0 1] [1 1]}
         (into #{} (t/coord-seq [0 0] [1 1])))))

(deftest test-solution-17
  (are [f r] (= r (f))
    t/part-1 353
    t/part-2 2472))
