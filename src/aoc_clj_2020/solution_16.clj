(ns aoc-clj-2020.solution-16
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn parse-ticket
  [s]
  (mapv lp/atoi
        (re-seq #"\d+" s)))

(defn parse-rule-ranges
  [s]
  (reduce (fn [acc [_ _ s e]]
            (into acc
                  (range (lp/atoi s)
                         (inc (lp/atoi e)))))
          #{}
          (re-seq #"((\d+)-(\d+))" s)))

(defn parse-rule
  [s]
  (let [[rule-name ranges] (str/split s #":" 2)]
    [(keyword rule-name) (parse-rule-ranges ranges)]))

(defn parse
  [s]
  (let [[rules-s your-ticket-s nearby-tickets-s] (str/split s #"\n\n")]
    {:rules (into {} (map parse-rule) (str/split-lines rules-s))
     :your-ticket (parse-ticket (second (str/split-lines your-ticket-s)))
     :nearby-tickets (mapv parse-ticket (rest (str/split-lines nearby-tickets-s)))}))

(defn combine-rules
  [{:keys [rules]}]
  (into #{} (apply concat (vals rules))))

(defn find-simple-violation
  [combined-rules ticket]
  (remove combined-rules ticket))

(defn sum-simple-violations
  [{:keys [nearby-tickets] :as config}]
  (let [combined-rules (combine-rules config)]
    (reduce
     #(apply + %1 (find-simple-violation combined-rules %2))
     0
     nearby-tickets)))

(defn part-1
  []
  (->> (li/read-input "16.txt")
       (parse)
       (sum-simple-violations)))

(defn part-2
  []
  nil)
