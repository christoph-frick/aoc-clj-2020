(ns aoc-clj-2020.solution-23
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [clojure.string :as str]))

; should be done with a linked ring

(defn parse
  ([s]
   (parse s nil))
  ([s pad-to]
   (let [game (map lp/atoi (re-seq #"\d" s))
         [mn mx] ((juxt first last) (apply sorted-set game))
         game (if pad-to
                (into game (range (inc mx) (inc pad-to)))
                game)
         mx (if pad-to
              (+ mx pad-to)
              mx)]
     {:game game
      :min mn
      :max mx})))

(defn shift-to
  [xs fst]
  (let [[p1 p2] (split-with (partial not= fst) xs)]
    (into (vec p2) p1)))

(defn shift-list-1
  [xs]
  (conj (vec (rest xs))
        (first xs)))

(defn next-start
  [start forbidden mn mx]
  (loop [target (dec start)]
    (println start forbidden mn mx target)
    (if (and (>= target mn)
             (not (contains? forbidden target)))
      target
      (recur  (if (< target mn) mx (dec target))))))

(defn step
  [{:keys [game min max] :as state}]
  (let [[start h1 h2 h3 & remaining] game
        next-start (next-start start #{h1 h2 h3} min max)
        result (concat [next-start]
                       [h1 h2 h3]
                       (rest (shift-to (conj remaining start) next-start)))]
    (assoc state
           :game (-> result
                     (shift-to start)
                     (shift-list-1)))))

(defn run
  [game nr]
  (if (zero? nr)
    game
    (recur (step game) (dec nr))))

(defn solution-1
  [{game :game}]
  (->> (shift-to game 1)
       (rest)
       (str/join "")))

(defn solution-2
  [game]
  (let [[_ a b] (drop-while (partial not= 1) game)]
    (* a b)))

(defn part-1
  []
  (-> "942387615"
      (parse)
      (run 100)
      (solution-1)))

(defn part-2
  []
  nil)
