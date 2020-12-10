(ns aoc-clj-2020.test-solution-23
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-23 :as t]))

(deftest test-solution-23
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
