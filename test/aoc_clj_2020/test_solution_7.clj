(ns aoc-clj-2020.test-solution-7
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-7 :as t]))

(def example
  "light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.")

(deftest test-parse-line
  (are [line result] (= result (t/parse-line line))

    "light red bags contain 1 bright white bag, 2 muted yellow bags."
    {:bag "light red"
     :contain
     [{:bag "bright white"
       :amount 1}
      {:bag "muted yellow"
       :amount 2}]}

    "faded blue bags contain no other bags."
    {:bag "faded blue"
     :contain []}))

(deftest test-find-containing
  (is (= #{"bright white" "dark orange" "light red" "muted yellow"}
         (-> example t/parse-lines t/to-contained (t/find-containing "shiny gold")))))

(deftest test-solution-7
  (are [f r] (= (f) r)
    t/part-1 119
    t/part-2 nil))
