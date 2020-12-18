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

(deftest test-ast-ltr
  (are [formula restult] (= restult (-> formula t/parse t/term->ast-ltr))
    "1 + 2" ['+ [1 2]]
    "2 * 3 + (4 * 5)" ['+ [['* [2 3]] ['* [4 5]]]]
    "(2 * 3) + (4 * 5)" ['+ [['* [2 3]] ['* [4 5]]]]))

(deftest test-solve
  (are [ast result] (= result (t/solve ast))
    ['+ [1 2]] 3
    ['+ [['* [2 3]] ['* [4 5]]]] 26))

(deftest test-calc-ltr
  (are [formula result] (= result (t/calc t/term->ast-ltr formula))
    "1 + 2 * 3 + 4 * 5 + 6" 71
    "2 * 3 + (4 * 5)" 26
    "5 + (8 * 3 + 9 + 3 * 4 * 3)" 437
    "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))" 12240
    "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2" 13632))

(deftest test-ast-plus-pred
  (are [formula restult] (= restult (-> formula t/parse t/term->ast-plus-pred (doto println)))
    "1 + 2" ['+ [1 2]]
    "2 * 3 + (4 * 5)" ['* [2 ['+ [3 ['* [4 5]]]]]]
    "(2 * 3) + (4 * 5)" ['+ [['* [2 3]] ['* [4 5]]]]))

(deftest test-calc-plus-pred
  (are [formula result] (= result (t/calc t/term->ast-plus-pred formula))
    "1 + 2 * 3 + 4 * 5 + 6" 231
    "1 + (2 * 3) + (4 * (5 + 6))" 51
    "2 * 3 + (4 * 5)" 46
    "5 + (8 * 3 + 9 + 3 * 4 * 3)" 1445
    "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))" 669060
    "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2" 23340))

(deftest test-solution-18
  (are [f r] (= r (f))
    t/part-1 7293529867931
    t/part-2 60807587180737))
