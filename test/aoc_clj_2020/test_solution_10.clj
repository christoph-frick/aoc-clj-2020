(ns aoc-clj-2020.test-solution-10
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-10 :as t]))

(deftest test-solution-10
  (are [f r] (= (f) r)
    t/part-1 nil
    t/part-2 nil))
