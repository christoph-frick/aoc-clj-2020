(ns aoc-clj-2020.solution-7
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn parse-line
  [s]
  (let [[_ bag] (re-find #"^(\w+ \w+) bags? contain" s)
        contains (re-seq #"(\d+) (\w+ \w+) bags?" s)]
    {:bag bag
     :contain (mapv (fn [[_ n b]]
                      {:bag b
                       :amount (lp/atoi n)})
                    contains)}))

(defn parse-lines
  [s]
  (->> (str/split s #"\n")
       (mapv parse-line)))

(defn to-contained
  [lines]
  (let [assoc-set (fn [m k v]
                    (if (contains? m k)
                      (update m k conj v)
                      (assoc m k #{v})))]
    (reduce (fn [m {:keys [bag contain]}]
              (reduce (fn [m {c-bag :bag}]
                        (assoc-set m c-bag bag))
                      m
                      contain))
            {}
            lines)))

(defn find-containing
  [contained bag]
  (loop [result #{}
         todo (contained bag #{})]
    (if (seq todo)
      (let [x (first todo)]
        (recur (conj result x) (into (disj todo x) (contained x #{}))))
      result)))

(defn count-bag-content
  [lines bag]
  (let [lut (into {} (map (juxt :bag :contain)) lines)]
    (letfn [(total [k]
              (apply + (mapcat
                        (fn [{:keys [bag amount]}]
                          [amount (* (total bag) amount)])
                        (lut k []))))]
      (total bag))))

(defn part-1
  []
  (-> (li/read-input "7.txt")
      (parse-lines)
      (to-contained)
      (find-containing "shiny gold")
      (count)))

(defn part-2
  []
  (-> (li/read-input "7.txt")
      (parse-lines)
      (count-bag-content "shiny gold")))
