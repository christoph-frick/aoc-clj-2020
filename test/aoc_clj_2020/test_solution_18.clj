(ns ^:day-18 aoc-clj-2020.test-solution-18
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-18 :as t]))

(deftest test-parse
  (are [formula result] (= result (t/parse formula))
    "1 + 2" [1 '+ 2]
    "1 + 2 * 3 + 4 * 5 + 6" [1 '+ 2 '* 3 '+ 4 '* 5 '+ 6]
    "2 * 3 + (4 * 5)" [2 '* 3 '+ [4 '* 5]]
    "5 + (8 * 3 + 9 + 3 * 4 * 3)" [5 '+ [8 '* 3 '+ 9 '+ 3 '* 4 '* 3]]
    "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"  [5 '* 9 '* [7 '* 3 '* 3 '+ 9 '* 3 '+ [8 '+ 6 '* 4]]]
    "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2" [[[2 '+ 4 '* 9] '* [6 '+ 9 '* 8 '+ 6] '+ 6] '+ 2 '+ 4 '* 2]))

(deftest test-ast
  (are [formula restult] (= restult (-> formula t/parse t/term->ast))
    "1 + 2" ['+ [1 2]]
    "2 * 3 + (4 * 5)" ['+ [['* [2 3]] ['* [4 5]]]]
    "(2 * 3) + (4 * 5)" ['+ [['* [2 3]] ['* [4 5]]]]))

(deftest test-solve
  (are [ast result] (= result (t/solve ast))
    ['+ [1 2]] 3
    ['+ [['* [2 3]] ['* [4 5]]]] 26))

(deftest test-calc
  (are [formula result] (= result (t/calc formula))
    "1 + 2 * 3 + 4 * 5 + 6" 71
    "2 * 3 + (4 * 5)" 26
    "5 + (8 * 3 + 9 + 3 * 4 * 3)" 437
    "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))" 12240
    "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2" 13632))

(deftest test-solution-18
  (are [f r] (= r (f))
    t/part-1 7293529867931
    t/part-2 nil))
