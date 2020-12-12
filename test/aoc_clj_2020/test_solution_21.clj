(ns ^:day-21 aoc-clj-2020.test-solution-21
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-21 :as t]))

(deftest test-solution-21
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
