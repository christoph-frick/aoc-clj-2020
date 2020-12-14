(ns ^:day-14 aoc-clj-2020.test-solution-14
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-14 :as t]))

(def example
  "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0")

(deftest test-parse
  (is (= [[:mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"]
          [:mem 8 11]
          [:mem 7 101]
          [:mem 8 0]] (t/parse example))))

(deftest test-mask-to-bin
  (is (= {:mask-or 64
          :mask-and 68719476733}
         (t/mask-to-bin "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"))))

(deftest test-run
  (is (= 165 (->> example (t/parse) (t/run t/initial-state) (t/sum-memory)))))

(deftest test-solution-14
  (are [f r] (= r (f))
    t/part-1 9615006043476
    t/part-2 nil))
