(ns aoc-clj-2020.solution-21
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [instaparse.core :as insta]
            [clojure.string :as str]
            [clojure.set :as set]))

(def line-parser
  (insta/parser
   "
   rule = ingredients allergens
   ingredients = wordlist
   allergens = <'(contains '> wordlist <')'>
   wordlist = (word <space>*)+
   space = #'[\\s,]+'
   <word> = #'\\w+'
   "))

(defn parse-line
  [line]
  (->> (line-parser line)
       (insta/transform
        {:wordlist (fn [& words] (into #{} words))
         :rule (fn [& kvs] (into {} kvs))})))

(defn parse
  [input]
  (lp/lines-to
   parse-line
   input))

(defn solve-allergens
  [xs]
  (let [unions (reduce
                (partial merge-with set/intersection)
                {}
                (for [{:keys [ingredients allergens]} xs
                      allergen allergens]
                  {allergen ingredients}))
        count=1? (fn [v] (= 1 (count v)))
        remove-val (fn [m rm]
                     (reduce
                      (fn [m k]
                        (if (count=1? (m k))
                          m
                          (update m k disj rm)))
                      m
                      (keys m)))]
    (loop [result unions]
      (if (every? count=1? (vals result))
        (into {} (map (juxt key (comp first val))) result)
        (let [{done true} (group-by count=1? (vals result))]
          (recur (reduce remove-val result (map first done))))))))

(defn allergen-free
  [xs]
  (let [allergens (->> xs (solve-allergens) (vals) (into #{}))]
    (into #{}
          (comp
           (mapcat :ingredients)
           (remove allergens))
          xs)))

(defn ingredient-frequencies
  [xs]
  (frequencies (mapcat :ingredients xs)))

(defn count-allergen-free
  [xs]
  (apply +
         (vals
          (select-keys (ingredient-frequencies xs)
                       (allergen-free xs)))))

(defn canonical-dangerous-ingredient-list
  [xs]
  (let [allergens (solve-allergens xs)]
    (str/join "," (map val (sort-by key allergens)))))

(defn part-1
  []
  (->> (li/read-input "21.txt")
       (parse)
       (count-allergen-free)))

(defn part-2
  []
  (->> (li/read-input "21.txt")
       (parse)
       (canonical-dangerous-ingredient-list)))
