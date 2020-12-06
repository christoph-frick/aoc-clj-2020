(ns aoc-clj-2020.test-solution-6
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-6 :as t]))

(def example
  "abc

a
b
c

ab
ac

a
a
a
a

b")

(deftest test-parse-answers
  (is (= (t/parse-answers example)
         [#{\a \b \c}
          #{\a \b \c}
          #{\a \b \c}
          #{\a}
          #{\b}])))

(deftest test-sum-answers
  (is (= 11 (t/sum-answers example))))

(deftest test-solution-6
  (are [f r] (= (f) r)
    t/part-1 6291
    t/part-2 nil))
