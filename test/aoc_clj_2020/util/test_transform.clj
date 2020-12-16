(ns ^:lib aoc-clj-2020.util.test-transform
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.util.transform :as t]))

(deftest test-transpose
  (is (= [[1 4 7]
          [2 5 8]
          [3 6 9]]
         (t/transpose [[1 2 3]
                       [4 5 6]
                       [7 8 9]]))))
