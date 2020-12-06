(ns aoc-clj-2020.solution-4
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn split-passport
  [s]
  (re-seq #"(?m)(.{3}):(\S+)" s))

(defn parse-passport
  [s]
  (into {}
        (map (fn [[_ k v]]
               [(keyword k) v]))
        (split-passport s)))

(defn parse-passports
  [s]
  (mapv parse-passport (lp/split-groups s)))

(def valid-passport-keys
  #{:ecl :pid :eyr :hcl :byr :iyr :hgt})

(defn valid-passport?
  [passport]
  (every? #(contains? passport %) valid-passport-keys))

(defn count-valid
  [pred passports]
  (lt/count-valid pred (parse-passports passports)))

(defn part-1
  []
  (->> (li/read-input "4.txt")
       (count-valid valid-passport?)))

(defn dispatch-check
  [config k v]
  ((config k (constantly false)) v))

(defn number
  [s]
  (when (re-matches #"\d+" s)
    (lp/atoi s)))

(defn valid-number?
  [pred s]
  (if-let [n (number s)]
    (pred n)
    false))

; byr (Birth Year) - four digits; at least 1920 and at most 2002.
(defn birth-year?
  [s]
  (valid-number? #(<= 1920 % 2002) s))

; iyr (Issue Year) - four digits; at least 2010 and at most 2020.
(defn issue-year?
  [s]
  (valid-number? #(<= 2010 % 2020) s))

; eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
(defn exp-year?
  [s]
  (valid-number? #(<= 2020 % 2030) s))

; hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
(defn hex-color?
  [s]
  (re-matches #"#[a-f0-9]{6}" s))

; ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
(defn eye-color?
  [s]
  (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} s))

; pid (Passport ID) - a nine-digit number, including leading zeroes.
(defn passport-id?
  [s]
  (re-matches #"\d{9}" s))

; If cm, the number must be at least 150 and at most 193.
(defn height-in-cm?
  [s]
  (valid-number? #(<= 150 % 193) s))

; If in, the number must be at least 59 and at most 76.
(defn height-in-in?
  [s]
  (valid-number? #(<= 59 % 76) s))

; hgt (Height) - a number followed by either cm or in:
(defn height?
  [s]
  (if-let [[_ n u] (re-find #"(\d+)(cm|in)" s)]
    (dispatch-check
     {"cm" height-in-cm?
      "in" height-in-in?}
     u n)
    false))

(def strict-checks
  {:byr birth-year?
   :iyr issue-year?
   :eyr exp-year?
   :hgt height?
   :hcl hex-color?
   :ecl eye-color?
   :pid passport-id?
   :cid (constantly true) ; cid (Country ID) - ignored, missing or not.
   })

(defn strict-check
  [[k v]]
  (dispatch-check strict-checks k v))

(defn valid-passport-strict?
  [p]
  (if (valid-passport? p)
    (every? strict-check p)
    false))

(defn part-2
  []
  (->> (li/read-input "4.txt")
       (count-valid valid-passport-strict?)))
