(ns aoc-clj-2020.solution-15
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]))

(defn parse
  [s]
  (map lp/atoi (re-seq #"\d+" s)))

(defn setup
  [game]
  {:turn (count game)
   :recent (into {}
                 (map #(vector %1 (list %2))
                      game
                      (iterate inc 1)))
   :spoken (last game)})

(defn inc-turn
  [game]
  (update game :turn inc))

(defn recent
  [{:keys [recent spoken]}]
  (when (and (contains? recent spoken)
             (< 1 (count (recent spoken))))
    (apply - (get recent spoken))))

(defn update-recent
  [{:keys [turn spoken] :as game}]
  (update-in game
             [:recent spoken]
             #(take 2 (conj % turn))))

(defn spoken
  [game nr]
  (assoc game :spoken nr))

(defn announce
  [game]
  (-> game
      (spoken (or (recent game) 0))
      update-recent))

(defn step
  [game]
  (-> game
      (inc-turn)
      (announce)))

(defn run
  [game turn]
  (let [{:keys [spoken]} (first
                          (filter
                           #(= turn (:turn %))
                           (iterate step (setup game))))]
    spoken))

(defn part-1
  []
  (-> "8,0,17,4,1,12" parse (run 2020)))

(defn part-2
  []
  (-> "8,0,17,4,1,12" parse (run 30000000)))
