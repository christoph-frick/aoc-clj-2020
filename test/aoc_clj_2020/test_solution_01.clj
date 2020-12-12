(ns ^:day-01 aoc-clj-2020.test-solution-01
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-01 :as t]))

(deftest test-sum=2020?
  (are [xs r] (= (t/sum=2020? xs) r)
    [2020] true
    [1010 1010] true
    [1010] false))

(deftest test-solution-1
  (are [f r] (= r (f))
    t/part-1          866436
    t/part-2          276650720))
