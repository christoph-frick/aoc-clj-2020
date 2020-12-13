(ns aoc-clj-2020.solution-13
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [clojure.string :as str]))

(defn parse
  [s]
  (let [[target lines] (str/split-lines s)]
    {:target (lp/atoi target)
     :lines (mapv
             (fn [s]
               (if (= s "x")
                 :x
                 (lp/atoi s)))
             (re-seq #"[^,]+" lines))}))

(defn distance
  [target line]
  (let [r (mod target line)]
    (if (zero? r)
      r
      (- line r))))

(defn minimize-wait
  [{:keys [target lines]}]
  (let [tt (into (sorted-map)
                 (map (juxt (partial distance target) identity) (remove (partial = :x) lines)))
        [line wait] (first tt)]
    (* line wait)))

(defn brute-force
  [{:keys [lines]}]
  (let [lines-with-offset (->> (map vector lines (range))
                               (remove #(= :x (first %))))
        [step offs] (apply max-key first lines-with-offset)]
    (first
     (filter
      (fn [target]
        (every? (fn [[line offs]]
                  (= offs (distance target line)))
                lines-with-offset))
      (iterate (partial + step) (- step offs))))))

(defn bezout-identity
  [a b]
  (loop [[old-r r] [a b]
         [old-s s] [1 0]
         [old-t t] [0 1]]
    (if-not (zero? r)
      (let [q (quot old-r r)]
        (recur [r, (- old-r (* q r))]
               [s, (- old-s (* q s))]
               [t, (- old-t (* q t))]))
      [old-s old-t])))

(defn crt
  [{:keys [lines]}]
  (let [mods-and-rems (->> (map vector (range) lines)
                           (remove #(= :x (second %)))
                           (map (fn [[a m]] [(if (zero? a) a (- m a)) m]))
                           (sort-by (comp - first)))]
    (apply mod
           (reduce
            (fn [[a1 n1] [a2 n2]]
              (let [[m1 m2] (bezout-identity n1 n2)]
                [(+' (*' a1 m2 n2) (*' a2 m1 n1)) (*' n1 n2)]))
            mods-and-rems))))

(defn part-1
  []
  (->> (li/read-input "13.txt")
       (parse)
       (minimize-wait)))

(defn part-2
  []
  (->> (li/read-input "13.txt")
       (parse)
       (brute-force)))
