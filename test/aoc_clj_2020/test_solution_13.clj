(ns ^:day-13 aoc-clj-2020.test-solution-13
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-13 :as t]))

(def example
  "939
7,13,x,x,59,x,31,19")

(deftest test-parse
  (is (= {:target 939 :lines [7, 13, 59, 31, 19]}
         (t/parse example))))

(deftest test-solve
  (is (= 295 (-> example t/parse t/solve))))

(deftest test-solution-13
  (are [f r] (= r (f))
    t/part-1 5946
    t/part-2 nil))
