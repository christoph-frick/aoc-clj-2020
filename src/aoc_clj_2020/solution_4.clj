(ns aoc-clj-2020.solution-4
  (:require [aoc-clj-2020.util.input :as li]
            [clojure.string :as str]))

(defn split-passports
  [s] 
  (str/split s #"(?m)^$"))

(defn split-passport
  [s]
  (re-seq #"(?m)(.{3}):(\S+)" s))

(defn parse-passport
  [s]
  (println s)
  (into {}
        (map (fn [[_ k v]]
               [(keyword k) v]))
        (split-passport s)))

(defn parse-passports
  [s]
  (mapv parse-passport (split-passports s)))

(def valid-passport-keys
  #{:ecl :pid :eyr :hcl :byr :iyr :hgt})

(defn valid-passport?
  [passport]
  (every? #(contains? passport %) valid-passport-keys))

(defn count-valid-passports
  [s]
  (count (filter valid-passport? (parse-passports s))))

(defn part-1
  []
  (-> (li/read-input "4.txt")
      (count-valid-passports)))

(defn part-2
  []
  -1)
