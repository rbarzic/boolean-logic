(ns boolean-logic.truth-table-test
  (:require [clojure.test :refer :all]
            [boolean-logic.core :refer :all]            
            [boolean-logic.truth-table :refer :all] 
             [clojure.pprint :as pp]
))


(def world {"a1" :true "b2" :false "c3" :false "d4" :false
            "e1" :true "e2" :false "e3" :false})

(def bf1 [:and "a1" "b2"])


(deftest truth-table-simple
  (testing "Simple truth table computation "
    (is (= (truth-table  bf1) [:and "a1" "b2"]))
))





