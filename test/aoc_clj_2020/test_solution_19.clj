(ns ^:day-19 aoc-clj-2020.test-solution-19
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-19 :as t]))

(def example-1
  "0: 1 2
1: \"a\"
2: 1 3 | 3 1
3: \"b\"")

(def example-2
  "0: 4 1 5
1: 2 3 | 3 2
2: 4 4 | 5 5
3: 4 5 | 5 4
4: \"a\"
5: \"b\"")

(def full-example-1
  "0: 4 1 5
1: 2 3 | 3 2
2: 4 4 | 5 5
3: 4 5 | 5 4
4: \"a\"
5: \"b\"

ababbb
bababa
abbbab
aaabbb
aaaabbb")

(deftest test-parse
  (are [input result] (= result (t/parse input))
    example-1 {0 ['& 1 2]
               1 "a"
               2 ['| ['& 1 3] ['& 3 1]]
               3 "b"}
    example-2 {0 ['& 4 1 5]
               1 ['| ['& 2 3] ['& 3 2]]
               2 ['| ['& 4 4] ['& 5 5]]
               3 ['| ['& 4 5] ['& 5 4]]
               4 "a"
               5 "b"}))

(deftest test-rules->r
  (are [rules re] (= (str re) (str (-> rules (t/parse) (t/rules->re))))
    example-1 #"(a((ab)|(ba)))"
    example-2 #"(a((((aa)|(bb))((ab)|(ba)))|(((ab)|(ba))((aa)|(bb))))b)"))

(deftest test-match
  (are [rules input result] (= result (boolean (re-matches (-> rules (t/parse) (t/rules->re)) input)))
    example-2 "ababbb" true
    example-2 "bababa" false
    example-2 "abbbab" true
    example-2 "aaabbb" false
    example-2 "aaaabbb" false))

(deftest test-run
  (is (= 2 (t/run full-example-1))))

(deftest test-solution-19
  (are [f r] (= r (f))
    t/part-1 235
    t/part-2 nil))
