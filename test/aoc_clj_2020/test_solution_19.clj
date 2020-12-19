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

(def full-example-2
  "42: 9 14 | 10 1
9: 14 27 | 1 26
10: 23 14 | 28 1
1: \"a\"
11: 42 31
5: 1 14 | 15 1
19: 14 1 | 14 14
12: 24 14 | 19 1
16: 15 1 | 14 14
31: 14 17 | 1 13
6: 14 14 | 1 14
2: 1 24 | 14 4
0: 8 11
13: 14 3 | 1 12
15: 1 | 14
17: 14 2 | 1 7
23: 25 1 | 22 14
28: 16 1
4: 1 1
20: 14 14 | 1 15
3: 5 14 | 16 1
27: 1 6 | 14 18
14: \"b\"
21: 14 1 | 1 14
25: 1 1 | 1 14
22: 14 14
8: 42
26: 14 22 | 1 20
18: 15 15
7: 14 5 | 1 21
24: 14 1

abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
bbabbbbaabaabba
babbbbaabbbbbabbbbbbaabaaabaaa
aaabbbbbbaaaabaababaabababbabaaabbababababaaa
bbbbbbbaaaabbbbaaabbabaaa
bbbababbbbaaaaaaaabbababaaababaabab
ababaaaaaabaaab
ababaaaaabbbaba
baabbaaaabbaaaababbaababb
abbbbabbbbaaaababbbbbbaaaababb
aaaaabbaabaaaaababaa
aaaabbaaaabbaaa
aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
babaaabbbaaabaababbaabababaaab
aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba")

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

(deftest test-run-8-42
  (is (= 12 (t/run-8-42 full-example-2))))

(deftest test-solution-19
  (are [f r] (= r (f))
    t/part-1 235
    t/part-2 379))
