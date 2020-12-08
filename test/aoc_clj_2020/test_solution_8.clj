(ns aoc-clj-2020.test-solution-8
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-8 :as t]))

(def example
  ["nop +0"
   "acc +1"
   "jmp +4"
   "acc +3"
   "jmp -3"
   "acc -99"
   "acc +1"
   "jmp -4"
   "acc +6"])

(deftest test-parse-program
  (is (= (t/parse-program example)
         [{:op :nop :ofs 0}
          {:op :acc :ofs 1}
          {:op :jmp :ofs 4}
          {:op :acc :ofs 3}
          {:op :jmp :ofs -3}
          {:op :acc :ofs -99}
          {:op :acc :ofs 1}
          {:op :jmp :ofs -4}
          {:op :acc :ofs 6}])))

(deftest test-run-program
  (is (= 5 (-> example t/parse-program t/run-program))))

(deftest test-solution-8
  (are [f r] (= (f) r)
    t/part-1 1744
    t/part-2 nil))
