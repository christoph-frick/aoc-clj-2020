(ns ^:day-24 aoc-clj-2020.test-solution-24
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-24 :as t]))

(def ex1
  "sesenwnenenewseeswwswswwnenewsewsw
neeenesenwnwwswnenewnwwsewnenwseswesw
seswneswswsenwwnwse
nwnwneseeswswnenewneswwnewseswneseene
swweswneswnenwsewnwneneseenw
eesenwseswswnenwswnwnwsewwnwsene
sewnenenenesenwsewnenwwwse
wenwwweseeeweswwwnwwe
wsweesenenewnwwnwsenewsenwwsesesenwne
neeswseenwwswnwswswnw
nenwswwsewswnenenewsenwsenwnesesenew
enewnwewneswsewnwswenweswnenwsenwsw
sweneswneswneneenwnewenewwneswswnese
swwesenesewenwneswnwwneseswwne
enesenwswwswneneswsenwnewswseenwsese
wnwnesenesenenwwnenwsewesewsesesew
nenewswnwewswnenesenwnesewesw
eneswnwswnwsenenwnwnwwseeswneewsenese
neswnwewnwnwseenwseesewsenwsweewe
wseweeenwnesenwwwswnew")

(deftest test-parse
  (is (= [["se" "se" "nw" "ne" "ne" "ne" "w" "se" "e" "sw" "w" "sw" "sw" "w" "ne" "ne" "w" "se" "w" "sw"]]
         (t/parse "sesenwnenenewseeswwswswwnenewsewsw"))))

(deftest test-instr-to-pos
  (is (= [-2 1 1]
         (-> (t/parse "neeswseenwwswnwswswnw") ; ne e sw se e nw w sw nw sw sw nw
             (first)
             (t/instr-to-pos)))))

(deftest test-setup-grid
  (is (= 10 (-> ex1
                (t/parse)
                (t/setup-grid)
                (t/count-flipped)))))

(deftest test-count-flipped-neighbours
  (is (= 2 (t/count-flipped-neighbours #{[0 -1 1] [0 1 -1] [2 -1 -1] [3 -2 -1]} t/ZERO))))

(deftest test-flip-rule-1
  (are [grid result] (= result (t/flip-rule-1 grid))
    #{t/ZERO} #{}
    #{t/ZERO [0 -1 1]} #{t/ZERO [0 -1 1]}
    #{t/ZERO [0 -1 1] [0 1 -1]} #{t/ZERO [0 -1 1] [0 1 -1]}
    #{t/ZERO [0 -1 1] [0 1 -1] [-1 1 0]} #{[0 -1 1] [0 1 -1] [-1 1 0]}))

(deftest test-flip-rule-2
  (are [grid result] (= result (t/flip-rule-2 grid))
    #{t/ZERO} #{}
    #{[0 -1 1] [0 1 -1]} #{t/ZERO}))

(deftest test-run
  (are [steps result] (= result
                         (-> ex1
                             (t/parse)
                             (t/setup-grid)
                             (t/run steps)
                             (t/count-flipped)))
    0 10
    1 15
    2 12
    3 25
    4 14
    5 23
    6 28
    7 41
    8 37
    9 49
    10 37
    20 132
    30 259
    40 406
    50 566
    60 788
    70 1106
    80 1373
    90 1844
    100 2208))

(deftest test-solution-24
  (are [f r] (= r (f))
    t/part-1 322
    t/part-2 3831))
