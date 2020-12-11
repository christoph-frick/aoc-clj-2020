(ns ^:day-20 aoc-clj-2020.test-solution-20
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-20 :as t]))

(deftest test-solution-20
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
