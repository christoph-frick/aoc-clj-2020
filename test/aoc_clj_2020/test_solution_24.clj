(ns ^:day-24 aoc-clj-2020.test-solution-24
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-24 :as t]))

(deftest test-solution-24
  (are [f r] (= r (f))
    t/part-1 nil
    t/part-2 nil))
