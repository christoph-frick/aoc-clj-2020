(ns aoc-clj-2020.solution-14
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn parse
  [s]
  (lp/lines-to
   (fn [s]
     (if-let [[_ mask] (re-find #"^mask = ([X01]+)" s)]
       [:mask mask]
       (let [[_ addr value] (re-find #"^mem\[(\d+)\] = (\d+)" s)]
         [:mem (lp/atoi addr) (lp/atoi value)])))
   s))


(def initial-state
  {:mask-and (bit-not 0)
   :mask-or 0
   :memory {}})

(defn mask-to-bin
  [s]
  {:mask-or (Long/parseLong (str/escape s {\X \0}) 2)
   :mask-and (Long/parseLong (str/escape s {\X \1}) 2)})

(defn apply-mask
  [{:keys [mask-or mask-and]} value]
  (->> value
       (bit-or mask-or)
       (bit-and mask-and)))

(defn step-1
  [state [instr & params]]
  (case instr
    :mask (let [[mask] params]
            (merge state (mask-to-bin mask)))
    :mem (let [[addr value] params]
           (update state :memory assoc addr (apply-mask state value)))))

(defn run
  [initial-state instrs]
  (reduce step-1 initial-state instrs))

(defn sum-memory
  [{:keys [memory]}]
  (apply + (vals memory)))

(defn part-1
  []
  (->> (li/read-input "14.txt")
       (parse)
       (run initial-state)
       (sum-memory)))

(defn part-2
  []
  nil)
