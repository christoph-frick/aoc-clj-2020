(ns aoc-clj-2020.test-solution-5
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-5 :as t]))

(deftest test-parse-boarding-pass
  (are [bp r] (= r (t/boarding-pass-to-seat-id bp))
    "FBFBBFFRLR" 357))

(deftest test-solution-5
  (are [f r] (= (f) r)
    t/part-1 883
    t/part-2 532))
