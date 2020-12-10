(ns aoc-clj-2020.test-solution-12
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-12 :as t]))

(deftest test-solution-12
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
