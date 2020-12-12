(ns ^:day-05 aoc-clj-2020.test-solution-05
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-05 :as t]))

(deftest test-parse-boarding-pass
  (are [bp r] (= r (t/boarding-pass-to-seat-id bp))
    "FBFBBFFRLR" 357))

(deftest test-solution-5
  (are [f r] (= r (f))
    t/part-1 883
    t/part-2 532))
