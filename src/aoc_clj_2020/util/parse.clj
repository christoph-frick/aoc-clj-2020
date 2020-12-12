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

(defn line-parser
  [re & mappings]
  (fn [s]
    (into {} (map (fn [[k f] v]
                    [k (f v)])
                  (partition 2 mappings)
                  (rest (re-find re s))))))
