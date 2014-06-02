(ns boolean-logic.cnf-test
  (:require [clojure.test :refer :all]
            [boolean-logic.core :refer :all]
            [boolean-logic.cnf :refer :all]
           
))

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
(def bf8_cnf [:and [:or "a1" "d4"] [:or "b2" "d4"] [:or "c3" "d4"]])
(def bf9 [:and "a1" "e1"])


(deftest bf2cnf-simple
  (testing "Simple boolean evaluation "
    (is (= (bf2cnf bf1) [:and "a1" "b2"]))
    (is (= (bf2cnf bf2) [:not "c3"]))
    (is (= (bf2cnf bf3) bf3))
    (is (= (bf2cnf bf4) "b2"))
    (is (= (bf2cnf bf7) [:or "e1" "e2" "e3"] ))
    (is (= (bf2cnf bf8) bf8_cnf))
    
    
))
