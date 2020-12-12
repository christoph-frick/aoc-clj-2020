(ns ^:day-03 aoc-clj-2020.test-solution-03
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-03 :as t]))

(def tree-map ["..##......."
               "#...#...#.."
               ".#....#..#."
               "..#.#...#.#"
               ".#...##..#."
               "..#.##....."
               ".#.#.#....#"
               ".#........#"
               "#.##...#..."
               "#...##....#"
               ".#..#...#.#"])

(deftest test-count-trees-for-slope
  (are [slope r] (= r (t/count-trees-for-slope slope tree-map))
    {:right 1 :down 1} 2
    {:right 3 :down 1} 7
    {:right 5 :down 1} 3
    {:right 7 :down 1} 4
    {:right 1 :down 2} 2))

(deftest test-count-tress
  (is (= 336 (t/count-trees t/slopes tree-map))))

(deftest test-solution
  (are [f r] (= r (f))
    t/part-1 252
    t/part-2 2608962048))
