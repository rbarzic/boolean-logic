(ns boolean-logic.cnf-test
  (:require [clojure.test :refer :all])
  (:require  [clojure.core.match :as cm])
  (:require  [boolean-logic.core :refer :all]
             [boolean-logic.cnf :refer :all]
             [boolean-logic.truth-table :refer :all] :reload-all)) 
 







(def world {"a1" :true "b2" :false "c3" :false "d4" :false
            "e1" :true "e2" :false "e3" :false})

(def bf1 [:and "a1" "b2"])
(def bf2 [:not "c3"])
(def bf3 [:and "a1" [:not "c3"]])
(def bf4 "b2")
(def bf5 :true)
(def bf6 [:and   [:or "a1" "b2" "c3"] "d5"])
(def bf7 [:or "e1" "e2" "e3"])
(def bf8 [:or  [:and "a1" "b2" "c3"] "d4"])

(def bf9 [:and "a1" "e1"])
(def bf10 [:and "a1" [:not [:or "b1" "c1"]]])
(def bf11 [:and "a1" [:not "b1"]])
(def bf12 [:not [:or "b1" "c1"]])

 ;; (A && ~(B || C))

;;  Result
;; verified using Wolfram Alpha

(def bf1_cnf [:and "a1" "b2"])
(def bf2_cnf [:not "c3"])
(def bf3_cnf [:and "a1" [:not "c3"]])
(def bf4_cnf "b2")
(def bf5_cnf :true)
(def bf6_cnf [:and   [:or "a1" "b2" "c3"] "d5"])
(def bf7_cnf [:or "e1" "e2" "e3"])
(def bf8_cnf  [:and [:or "a1" "d4"] [:or "b2" "d4"] [:or "c3" "d4"]])
(def bf9_cnf  [:and "a1" "e1"])
(def bf10_cnf [:and "a1" [:not "b1"] [:not "c1"]])
(def bf11_cnf [:and "a1" [:not "b1"]])
(def bf12_cnf [:and [:not "b1"] [:not "c1"]])

;; sat4j vector
;; bf8:cnf :
;; [:and [:or "a1" "d4"] [:or "b2" "d4"] [:or "c3" "d4"]])
;; => ("a1" "b2" "c3" "d4")

(def bf8_sat4j
  [
   [ 1 4]  ;; [:or "a1" "d4"]
   [ 2 4]  ;; [:or "b2" "d4"]
   [ 3 4]  ;; [:or "c3" "d4"]
   ])

(def bf8_sat4j_full
  {:nbvar     4
   :nbclauses 3
   :clauses 
   [
    [ 1 4]  ;; [:or "a1" "d4"]
    [ 2 4]  ;; [:or "b2" "d4"]
    [ 3 4]  ;; [:or "b3" "d4"]
    ]}
  )




(def bf7_sat4j
  [
   [ 1 2 3]  ;; [:or "e1" "e2" "e3"]   
   ])




(deftest bf2cnf-simple-test
  (testing "Simple conversion to CNF "
    (is (= (bf2cnf bf1) bf1_cnf))
    (is (= (bf2cnf bf2) bf2_cnf))
    (is (= (bf2cnf bf3) bf3_cnf))
    (is (= (bf2cnf bf4) bf4_cnf))
    (is (= (bf2cnf bf5) bf5_cnf))
    (is (= (bf2cnf bf6) bf6_cnf))
    (is (= (bf2cnf bf7) bf7_cnf))
    (is (= (bf2cnf bf8) bf8_cnf))
    (is (= (bf2cnf bf9) bf9_cnf))
    (is (= (bf2cnf bf10) bf10_cnf))
    (is (= (bf2cnf bf11) bf11_cnf))
    (is (= (bf2cnf bf12) bf12_cnf))))

(deftest cnf2sat4j-array-simple-test 
  (testing "Simple conversion to array of clauses "
    (is (= (cnf2sat4j-array bf8_cnf) bf8_sat4j))
    (is (= (cnf2sat4j-array bf7_cnf) bf7_sat4j))
))

(deftest cnf2sat4j-conversion 
  (testing "Simple conversion to a map, suitable for sat4j interface "
    (is (= (cnf2sat4j  bf8_cnf) bf8_sat4j_full))
    (is (= (cnf2sat4j  (bf2cnf bf8)) bf8_sat4j_full))
))
(cnf2sat4j bf6_cnf)




