(ns boolean-logic.truth-table-test
  (:require [clojure.test :refer :all]
            [clojure.pprint :as pp])
  (:require  [boolean-logic.core :refer :all]
             [boolean-logic.cnf :refer :all]
             [boolean-logic.truth-table :refer :all] :reload-all))



(def bf1 [:and "a1" "b2"])
(def bf1_truth_table '(
                       [{"b2" :true, "a1" :true} :true] 
                       [{"b2" :false, "a1" :true} :false] 
                       [{"b2" :true, "a1" :false} :false] 
                       [{"b2" :false, "a1" :false} :false]))


(def bf2 [:or "a1" "b2"])
(def bf2_truth_table '(
                       [{"b2" :true, "a1" :true} :true] 
                       [{"b2" :false, "a1" :true} :true] 
                       [{"b2" :true, "a1" :false} :true] 
                       [{"b2" :false, "a1" :false} :false]))


(def bf3 [:not "a1"])
(def bf3_truth_table '(
                       [{"a1" :true}  :false]                       
                       [{"a1" :false} :true] 
                       ))


(deftest truth-table-simple
  (testing "Simple truth table computation "
    (is (= (truth-table  bf1) bf1_truth_table))
    (is (= (truth-table  bf2) bf2_truth_table))
    (is (= (truth-table  bf3) bf3_truth_table))
))





