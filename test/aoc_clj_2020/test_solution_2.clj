(ns aoc-clj-2020.test-solution-2
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-2 :as t]))

(deftest test-parse-line
  (are [t r] (= (t/parse-line t) r)
    "1-3 a: abcde" {:min 1 :max 3 :char \a :password "abcde"}
    "1-3 b: cdefg" {:min 1 :max 3 :char \b :password "cdefg"}
    "2-9 c: ccccccccc" {:min 2 :max 9 :char \c :password "ccccccccc"}))

(deftest test-in-range?
  (are [line n r] (= (t/in-range? line n) r)
    {:min 1 :max 3} 0 false
    {:min 1 :max 3} 1 true
    {:min 1 :max 3} 3 true
    {:min 1 :max 3} 4 false))

(deftest test-valid-frequency?
  (are [t r] (= (t/valid-frequency? (t/parse-line t)) r)
    "1-3 a: abcde" true
    "1-3 b: cdefg" false
    "2-9 c: ccccccccc" true))

(deftest test-char-at?
  (are [text char pos r] (= (t/char-at? text char pos) r)
    "abcde" \a 1 true
    "abcde" \a 3 false
    "cdefg" \b 1 false
    "cdefg" \b 3 false
    "ccccccccc" \c 2 true
    "ccccccccc" \c 9 true))

(deftest test-valid-location?
  (are [t r] (= (t/valid-location? (t/parse-line t)) r)
    "1-3 a: abcde" true
    "1-3 b: cdefg" false
    "2-9 c: ccccccccc" false))

(deftest test-solution
  (are [f r] (= r (f))
    t/part-1 586  
    t/part-2 352))
