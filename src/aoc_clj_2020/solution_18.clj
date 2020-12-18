(ns aoc-clj-2020.solution-18
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [instaparse.core :as insta]))

(def parser
  (insta/parser
   "<formula> = term | paren-term
    paren-term = <'('> term <')'>
    <val> = num | paren-term
    <term> = val op val {op val}
    op = '+' | '*'
    num = #'\\d+'"
   :auto-whitespace :standard))

(defn parse
  [s]
  (->> s
       parser
       (insta/transform {:num lp/atoi
                         :op symbol
                         :paren-term (fn [& terms] terms)})))

(defn term->ast-ltr
  [term]
  (if (seq? term)
    (reduce (fn [a [op b]]
              [op [a (term->ast-ltr b)]])
            (term->ast-ltr (first term))
            (partition 2 (rest term)))
    term))

(defn term->ast-plus-pred
  [term]
  (if (seq? term)
    (reduce (fn [a [i [op b]]]
              (if (and (= op '+) (pos? i))
                (update-in a [1 1] (fn [a'] [op [a' (term->ast-plus-pred b)]]))
                [op [a (term->ast-plus-pred b)]]))
            (term->ast-plus-pred (first term))
            (map vector
                 (range)
                 (partition 2 (rest term))))
    term))

(defn solve
  [instr]
  (if (vector? instr)
    (let [[op [l r]] instr]
      (({'+ + '* *} op) (solve l) (solve r)))
    instr))

(defn calc
  [term->ast formula]
  (-> formula
      parse
      term->ast
      solve))

(defn part-1
  []
  (->> (li/read-lines "18.txt")
       (map (partial calc term->ast-ltr))
       (apply +)))

(defn part-2
  []
  (->> (li/read-lines "18.txt")
       (map (partial calc term->ast-plus-pred))
       (apply +)))
