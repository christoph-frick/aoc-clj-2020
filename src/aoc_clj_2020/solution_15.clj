(ns aoc-clj-2020.solution-15
  (:require [aoc-clj-2020.util.parse :as lp]))

(defn parse
  [s]
  (map lp/atoi (re-seq #"\d+" s)))

(defn setup
  [game]
  {:turn (count game)
   :recent (into {} (map vector (butlast game) (iterate inc 1)))
   :spoken (last game)})

(defn step
  [{:keys [recent turn spoken] :as game}]
  (let [spoken' (if (contains? recent spoken)
                  (- turn (get recent spoken))
                  0)]
    (-> game
        (assoc-in [:recent spoken] turn)
        (assoc :spoken spoken'
               :turn (inc turn)))))

(defn run
  [game upto-turn]
  (let [{:keys [spoken]} (loop [game (setup game)]
                           (let [game' (step game)]
                             (if (= upto-turn (:turn game'))
                               game'
                               (recur game'))))]
    spoken))

(defn part-1
  []
  (-> "8,0,17,4,1,12" parse (run 2020)))

(defn part-2
  []
  (-> "8,0,17,4,1,12" parse (run 30000000)))
