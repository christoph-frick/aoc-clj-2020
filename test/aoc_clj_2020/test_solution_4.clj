(ns aoc-clj-2020.test-solution-4
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-clj-2020.solution-4 :as t]))

(def test-data
"ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in")

(deftest test-parse-passports
  (is (= (t/parse-passports test-data) [{:ecl "gry" :pid "860033327" :eyr "2020" :hcl "#fffffd" :byr "1937" :iyr "2017" :cid "147" :hgt "183cm"}
                                        {:iyr "2013"  :ecl "amb"  :cid "350"  :eyr "2023"  :pid "028048884"  :hcl "#cfa07d"  :byr "1929"}
                                        {:hcl "#ae17e1"  :iyr "2013"  :eyr "2024"  :ecl "brn"  :pid "760753108"  :byr "1931"  :hgt "179cm"}
                                        {:hcl "#cfa07d"  :eyr "2025"  :pid "166559648"  :iyr "2011"  :ecl "brn"  :hgt "59in"}])))

(deftest test-valid-passport?
  (are [p r] (= (t/valid-passport? p) r)
    {:ecl "gry"
     :pid "860033327"
     :eyr "2020"
     :hcl "#fffffd"
     :byr "1937"
     :iyr "2017"
     :cid "147"
     :hgt "183cm"} true
    {:ecl "gry"
     :pid "860033327"
     :eyr "2020"
     :hcl "#fffffd"
     :byr "1937"
     :iyr "2017"
     ; :cid "147"
     :hgt "183cm"} true
    {; :ecl "gry"
     :pid "860033327"
     :eyr "2020"
     :hcl "#fffffd"
     :byr "1937"
     :iyr "2017"
     :cid "147"
     :hgt "183cm"} false))

(deftest test-count-valid
  (is (= 2 (t/count-valid-passports test-data))))

(deftest test-solution
  (are [f r] (= (f) r)
    t/part-1 222
    t/part-2 0))
