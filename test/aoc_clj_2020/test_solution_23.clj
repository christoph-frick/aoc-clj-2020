(ns ^:day-23 aoc-clj-2020.test-solution-23
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-23 :as t]))

(def data1
  "389125467")

(deftest test-parse
  (is (= [3 8 9 1 2 5 4 6 7]
         (t/parse data1))))

(deftest test-step-10
  (is (= [[3 8 9 1 2 5 4 6 7]
          [2 8 9 1 5 4 6 7 3]
          [5 4 6 7 8 9 1 3 2]
          [8 9 1 3 4 6 7 2 5]
          [4 6 7 9 1 3 2 5 8]
          [1 3 6 7 9 2 5 8 4]
          [9 3 6 7 2 5 8 4 1]
          [2 5 8 3 6 7 4 1 9]
          [6 7 4 1 5 8 3 9 2]
          [5 7 4 1 8 3 9 2 6]
          [8 3 7 4 1 9 2 6 5]]
         (reductions
          (fn [game _] (t/step game))
          (t/parse data1)
          (range 10)))))

(deftest test-run
  (are [steps result] (= result (-> data1
                                    (t/parse)
                                    (t/run steps)
                                    (t/solution-1)))
    10 "92658374"
    100 "67384529"))

(deftest test-solution-23
  (are [f r] (= r (f))
    t/part-1 "36542897"
    t/part-2 nil))
