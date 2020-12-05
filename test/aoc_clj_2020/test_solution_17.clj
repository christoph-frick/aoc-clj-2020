(ns aoc-clj-2020.test-solution-17
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-17 :as t]))

(deftest test-solution-17
  (are [f r] (= (f) r)
    t/part-1 nil
    t/part-2 nil))
