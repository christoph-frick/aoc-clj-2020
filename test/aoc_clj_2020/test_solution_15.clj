(ns ^:day-15 aoc-clj-2020.test-solution-15
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-15 :as t]))

(def example-1
  "0,3,6")

(deftest test-parse
  (is (= [0 3 6] (t/parse example-1))))

(deftest test-setup
  (is (= {:recent {0 1
                   3 2}
          :turn 3
          :spoken 6}
         (-> example-1 t/parse t/setup))))

(deftest test-step
  (is (= [{:recent {0 1
                    3 2}
           :turn 3
           :spoken 6}
          {:recent {0 1
                    3 2
                    6 3}
           :turn 4
           :spoken 0}
          {:recent {0 4
                    3 2
                    6 3}
           :turn 5
           :spoken 3}
          {:recent {0 4
                    3 5
                    6 3}
           :turn 6
           :spoken 3}
          {:recent {0 4
                    3 6
                    6 3}
           :turn 7
           :spoken 1}
          {:recent {0 4
                    1 7
                    3 6
                    6 3}
           :turn 8
           :spoken 0}
          {:recent {0 8
                    1 7
                    3 6
                    6 3}
           :turn 9
           :spoken 4}
          {:recent {0 8
                    1 7
                    3 6
                    4 9
                    6 3}
           :turn 10
           :spoken 0}]
         (let [game (-> example-1 t/parse)]
           (take 8 (iterate t/step (t/setup game)))))))

(deftest test-run-2020
  (are [game result] (= result (-> game t/parse (t/run 2020)))
    example-1 436
    "1,3,2" 1
    "2,1,3" 10
    "1,2,3" 27
    "2,3,1" 78
    "3,2,1" 438
    "3,1,2" 1836))

; to lazy... just brute force it
#_(deftest test-run-3m
  (are [game result] (= result (-> game t/parse (t/run 30000000)))
    example-1 175594
    "1,3,2" 2578
    "2,1,3" 3544142
    "1,2,3" 261214
    "2,3,1" 6895259
    "3,2,1" 18
    "3,1,2" 362))

(deftest test-solution-15
  (are [f r] (= r (f))
    t/part-1 981
    t/part-2 164878))
