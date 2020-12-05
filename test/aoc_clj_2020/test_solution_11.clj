(ns aoc-clj-2020.test-solution-11
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-11 :as t]))

(deftest test-solution-11
  (are [f r] (= (f) r)
    t/part-1 nil
    t/part-2 nil))
