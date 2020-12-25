(ns aoc-clj-2020.solution-25
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]))

(defn step
  [subject value]
  (rem (* value subject) 20201227))

(defn enc-key
  [subject loop-size]
  (loop [value 1
         loop-size loop-size]
    (if (zero? loop-size)
      value
      (recur (step subject value) (dec loop-size)))))

(defn loop-size
  [subject pub-keys]
  (loop [loop-size 0
         values (repeat (count pub-keys) 1)]
    (if-let [pub-key (first
                      (filter
                       (partial apply =)
                       (map vector
                            values
                            pub-keys)))]
      [(first pub-key) loop-size]
      (recur (inc loop-size)
             (map (partial step subject)
                  values)))))

(def input
  [9093927 11001876])

(defn part-1
  []
  (let [[pub-key loop-size] (loop-size 7 input)
        other-pub-key (first (remove (partial = pub-key) input))]
    (enc-key other-pub-key loop-size)))

(defn part-2
  []
  nil)
