(ns aoc-clj-2020.solution-8
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn parse-program
  [lines]
  (lp/lines-to
   (lp/line-parser
    #"(\w+) ([+-]\d+)"
    :op keyword
    :ofs lp/atoi)
   lines))

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

(defn setup-state
  [ops]
  {:seen-lines #{}
   :current-line 0
   :prev ()
   :acc 0
   :ops ops})

(defn step-program
  [{:keys [seen-lines current-line acc ops] :as state}]
  (if (>= current-line (count ops))
    (assoc state :result {:success acc})
    (if (contains? seen-lines current-line)
      (assoc state :result {:error acc})
      (let [{:keys [op] :as instr} (nth ops current-line)]
        (-> state
            (update :prev conj state)
            (assoc :instr instr)
            ((dispatch op) instr)
            (update :seen-lines conj current-line))))))

(defn run-program
  [state]
  (loop [state state]
    (let [state' (step-program state)]
      (if (:result state')
        state'
        (recur state')))))

(defn problem-candidate?
  [{:keys [current-line ops]}]
  (#{:nop :jmp} (:op (nth ops current-line))))

(defn fix-problem-candidate
  [state]
  (let [{:keys [current-line]} state]
    (update-in state [:ops current-line :op] {:jmp :nop
                                              :nop :jmp})))

(defn success?
  [state]
  (boolean (some-> state :result :success)))

(defn fix-program
  [state]
  (let [{:keys [prev]} (run-program state)]
    (reduce
     (fn [_ state]
       (let [state (-> state fix-problem-candidate run-program)]
         (if (success? state)
           (reduced state)
           nil)))
     nil
     (filter problem-candidate? prev))))

(defn part-1
  []
  (->> (li/read-input "8.txt")
       (parse-program)
       (setup-state)
       (run-program)
       (:result)
       (:error)))

(defn part-2
  []
  (->> (li/read-input "8.txt")
       (parse-program)
       (setup-state)
       (fix-program)
       (:result)
       (:success)))
