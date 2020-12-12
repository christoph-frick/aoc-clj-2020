(ns ^:day-14 aoc-clj-2020.test-solution-14
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-14 :as t]))

(deftest test-solution-14
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
