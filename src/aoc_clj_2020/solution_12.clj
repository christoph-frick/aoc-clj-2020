(ns aoc-clj-2020.solution-12
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]))

(defn parse
  [s]
  (lp/lines-to
   (lp/line-parser
    #"(.)(\d+)"
    :op keyword
    :amt lp/atoi)
   s))

(def directions
  [:N
   :E
   :S
   :W])

(def direction-indices
  (into {} (map vector directions (range))))

(def move-offsets
  {:N [0 1]
   :E [1 0]
   :S [0 -1]
   :W [-1 0]})

(def rotation-matrices
  [[[1 0] [0 1]],
   [[0 1] [-1 0]],
   [[-1 0] [0 -1]],
   [[0 -1] [1 0]]])

(def rotation-offsets
  (into {} (for [i (range 1 4)
                 :let [angle (* i 90)]
                 k [:L :R]]
             [[k angle] ((if (= :L k) - +) i)])))

(defn rel-rot-in
  [lut dir op amt]
  (let [dir-ofs (direction-indices dir)
        rot-ofs (rotation-offsets [op amt])]
    (nth lut (mod (+ dir-ofs rot-ofs) 4))))

(defn pos-translate
  [[ax ay] [bx by]]
  [(+ ax bx) (+ ay by)])

(defn pos-scale
  [[ax ay] factor]
  [(* ax factor) (* ay factor)])

(defn pos-rotate
  [[x y] op amt]
  (let [[[a b] [c d]] (rel-rot-in rotation-matrices :N op amt)]
    [(+ (* x a) (* y b))
     (+ (* x c) (* y d))]))

(defn move
  [pos dir amt]
  (pos-translate pos (pos-scale dir amt)))

(defn step-1
  [rules state {:keys [op] :as instr}]
  ((rules op) state instr))

(defn run
  [rules initial-state instrs]
  (reduce (partial step-1 rules) initial-state instrs))

(defn distance
  [{:keys [pos]}]
  (apply + (map #(Math/abs %) pos)))

(defn build-rules
  [dir-rule forward-rule rotate-rule]
  (into {}
        (for [[keys fun] {#{:N :S :E :W} dir-rule
                          #{:F} forward-rule
                          #{:R :L} rotate-rule}
              k keys]
          [k fun])))

; part 1

(defn move-ship-by
  [{:keys [pos] :as state} {:keys [amt]} dir]
  (assoc state
         :pos (move pos dir amt)))

(defn rotate-ship
  [{:keys [dir] :as state} {:keys [op amt]}]
  (assoc state
         :dir (rel-rot-in directions dir op amt)))

(defn move-ship
  [state {:keys [op] :as instr}]
  (move-ship-by state instr (move-offsets op)))

(defn move-ship-by-dir
  [{:keys [dir] :as state} instr]
  (move-ship-by state instr (move-offsets dir)))

(def initial-state-1
  {:pos [0 0]
   :dir :E})

(def rules-1
  (build-rules move-ship
               move-ship-by-dir
               rotate-ship))

; part 2

(defn move-ship-by-wp
  [{:keys [wp] :as state} instr]
  (move-ship-by state instr wp))

(defn move-wp
  [{:keys [wp] :as state} {:keys [op amt]}]
  (assoc state
         :wp (move wp (move-offsets op) amt)))

(defn rotate-wp
  [{:keys [wp] :as state} {:keys [op amt]}]
  (assoc state
         :wp (pos-rotate wp op amt)))

(def initial-state-2
  {:pos [0 0]
   :wp [10 1]
   :dir :E})

(def rules-2
  (build-rules move-wp
               move-ship-by-wp
               rotate-wp))

(defn part
  [input rules initial-state]
  (->> (li/read-input input)
       (parse)
       (run rules initial-state)
       (distance)))

(defn part-1
  []
  (part "12.txt"
        rules-1
        initial-state-1))

(defn part-2
  []
  (part "12.txt"
        rules-2
        initial-state-2))
