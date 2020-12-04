(ns aoc-clj-2020.solution-3
  (:require [aoc-clj-2020.util.input :as li]))

(defn count-trees-for-slope
  [{:keys [right down]} tree-map]
  (let [width (count (first tree-map))
        height (count tree-map)]
    (loop [y 0
           x 0
           r 0]
      (if (< y height)
        (recur (+ y down)
               (+ x right)
               (let [row (nth tree-map y)
                     cell (nth row (mod x width))]
                 (+ r ({\# 1} cell 0))))
        r))))

(defn count-trees
  [slopes tree-map]
  (apply * (map #(count-trees-for-slope % tree-map) slopes)))

(def slopes [{:right 1 :down 1}
             {:right 3 :down 1}
             {:right 5 :down 1}
             {:right 7 :down 1}
             {:right 1 :down 2}])

(defn part-1
  []
  (->> (li/read-lines "3.txt")
       (count-trees-for-slope {:right 3 :down 1})))

(defn part-2
  []
  (->> (li/read-lines "3.txt")
       (count-trees slopes)))
