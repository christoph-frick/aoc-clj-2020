(ns aoc-clj-2020.util.parse
  (:require [clojure.string :as str]))

(defn atoi
  [s]
  (Integer/parseInt s))

(defn atol
  [s]
  (Long/parseLong s))

(defn lines-to
  [transform s]
  (mapv transform (str/split-lines s)))

(defn split-groups
  "Split a string on double line breaks"
  [s]
  (str/split s #"\n\n"))
