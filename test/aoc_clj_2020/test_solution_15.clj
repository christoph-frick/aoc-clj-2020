(ns aoc-clj-2020.test-solution-15
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-15 :as t]))

(deftest test-solution-15
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
