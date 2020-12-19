(ns aoc-clj-2020.solution-19
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [instaparse.core :as insta]
            [clojure.string :as str]))

(def parser
  (insta/parser
   "
   rules = rule+
   rule = index <':'> <space> (char|term) <newline>?
   term = and | or
   and = (ref <space>?)+
   or = and <'|'> <space> and
   newline = '\\n'
   space = ' '
   char = <'\"'> #'[a-z]' <'\"'>
   ref = num
   index = num
   num = #'\\d+'
   "))

(defn parse
  [s]
  (->> s
       (parser)
       (insta/transform {:num lp/atoi
                         :or #(into ['|] %&)
                         :and #(into ['&] (map second) %&)
                         :rule (fn [[_ index] [_ term]]
                                 [index term])
                         :rules #(into {} %&)})))

(defn rules->re
  [rules]
  (letfn [(apply-rule [rule]
            (if (vector? rule)
              (let [[op & args] rule]
                (case op
                  | (str "(" (apply-rule (first args)) "|" (apply-rule (last args)) ")")
                  & (str "(" (str/join "" (map re args)) ")")))
              rule))
          (re [i]
            (apply-rule (get rules i)))]
    (re-pattern (re 0))))

(defn run
  [input]
  (let [[rules-s messages-s] (lp/split-groups input)
        rules (parse rules-s)
        re (rules->re rules)
        messages (str/split-lines messages-s)]
    (count
     (filter (partial re-matches re) messages))))

(defn part-1
  []
  (-> (li/read-input "19.txt")
      (run)))

(defn part-2
  []
  nil)
