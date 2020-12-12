(ns aoc-clj-2020.solution-02
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]))

(def parse-line
  (lp/line-parser
   #"(\d+)-(\d+)\s(.): (.*)"
   :min lp/atoi
   :max lp/atoi
   :char first
   :password identity))

(defn parse
  [s]
  (lp/lines-to parse-line s))

(defn in-range?
  [{:keys [min max]} n]
  (<= min n max))

(defn valid-frequency?
  [{:keys [char password] :as entry}]
  (in-range? entry (get (frequencies password) char 0)))

(defn char-at?
  [text char pos]
  (= char (nth text (dec pos))))

(defn valid-location?
  [{:keys [min max char password]}]
  (let [check (partial char-at? password char)]
    (not= (check min) (check max))))

(defn part-1
  []
  (lt/count-valid valid-frequency? (parse (li/read-input "2.txt"))))

(defn part-2
  []
  (lt/count-valid valid-location? (parse (li/read-input "2.txt"))))
