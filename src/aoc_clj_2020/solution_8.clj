(ns aoc-clj-2020.solution-8
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn parse-op
  [s]
  (let [[_ op ofs] (re-find #"(\w+) ([+-]\d+)" s)]
    {:op (keyword op) :ofs (lp/atoi ofs)}))

(defn parse-program
  [lines]
  (mapv parse-op lines))

(defn op-nop
  [state _]
  (update state :current-line inc))

(defn op-acc
  [state {:keys [ofs]}]
  (update (op-nop state {}) :acc + ofs))

(defn op-jmp
  [state {:keys [ofs]}]
  (update state :current-line + ofs))

(def dispatch
  {:nop op-nop
   :acc op-acc
   :jmp op-jmp})

(defn run-program
  [ops]
  (loop [{:keys [seen-lines current-line acc] :as state} {:seen-lines #{}
                                                          :current-line 0
                                                          :acc 0}]
    (if (contains? seen-lines current-line)
      acc
      (let [{:keys [op] :as instr} (nth ops current-line)]
        (recur (update ((dispatch op) state instr) :seen-lines conj current-line))))))

(defn part-1
  []
  (->>
   (li/read-lines "8.txt")
   (parse-program)
   (run-program)))

(defn part-2
  []
  nil)
