(ns ^:day-13 aoc-clj-2020.test-solution-13
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-13 :as t]))

(deftest test-solution-13
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
