(ns ^:day-17 aoc-clj-2020.test-solution-17
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-17 :as t]))

(deftest test-solution-17
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
