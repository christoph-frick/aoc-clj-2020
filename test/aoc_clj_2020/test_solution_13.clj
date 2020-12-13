(ns ^:day-13 aoc-clj-2020.test-solution-13
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-13 :as t]))

(def example
  "939\n7,13,x,x,59,x,31,19")

(deftest test-distance
  (are [result target line] (= result (t/distance target line))
    6 939 7
    10 939 13
    5 939 59
    0 3417 17))

(deftest test-parse
  (is (= {:target 939 :lines [7, 13, :x, :x, 59, :x, 31, 19]}
         (t/parse example))))

(deftest test-minimize-wait
  (is (= 295 (-> example t/parse t/minimize-wait))))

(deftest test-bezout-identity
  (are [result a b] (= result (t/bezout-identity a b))
    [1 -1] 4 3
    [5 -2] 5 12
    [7 -1] 3 20
    [4 -1] 4 15
    [5 -2] 5 12))

(deftest test-brute-force
  (are [s result] (= result (-> s (t/parse) (t/brute-force)))
    example 1068781
    "0\n17,x,13,19" 3417
    "0\n67,7,59,61" 754018
    "0\n67,x,7,59,61" 779210
    "0\n67,7,x,59,61" 1261476
    "0\n1789,37,47,1889" 1202161486))

(deftest test-crt
  (are [s result] (= result (-> s (t/parse) (t/crt)))
    example 1068781
    "0\n17,x,13,19" 3417
    "0\n67,7,59,61" 754018
    "0\n67,x,7,59,61" 779210
    "0\n67,7,x,59,61" 1261476
    "0\n1789,37,47,1889" 1202161486))

(deftest test-solution-13
  (are [f r] (= r (f))
    t/part-1 5946
    #_#_t/part-2 nil))
