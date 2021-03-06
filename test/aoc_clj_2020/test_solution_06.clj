(ns ^:day-06 aoc-clj-2020.test-solution-06
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-06 :as t]))

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
         [[#{\a \b \c}]
          [#{\a}
           #{\b}
           #{\c}]
          [#{\a \b}
           #{\a \c}]
          [#{\a}
           #{\a}
           #{\a}
           #{\a}]
          [#{\b}]])))

(deftest test-group-answers
  (are [f r] (= r (f (t/parse-answers example)))
    t/group-answers-union
    [#{\a \b \c}
     #{\a \b \c}
     #{\a \b \c}
     #{\a}
     #{\b}]

    t/group-answers-intersection
    [#{\a \b \c}
     #{}
     #{\a}
     #{\a}
     #{\b}]))

(deftest test-solution-6
  (are [f r] (= r (f))
    t/part-1 6291
    t/part-2 3052))
