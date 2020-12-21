(ns ^:day-21 aoc-clj-2020.test-solution-21
  (:require [clojure.test :refer [deftest is are]]
            [aoc-clj-2020.solution-21 :as t]))

(def data-1
  "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
trh fvjkl sbzzf mxmxvkd (contains dairy)
sqjhc fvjkl (contains soy)
sqjhc mxmxvkd sbzzf (contains fish)")

(deftest test-parse
  (is (= [{:ingredients #{"mxmxvkd" "kfcds" "sqjhc" "nhms"} :allergens #{"dairy" "fish"}}
          {:ingredients #{"trh" "fvjkl" "sbzzf" "mxmxvkd"} :allergens #{"dairy"}}
          {:ingredients #{"sqjhc" "fvjkl"} :allergens #{"soy"}}
          {:ingredients #{"sqjhc" "mxmxvkd" "sbzzf"} :allergens #{"fish"}}]
         (t/parse data-1))))

(deftest test-solve-allergens
  (is (= {"dairy" "mxmxvkd"
          "fish" "sqjhc"
          "soy" "fvjkl"}
         (->> data-1
              (t/parse)
              (t/solve-allergens)))))

(deftest test-allergen-free
  (is (= #{"kfcds" "nhms" "sbzzf" "trh"}
         (->> data-1
              (t/parse)
              (t/allergen-free)))))

(deftest test-ingredient-frequencies
  (is (= 2
         (-> data-1
             (t/parse)
             (t/ingredient-frequencies)
             (get "sbzzf")))))

(deftest test-count-allergen-free
  (is (= 5
         (->> data-1
              (t/parse)
              (t/count-allergen-free)))))

(deftest test-solution-21
  (are [f r] (= r (f))
    t/part-1 1685
    t/part-2 nil))
