(ns aoc-clj-2020.util.input
  (:require [clojure.java.io :as io]))

(defn read-input
  [file-name]
  (-> file-name io/resource io/reader slurp))

(defn read-lines
  [file-name]
  (with-open [r (-> file-name io/resource io/reader)]
    (doall (line-seq r))))
