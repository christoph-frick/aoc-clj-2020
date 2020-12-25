(ns ^:day-25 aoc-clj-2020.test-solution-25
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-25 :as t]))

(deftest test-enc-key
  (are [subject loop-size result] (= result (t/enc-key subject loop-size))
    7 8 5764801
    7 11 17807724
    17807724 8 14897079
    5764801 11 14897079))

(deftest test-loop-size
  (are [subject pub-key result] (= result (t/loop-size subject pub-key))
    7 [5764801 17807724] [5764801 8]
    7 [5764801] [5764801 8]
    7 [17807724] [17807724 11]))

(deftest test-solution-24
  (are [f r] (= r (f))
    t/part-1 12227206
    t/part-2 nil))
