(ns ^:day-12 aoc-clj-2020.test-solution-12
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-12 :as t]))

(def example
  "F10
N3
F7
R90
F11")

(deftest test-parse
  (is (= [{:op :F :amt 10}
          {:op :N :amt 3}
          {:op :F :amt 7}
          {:op :R :amt 90}
          {:op :F :amt 11}]
         (t/parse example))))

(deftest test-run
  (is (= 25 (->> example (t/parse) (t/run) (t/distance)))))

(deftest test-solution-12
  (are [f r] (= r (f))
    t/part-1 2458
    t/part-2 nil))
