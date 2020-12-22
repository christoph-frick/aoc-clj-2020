(ns ^:day-22 aoc-clj-2020.test-solution-22
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-22 :as t]))

(def example-1
  "Player 1:
9
2
6
3
1

Player 2:
5
8
4
7
10")

(deftest test-parse
  (is (= [[9 2 6 3 1]
          [5 8 4 7 10]]
         (t/parse example-1))))

(deftest test-step
  (is (= [[2 6 3 1 9 5]
          [8 4 7 10]]
         (-> example-1 t/parse t/step))))

(deftest test-winner
  (are [deck result] (= result (t/winner deck))
    [[] [1]] 1
    [[1] []] 0
    [[1] [2]] nil))

(deftest test-run
  (is (= 306
         (-> example-1 t/parse t/run))))

(deftest test-solution-22
  (are [f r] (= r (f))
    t/part-1 33010
    t/part-2 nil))
