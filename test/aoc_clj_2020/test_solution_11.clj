(ns aoc-clj-2020.test-solution-11
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-11 :as t]))

(def examples-part-1
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

(def no-visible-neighbour
  ".##.##.
#.#.#.#
##...##
...L...
##...##
#.#.#.#
.##.##.")

(deftest test-parse-format-roundtrip
  (is (= (first examples-part-1) (-> (first examples-part-1) t/parse t/to-string))))

(deftest test-calculate-direct-neighbours
  (is (= {[0 0] #{[0 1] [1 0] [1 1]},
          [0 1] #{[0 0] [0 2] [1 0] [1 1] [1 2]},
          [0 2] #{[0 1] [1 1] [1 2]},
          [1 0] #{[0 0] [0 1] [1 1] [2 0] [2 1]},
          [1 1] #{[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]},
          [1 2] #{[0 1] [0 2] [1 1] [2 1] [2 2]},
          [2 0] #{[1 0] [1 1] [2 1]},
          [2 1] #{[1 0] [1 1] [1 2] [2 0] [2 2]},
          [2 2] #{[1 1] [1 2] [2 1]}}
         (->> "abc\ndef\nghi"
              (t/parse)
              :grid
              (t/calculate-direct-neighbours)))))

(deftest test-calculate-visible-neightbours
  (is (= #{}
         ((->> no-visible-neighbour
               (t/parse)
               :grid
               (t/calculate-visible-neighbours))
          [3 3]))))

(deftest test-surrounding
  (let [{:keys [grid neighbours]}
        (->> "abc\ndef\nghi"
             (t/parse)
             (t/setup t/calculate-direct-neighbours nil))
        coord [1 1]]
    (is (= {\a 1 \b 1 \c 1
            \d 1      \f 1
            \g 1 \h 1 \i 1}
           (t/surrounding grid neighbours coord)))))

(deftest test-rule-occupy
  (are [init surr result] (= result (t/rule-occupy init surr))
    t/empty-seat {t/occupied-seat 0} t/occupied-seat
    t/empty-seat {} t/occupied-seat
    t/empty-seat {t/occupied-seat 1} nil))

(deftest test-rule-empty
  (are [init surr result] (= result ((t/rule-empty 4) init surr))
    t/occupied-seat {t/occupied-seat 3} nil
    t/occupied-seat {} nil
    t/occupied-seat {t/occupied-seat 4} t/empty-seat))

(deftest test-step
  (let [tests (partition 2 1 examples-part-1)]
    (doseq [[grid grid'] tests]
      (is (= grid' (->> grid
                        (t/parse)
                        (t/setup
                         t/calculate-direct-neighbours
                         t/rules-part-1)
                        (t/step)
                        (t/to-string)))))))

(deftest test-count-occupied
  (is (= 37 (->> (last examples-part-1) t/parse t/count-occupied))))

(deftest test-run
  (are [rules neighbours-fn plans] (= (last plans)
                                      (->> (first plans)
                                           (t/parse)
                                           (t/setup
                                            neighbours-fn
                                            rules)
                                           (t/run)
                                           (t/to-string)))
    t/rules-part-1 t/calculate-direct-neighbours examples-part-1))

(deftest test-solution-11
  (are [f r] (= r (f))
    t/part-1 2418
    t/part-2 2144))
