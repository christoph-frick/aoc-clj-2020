(ns aoc-clj-2020.solution-16
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.transform :as transform]
            [clojure.string :as str]
            [clojure.set :as set]))

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
    [rule-name (parse-rule-ranges ranges)]))

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

(defn filter-and-append-valid-tickets
  [{:keys [your-ticket nearby-tickets] :as config}]
  (let [combined-rules (combine-rules config)]
    (assoc config :valid-tickets
           (into []
                 (remove
                  #(seq (find-simple-violation combined-rules %)))
                 (conj nearby-tickets your-ticket)))))

(defn find-rules-for-column
  [{:keys [rules]} col]
  (into #{}
        (comp
         (filter (fn [[_ allowed]]
                   (every? allowed col)))
         (map first))
        rules))

(defn count=1?
  [coll]
  (= 1 (count coll)))

(defn remove-if-seen
  [seen it]
  (let [all-seen (apply set/union seen)]
    (if (count=1? it)
      it
      (set/difference it all-seen))))

(defn solve-cols
  [col-options]
  (let [{done true todo false} (group-by count=1? col-options)]
    (if (empty? todo)
      (mapv first col-options)
      (recur
       (mapv (partial remove-if-seen done)
             col-options)))))

(defn rules-for-columns
  [{:keys [valid-tickets] :as config}]
  (->> valid-tickets
       (transform/transpose)
       (mapv (partial find-rules-for-column config))
       (solve-cols)))

(defn solve-your-ticket
  [config-s]
  (let [valid-config (->> config-s
                          (parse)
                          (filter-and-append-valid-tickets))
        cols (rules-for-columns valid-config)]
    (into {} (map vector cols (:your-ticket valid-config)))))

(defn part-1
  []
  (->> (li/read-input "16.txt")
       (parse)
       (sum-simple-violations)))

(defn part-2
  []
  (apply * (->> (li/read-input "16.txt")
                (solve-your-ticket)
                (filter (fn [[k _]]
                          (str/starts-with? k "departure")))
                (map val))))
