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

(defn read-input
  [input]
  (let [[rules-s messages-s] (lp/split-groups input)
        messages (str/split-lines messages-s)]
    {:rules rules-s
     :messages messages}))

(defn run
  [input]
  (let [{:keys [rules messages]} (read-input input)
        rules (parse rules)
        re (rules->re rules)]
    (count
     (filter (partial re-matches re) messages))))

(defn parser-8-42
  [rules-s]
  (-> (str "start: 0\n" rules-s)
      (str/replace "8: 42" "8: 42 | 42 8")
      (str/replace "11: 42 31" "11: 42 31 | 42 11 31")
      (str/replace #"(\d+)" "r$1")
      (str/replace ":" " =")
      (insta/parser)))

(defn run-8-42
  [input]
  (let [{:keys [rules messages]} (read-input input)
        parser (parser-8-42 rules)]
    (count
     (remove insta/failure?
             (map parser
                  messages)))))

(defn part-1
  []
  (-> (li/read-input "19.txt")
      (run)))

(defn part-2
  []
  (-> (li/read-input "19.txt")
      (run-8-42)))
