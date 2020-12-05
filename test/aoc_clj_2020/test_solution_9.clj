(ns aoc-clj-2020.test-solution-9
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-9 :as t]))

(deftest test-solution-9
  (are [f r] (= (f) r)
    t/part-1 nil
    t/part-2 nil))
