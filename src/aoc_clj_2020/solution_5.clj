(ns aoc-clj-2020.solution-5
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [aoc-clj-2020.util.test :as lt]
            [clojure.string :as str]))

(defn boarding-pass-to-seat-id
  [boarding-pass]
  (->> (Integer/parseInt
        (str/join
         (map {\F "0" \B "1" \L "0" \R "1"} boarding-pass))
        2)))

(defn load-seat-ids
  [file-name]
  (->> file-name
       (li/read-lines)
       (into (sorted-set) (map boarding-pass-to-seat-id))))

(defn part-1
  []
  (last (load-seat-ids "5.txt")))

(defn part-2
  []
  (let [ids (->> (load-seat-ids "5.txt"))
        [min-id max-id] ((juxt first last) ids)]
    (reduce (fn [_ s]
              (when (not (contains? ids s))
                (reduced s)))
            (range min-id (inc max-id)))))
