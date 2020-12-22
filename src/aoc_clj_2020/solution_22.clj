(ns aoc-clj-2020.solution-22
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [clojure.string :as str])
  (:import [clojure.lang PersistentQueue]))

(defmethod print-method clojure.lang.PersistentQueue
  [q w]
  (print-method '< w)
  (print-method (seq q) w)
  (print-method '< w))

(defn queue
  [cards]
  (into (PersistentQueue/EMPTY) cards))

(defn parse
  [s]
  (->> s
       (lp/split-groups)
       (mapv (fn [line]
               (queue (map lp/atoi (rest (str/split-lines line))))))))

(defn compare-cards
  [cards]
  (if (apply < cards)
    1
    0))

(defn win-round
  [deck player cards]
  (update deck player into (if (zero? player) cards (reverse cards))))

(defn draw
  [deck]
  ((juxt
    (partial mapv pop)
    (partial mapv peek))
   deck))

(defn step
  [deck]
  (let [[deck cards] (draw deck)]
    (win-round deck (compare-cards cards) cards)))

(defn winner
  [[d1 d2]]
  (cond
    (empty? d1) 1
    (empty? d2) 0))

(defn score
  [deck player]
  (apply +
         (map *
              (reverse (get deck player))
              (iterate inc 1))))

(defn run
  [deck]
  (if-let [winner (winner deck)]
    (score deck winner)
    (recur (step deck))))

(defn init-recursive-game
  [deck]
  {:deck deck
   :seen #{}})

(defn replace-state
  [[_ & prev*] current]
  (conj prev* current))

(defn new-recurse
  [[current :as state] deck cards]
  (let [deck' (map #(queue (take %2 %1)) deck cards)]
    (conj
     (replace-state state (assoc current
                                 :deck deck
                                 :loot cards))
     (init-recursive-game deck'))))

(defn need-recurse?
  [[d1 d2] [c1 c2]]
  (and
   (<= c1 (count d1))
   (<= c2 (count d2))))

(defn winner-recurse
  [{:keys [deck seen]}]
  (if (contains? seen deck)
    0
    (winner deck)))

(defn win-recurse
  [[_ {:keys [deck loot] :as prev} :as state] winner]
  (replace-state (rest state)
                 (assoc prev :deck (win-round deck winner loot))))

; FIXME: pretty ugly; might be better to have a proper step fn and make the win condtion just a marker in state
(defn recursive-run
  [deck]
  (loop [state (list (init-recursive-game deck))]
    (let [{:keys [deck] :as current} (first state)]
      (if-let [winner (winner-recurse current)]
        (if (= 1 (count state))
          (score deck winner)
          (recur (win-recurse state winner)))
        (let [prev-deck deck
              [deck cards] (draw deck)]
          (recur
           (if (need-recurse? deck cards)
             (new-recurse state deck cards)
             (replace-state state
                            (-> current
                                (update :seen conj prev-deck)
                                (assoc :deck (win-round deck (compare-cards cards) cards)))))))))))

(defn part-1
  []
  (->> (li/read-input "22.txt")
       (parse)
       (run)))

(defn part-2
  []
  (->> (li/read-input "22.txt")
       (parse)
       (recursive-run)))
