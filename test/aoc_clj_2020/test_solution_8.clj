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

(deftest test-run-program-error
  (is (= {:error 5} (-> example
                        t/parse-program
                        t/setup-state
                        t/run-program
                        :result))))

(deftest test-run-program-success
  (is (= {:success 1} (-> [{:op :acc :ofs 1}]
                          t/setup-state
                          t/run-program
                          :result))))

(deftest test-fix-program-success
  (is (= {:success 8} (-> example
                          t/parse-program
                          t/setup-state
                          t/fix-program
                          :result))))

(deftest test-solution-8
  (are [f r] (= r (f))
    t/part-1 1744
    t/part-2 1174))
