(ns aoc-clj-2020.test-solution-11
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-11 :as t]))

(def examples
  ["L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL"
   "#.##.##.##
#######.##
#.#.#..#..
####.##.##
#.##.##.##
#.#####.##
..#.#.....
##########
#.######.#
#.#####.##"
   "#.LL.L#.##
#LLLLLL.L#
L.L.L..L..
#LLL.LL.L#
#.LL.LL.LL
#.LLLL#.##
..L.L.....
#LLLLLLLL#
#.LLLLLL.L
#.#LLLL.##"
   "#.##.L#.##
#L###LL.L#
L.#.#..#..
#L##.##.L#
#.##.LL.LL
#.###L#.##
..#.#.....
#L######L#
#.LL###L.L
#.#L###.##"
   "#.#L.L#.##
#LLL#LL.L#
L.L.L..#..
#LLL.##.L#
#.LL.LL.LL
#.LL#L#.##
..L.L.....
#L#LLLL#L#
#.LLLLLL.L
#.#L#L#.##"
   "#.#L.L#.##
#LLL#LL.L#
L.#.L..#..
#L##.##.L#
#.#L.LL.LL
#.#L#L#.##
..L.L.....
#L#L##L#L#
#.LLLLLL.L
#.#L#L#.##"])

(deftest test-parse-format-roundtrip
  (is (= (first examples) (-> (first examples) t/parse t/to-string))))

(deftest test-surrounding
  (let [{:keys [grid]} (-> "abc\ndef\nghi" t/parse)]
    (is (= {\a 1 \b 1 \c 1
            \d 1      \f 1
            \g 1 \h 1 \i 1}
           (t/surrounding grid [1 1])))))

(deftest test-rule-occupy
  (are [init surr result] (= result (t/rule-occupy init surr))
    t/empty-seat {t/occupied-seat 0} t/occupied-seat
    t/empty-seat {} t/occupied-seat
    t/empty-seat {t/occupied-seat 1} nil))

(deftest test-rule-empty
  (are [init surr result] (= result (t/rule-empty init surr))
    t/occupied-seat {t/occupied-seat 3} nil
    t/occupied-seat {} nil
    t/occupied-seat {t/occupied-seat 4} t/empty-seat))

(deftest test-step
  (let [tests (partition 2 1 examples)]
    (doseq [[grid grid'] tests]
      (is (= grid' (->> grid (t/parse) (t/step t/rules) (t/to-string)))))))

(deftest test-count-occupied
  (is (= 37 (->> (last examples) t/parse t/count-occupied))))

(deftest test-run
  (is (= (last examples)
         (->> (first examples)
              (t/parse)
              (t/run t/rules)
              (t/to-string)))))

(deftest test-solution-11
  (are [f r] (= r (f))
    t/part-1 2418
    t/part-2 nil))
