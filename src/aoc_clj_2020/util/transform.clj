(ns aoc-clj-2020.util.transform)

(defn transpose
  [vs]
  (apply mapv vector vs))
