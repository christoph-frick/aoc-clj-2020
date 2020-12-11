(ns ^:day-19 aoc-clj-2020.test-solution-19
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-19 :as t]))

(deftest test-solution-19
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
