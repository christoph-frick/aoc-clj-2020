(ns aoc-clj-2020.solution-20
  (:require [aoc-clj-2020.util.input :as li]
            [aoc-clj-2020.util.parse :as lp]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn parse
  [input]
  (->> input
       (lp/split-groups)
       (into []
             (map (fn [group]
                    (let [[tile-id & lines] (str/split-lines group)]
                      {:id (lp/atoi (re-find #"\d+" tile-id))
                       :image lines}))))))

(defn borders
  [{:keys [image]}]
  (let [border (into []
                     (map (fn [extractor]
                            (str/join "" (extractor image))))
                     [(comp seq first) (partial map last) (comp seq last) (partial map first)])
        rotated (->> (cycle border)
                     (partition 4 1)
                     (take 4))
        flip-v (fn [[n e s w]]
                 [(str/reverse n) w (str/reverse s) e])
        flip-h (fn [[n e s w]]
                 [s (str/reverse e) n (str/reverse w)])]
    (into #{}
          (mapcat
            (juxt identity flip-v flip-h))
          rotated)))

(defn potentials
  [rules]
  (let [unique-borders (into {}
                             (map (fn [{:keys [id] :as image}]
                                    [id (into #{} cat (borders image))]))
                             rules)
        ids (keys unique-borders)]
    (into {}
          (map (fn [id]
                 [id (into #{}
                           (filter (fn [other]
                                     (and (not= id other)
                                          (pos? (count (set/intersection (unique-borders id)
                                                                         (unique-borders other)))))))
                           ids)]))
          ids)))

(defn quickn-dirty-corner-sum
  [rules]

  (let [corners
        (map key
             (filter (fn [p]
                       (= 2
                          (count (val p))))
                     (potentials rules)))]
    (apply * corners)))

(defn part-1
  []
  (->> (li/read-input "20.txt")
       (parse)
       (quickn-dirty-corner-sum)))

(defn part-2
  []
  nil)
