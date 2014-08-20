(ns boolean-logic.dimacs-test
  (:require [clojure.test :refer :all]           
            [boolean-logic.dimacs :refer :all]            
            ))


(def fc1 '("c"
          "c start with comments"
          "c"
          "c "
          "p cnf 5 3"
          "1 -5 4 0"
          "-1 5 3 4 0"
          "-3 -4 0"
          ))


(def fc1-no-zero '("c"
           "c start with comments"
           "c"
           "c "
           "p cnf 5 3"
           "1 -5 4 "
           "-1 5 3 4 "
           "-3 -4 "
           ))


(def fc2 '(
           "p cnf 6 2" ;;no comment section
           "1 -2 3 0"
           "4 5 6 "          
           ))



(def fc1-expected-result {:nbclauses 3, :nbvar 5, 
      :clauses [
                [1 -5 4] 
                [-1 5 3 4] 
                [-3 -4]]})


(def fc2-expected-result {:nbclauses 2, :nbvar 6, 
                          :clauses [
                                    [1 -2  3] 
                                    [4 5 6]]}) 




(deftest dimacs-simple-parsing
  (testing "Parsing of simple dimacs strings "
    (is (= (parse-dimacs fc1) fc1-expected-result))
    (is (= (parse-dimacs fc1-no-zero) fc1-expected-result))
    (is (= (parse-dimacs fc2) fc2-expected-result))
    ))
