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

(deftest test-solution1
  (is (= 10 (-> ex1
                (t/parse)
                (t/solution1)
                (t/count-flipped)))))

(deftest test-solution-24
  (are [f r] (= r (f))
    t/part-1 322
    t/part-2 nil))
