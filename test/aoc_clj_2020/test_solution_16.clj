(ns ^:day-16 aoc-clj-2020.test-solution-16
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-16 :as t]))

(def example-1
  "class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12")

(deftest test-parse
  (is (= {:rules {:class (into #{} (concat (range 1 4) (range 5 8)))
                  :row (into #{} (concat (range 6 12) (range 33 45)))
                  :seat (into #{} (concat (range 13 41) (range 45 51)))}
          :your-ticket [7,1,14]
          :nearby-tickets [[7,3,47]
                           [40,4,50]
                           [55,2,20]
                           [38,6,12]]}
         (t/parse example-1))))

(deftest test-sum-simple-violations
  (is (= 71 (-> example-1 (t/parse) (t/sum-simple-violations)))))

(deftest test-solution-16
  (are [f r] (= r (f))
    t/part-1 26009
    t/part-2 nil))
