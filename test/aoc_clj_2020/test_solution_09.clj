(ns ^:day-09 aoc-clj-2020.test-solution-09
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-09 :as t]))

(def example
  "35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576")

(deftest test-attack
  (is (= 127 (->> example (t/parse) (t/attack 5) (first)))))

(deftest test-weakness
  (is (= 62 (->> example (t/parse) (t/find-weakness 127) (t/sum-weakness)))))

(deftest test-solution-9
  (are [f r] (= r (f))
    t/part-1 133015568
    t/part-2 16107959))
