(ns aoc-clj-2020.solution-2
  (:require [aoc-clj-2020.util.input :as li]))

(defn parse-line
  [t]
  (let [[_ min max char password] (re-find #"(\d+)-(\d+)\s(.): (.*)" t)]
    {:min (Integer/parseInt min)
     :max (Integer/parseInt max)
     :char (first char)
     :password password}))

(defn in-range?
  [{:keys [min max]} n]
  (<= min n max))

(defn parse-input
  [file-name]
  (into []
        (map parse-line)
        (li/read-lines file-name)))

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
  (->> (parse-input "2.txt")
       (filter valid-frequency?)
       (count)))

(defn part-2
  []
  (->> (parse-input "2.txt")
       (filter valid-location?)
       (count)))
