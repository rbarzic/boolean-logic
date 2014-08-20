(ns boolean-logic.sat4j-test
  (:require [clojure.test :refer :all]            
            [boolean-logic.sat4j :refer :all]           
            ))
 
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
