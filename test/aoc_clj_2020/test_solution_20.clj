(ns ^:day-20 aoc-clj-2020.test-solution-20
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-20 :as t]))

(def data-example-1
  "Tile 2311:
..##.#..#.
##..#.....
#...##..#.
####.#...#
##.##.###.
##...#.###
.#.#.#..##
..#....#..
###...#.#.
..###..###

Tile 1951:
#.##...##.
#.####...#
.....#..##
#...######
.##.#....#
.###.#####
###.##.##.
.###....#.
..#.#..#.#
#...##.#..

Tile 1171:
####...##.
#..##.#..#
##.#..#.#.
.###.####.
..###.####
.##....##.
.#...####.
#.##.####.
####..#...
.....##...

Tile 1427:
###.##.#..
.#..#.##..
.#.##.#..#
#.#.#.##.#
....#...##
...##..##.
...#.#####
.#.####.#.
..#..###.#
..##.#..#.

Tile 1489:
##.#.#....
..##...#..
.##..##...
..#...#...
#####...#.
#..#.#.#.#
...#.#.#..
##.#...##.
..##.##.##
###.##.#..

Tile 2473:
#....####.
#..#.##...
#.##..#...
######.#.#
.#...#.#.#
.#########
.###.#..#.
########.#
##...##.#.
..###.#.#.

Tile 2971:
..#.#....#
#...###...
#.#.###...
##.##..#..
.#####..##
.#..####.#
#..#.#..#.
..####.###
..#.#.###.
...#.#.#.#

Tile 2729:
...#.#.#.#
####.#....
..#.#.....
....#..#.#
.##..##.#.
.#.####...
####.#.#..
##.####...
##..#.##..
#.##...##.

Tile 3079:
#.#.#####.
.#..######
..#.......
######....
####.#..#.
.#...#.##.
#.#####.##
..#.###...
..#.......
..#.###...")

(deftest test-parse
  (let [result (t/parse data-example-1)]
    (is (= [2311 1951 1171 1427 1489 2473 2971 2729 3079] (map :id result)))))

(deftest test-test-borders
  (let [result (-> data-example-1
                   (t/parse)
                   (last)
                   (t/borders))]
    ; Tile 3079:
    ; #.#.#####.
    ; .#..######
    ; ..#.......
    ; ######....
    ; ####.#..#.
    ; .#...#.##.
    ; #.#####.##
    ; ..#.###...
    ; ..#.......
    ; ..#.###... )
    (is (= #{["#.#.#####." ".#....#..." "..#.###..." "#..##.#..."]
             ["#..##.#..." "#.#.#####." ".#....#..." "..#.###..."]
             [".#....#..." "..#.###..." "#..##.#..." "#.#.#####."]
             ["...#.##..#" "..#.###..." "...#....#." "#.#.#####."]
             ["...#....#." "#.#.#####." "...#.##..#" "..#.###..."]
             ["...###.#.." ".#....#..." ".#####.#.#" "#..##.#..."]
             ["#.#.#####." "...#.##..#" "..#.###..." "...#....#."]
             [".#....#..." ".#####.#.#" "#..##.#..." "...###.#.."]
             [".#####.#.#" "#..##.#..." "...###.#.." ".#....#..."]
             ["..#.###..." "#..##.#..." "#.#.#####." ".#....#..."]
             ["..#.###..." "...#....#." "#.#.#####." "...#.##..#"]
             ["#..##.#..." "...###.#.." ".#....#..." ".#####.#.#"]} result))))

(deftest test-potentials
  (is (= {1171 #{1489 2473},
          1427 #{1489 2311 2473 2729},
          1489 #{1171 1427 2971},
          1951 #{2311 2729},
          2311 #{1427 1951 3079},
          2473 #{1171 1427 3079},
          2729 #{1427 1951 2971},
          2971 #{1489 2729},
          3079 #{2311 2473}}
         (-> data-example-1 (t/parse) (t/potentials)))))

(deftest test-quickn-dirty-corner-sum
  (is (= 20899048083289 (-> data-example-1 (t/parse) (t/quickn-dirty-corner-sum)))))

(deftest test-solution-20
   (are [f r] (= r (f))
    t/part-1 29584525501199
    t/part-2 nil))
