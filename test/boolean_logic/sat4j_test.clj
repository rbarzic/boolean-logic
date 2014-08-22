(ns boolean-logic.sat4j-test
  (:require [clojure.test :refer :all]            
            [boolean-logic.sat4j :refer :all]
            [boolean-logic.core :refer :all]
            [boolean-logic.cnf :refer :all]
            [boolean-logic.truth-table :refer :all]
            :reload-all))


(def bf8 [:or  [:and "a1" "b2" "c3"] "d4"])

(def bf9 [:and "a1" [:not "a1"] ])

(def bf7_cnf [:or "e1" "e2" "e3"])
 
(def fc1  {:nbclauses 3, :nbvar 5, 
           :clauses [
                     [1 -5 4] 
                     [-1 5 3 4] 
                     [-3 -4]]})



(def fc1-expected-result [-1 -3 -4 -5])

(deftest sat4j-simple-tests
  (testing "Very simple SAT evaluation"
    (is (= (solve-problem fc1) fc1-expected-result))
    ))


(solve-problem (cnf2sat4j (bf2cnf bf8)))

;; (solve-problem-iter  (cnf2sat4j (bf2cnf bf8)))
;; (solve-problem-iter  (cnf2sat4j bf7_cnf))

;; (solve-problem2 (cnf2sat4j (bf2cnf bf8)))
;; (get-all-solutions  (cnf2sat4j (bf2cnf bf8)))
(get-all-solutions  (cnf2sat4j (bf2cnf bf8)))
(get-all-solutions  (cnf2sat4j bf7_cnf))


(cnf-get-all-solutions-as-mapping  (bf2cnf bf8))
(bf-get-all-solutions-as-mapping  bf8)

(bf2cnf bf8)

;; Will generate a org.sat4j.specs.ContradictionException

;; (bf2cnf bf9)
;; (cnf2sat4j (bf2cnf bf9))
;; (bf-get-all-solutions-as-mapping  bf9)

