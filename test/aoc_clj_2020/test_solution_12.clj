(ns ^:day-12 aoc-clj-2020.test-solution-12
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-12 :as t]))

(def example
  "F10
N3
F7
R90
F11")

(deftest test-parse
  (is (= [{:op :F :amt 10}
          {:op :N :amt 3}
          {:op :F :amt 7}
          {:op :R :amt 90}
          {:op :F :amt 11}]
         (t/parse example))))

(deftest test-run-1
  (is (= 25 (->> example (t/parse) (t/run t/rules-1 t/initial-state-1) (t/distance)))))

(deftest test-step-1
  (is (= [t/initial-state-1
          {:dir :E :pos [10 0]} ; F10
          {:dir :E :pos [10 3]} ; N3
          {:dir :E :pos [17 3]} ; F7
          {:dir :S :pos [17 3]} ; R90
          {:dir :S :pos [17 -8]} ; F11
          ]
         (reductions (partial t/step-1 t/rules-1) t/initial-state-1 (t/parse example)))))

(deftest test-run-2
  (is (= 286 (->> example (t/parse) (t/run t/rules-2 t/initial-state-2) (t/distance)))))

(deftest test-step-2
  (is (= [t/initial-state-2
          {:dir :E :pos [100 10] :wp [10 1]} ; F10
          {:dir :E :pos [100 10] :wp [10 4]} ; N3
          {:dir :E :pos [170 38] :wp [10 4]} ; F7
          {:dir :E :pos [170 38] :wp [4 -10]} ; R90
          {:dir :E :pos [214 -72] :wp [4 -10]} ; F11
          ]
         (reductions (partial t/step-1 t/rules-2) t/initial-state-2 (t/parse example)))))

(deftest test-rel-rot-in
  (are [result dir op amt] (= result (t/rel-rot-in t/directions dir op amt))
    :E :N :R 90
    :W :N :L 90
    :W :E :L 180
    :W :E :R 180))

(deftest test-pos-rotate
  (are [result pos op amt] (= result (t/pos-rotate pos op amt))
    [4 -10] [10 4] :R 90
    [-4 10] [10 4] :L 90
    [-10 -4] [10 4] :L 180
    [-10 -4] [10 4] :R 180))

(deftest test-solution-12
  (are [f r] (= r (f))
    t/part-1 2458
    t/part-2 145117))
