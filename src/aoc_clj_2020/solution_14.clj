(ns aoc-clj-2020.solution-14
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.format :as lf]
            [clojure.string :as str]
            [clojure.pprint :as pp]))

(defn parse
  [s]
  (lp/lines-to
   (fn [s]
     (if-let [[_ mask] (re-find #"^mask = ([X01]+)" s)]
       [:mask mask]
       (let [[_ addr value] (re-find #"^mem\[(\d+)\] = (\d+)" s)]
         [:mem (lp/atoi addr) (lp/atoi value)])))
   s))


(def initial-state-1
  {:mask-and (bit-not 0)
   :mask-or 0
   :memory {}})

(defn mask-to-bin
  [s]
  {:mask-or (lp/btol (str/escape s {\X \0}))
   :mask-and (lp/btol (str/escape s {\X \1}))})

(defn apply-mask-1
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
           (update state :memory assoc addr (apply-mask-1 state value)))))

(defn sum-memory
  [{:keys [memory]}]
  (apply + (vals memory)))

(defn apply-mask-2
  [{:keys [mask]} value]
  (let [bin-value (pp/cl-format nil "~36,'0d" (lf/ltob value))]
    (str/join
     (map (fn [v m]
            (if (= m \0)
              v
              m))
          bin-value
          mask))))

(defn mask-to-values
  [mask]
  (->> mask
       (map {\0 [0]
             \1 [1]
             \X [0 1]})
       (reduce (fn [acc m]
                 (for [a acc
                       b m]
                   (bit-or (bit-shift-left a 1) b)))
               [0])))

(def initial-state-2
  {:memory {}})

(defn step-2
  [state [instr & params]]
  (case instr
    :mask (let [[mask] params]
            (assoc state :mask mask))
    :mem (let [[addr value] params
               all-addr (-> (apply-mask-2 state addr) (mask-to-values))]
           (update state :memory #(reduce (fn [m o] (assoc m o value)) % all-addr)))))

(defn part-1
  []
  (->> (li/read-input "14.txt")
       (parse)
       (reduce step-1 initial-state-1)
       (sum-memory)))

(defn part-2
  []
  (->> (li/read-input "14.txt")
       (parse)
       (reduce step-2 initial-state-2)
       (sum-memory)))
