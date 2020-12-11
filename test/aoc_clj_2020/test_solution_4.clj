(ns ^:day-4 aoc-clj-2020.test-solution-4
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
  (is (= 2 (t/count-valid t/valid-passport? test-data))))

(deftest test-checks
  (are [k r v] (= r (if (t/strict-check [k v]) :valid :invalid))
    :byr :valid   "2002"
    :byr :invalid "2003"

    :hgt :valid   "60in"
    :hgt :valid   "190cm"
    :hgt :invalid "190in"
    :hgt :invalid "190"

    :hcl :valid   "#123abc"
    :hcl :invalid "#123abz"
    :hcl :invalid "123abc"

    :ecl :valid   "brn"
    :ecl :invalid "wat"

    :pid :valid   "000000001"
    :pid :invalid "0123456789"))

; byr (Birth Year) - four digits; at least 1920 and at most 2002.
(deftest test-birth-year?
  (are [s r] (= r (t/birth-year? s))
    "1919" false
    "1920" true
    "2002" true
    "2003" false

    "asdf" false
    "" false))

; iyr (Issue Year) - four digits; at least 2010 and at most 2020.
(deftest test-issue-year?
  (are [s r] (= r (t/issue-year? s))
    "2009" false
    "2010" true
    "2020" true
    "2021" false

    "asdf" false
    "" false))

; eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
(deftest test-exp-year?
  (are [s r] (= r (t/exp-year? s))
    "2019" false
    "2020" true
    "2030" true
    "2031" false

    "asdf" false
    "" false))

; hgt (Height) - a number followed by either cm or in:
; If cm, the number must be at least 150 and at most 193.
; If in, the number must be at least 59 and at most 76.
(deftest test-heigt?
  (are [s r] (= r (t/height? s))
    "149cm" false
    "150cm" true
    "193cm" true
    "194cm" false

    "58in" false
    "59in" true
    "76in" true
    "77in" false

    "asdf" false
    "" false) )

(def all-invalid-passports
  "eyr:1972 cid:100
hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

iyr:2019
hcl:#602927 eyr:1967 hgt:170cm
ecl:grn pid:012533040 byr:1946

hcl:dab227 iyr:2012
ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

hgt:59cm ecl:zzz
eyr:2038 hcl:74454a iyr:2023
pid:3556412378 byr:2007")

(def all-valid-passports
  "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
hcl:#623a2f

eyr:2029 ecl:blu cid:129 byr:1989
iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

hcl:#888785
hgt:164cm byr:2001 iyr:2015 cid:88
pid:545766238 ecl:hzl
eyr:2022

iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719")

(deftest test-valid-passport-strict?
  (are [passports r] (every?
                      #(= r (t/valid-passport-strict? %))
                      (t/parse-passports passports))
    all-invalid-passports false
    all-valid-passports true))

(deftest test-solution
  (are [f r] (= r (f))
    t/part-1 222
    t/part-2 140))
