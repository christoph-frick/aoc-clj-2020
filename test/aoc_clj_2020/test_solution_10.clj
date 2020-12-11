(ns aoc-clj-2020.test-solution-10
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-10 :as t]))

(def example-1
  "16
10
15
5
1
11
7
19
6
12
4")

(def example-2
  "28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3")

(deftest test-differences
  (are [input result] (= result (-> input t/parse t/prepare t/differences))
    example-1 {1 7, 3 5}
    example-2 {1 22, 3 10}))

(deftest test-valid-distance?
  (are [a b r] (= r (t/valid-distance? a b))
    0 0 false
    0 1 true
    0 2 true
    0 3 true
    0 4 false))

(deftest test-combinations
  (are [input result] (= result (-> input t/parse t/prepare t/combinations))
    example-1 8
    example-2 19208))

(deftest test-solution-10
  (are [f r] (= r (f))
    t/part-1 2176
    t/part-2 18512297918464))
