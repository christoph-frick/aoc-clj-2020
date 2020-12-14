(ns ^:day-14 aoc-clj-2020.test-solution-14
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-14 :as t]))

(def example-1
  "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0")

(def example-2
  "mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1")

(deftest test-parse
  (is (= [[:mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"]
          [:mem 8 11]
          [:mem 7 101]
          [:mem 8 0]] (t/parse example-1))))

(deftest test-mask-to-bin
  (is (= {:mask-or 64
          :mask-and 68719476733}
         (t/mask-to-bin "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"))))

(deftest test-run-val
  (is (= 165 (->> example-1 (t/parse) (reduce t/step-1 t/initial-state-1) (t/sum-memory)))))

(deftest test-apply-mask-2
  (are [result mask value] (= result (t/apply-mask-2 {:mask mask} value))
    "000000000000000000000000000000X1101X" "000000000000000000000000000000X1001X" 2r000000000000000000000000000000101010
    "00000000000000000000000000000001X0XX" "00000000000000000000000000000000X0XX" 2r000000000000000000000000000000011010))

(deftest test-mask-to-values
  (are [mask value result] (= result (t/mask-to-values (t/apply-mask-2 {:mask mask} value)))
    "000000000000000000000000000000X1001X" 42 [26 27 58 59]
    "00000000000000000000000000000001X0XX" 26 [16 17 18 19 24 25 26 27]))

(deftest test-2
  (is (= 208 (->> example-2 (t/parse) (reduce t/step-2 t/initial-state-2) (t/sum-memory)))))

(deftest test-solution-14
  (are [f r] (= r (f))
    t/part-1 9615006043476
    t/part-2 4275496544925))
