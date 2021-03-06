(ns boolean-logic.core-test
  (:require [clojure.test :refer :all]
            [boolean-logic.core :refer :all] :reload-all))

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




(deftest literal-test
  (testing "Literal check test"
    (is (= (literal? "a") true ))
    (is (= (literal? [:not "a"]) true ))
    (is (= (literal? [:and "a" "b"]) false ))
    (is (= (literal? [:or "a" "b"]) false ))
))

(deftest all-literals-test
  (testing "All-literals check test"

    (is (=(all-literals? ["a" "b" "c"]) true))
    (is (=(all-literals?  ["a" [:not "b"] "c"]) true))
    (is (=(all-literals? ["a" [:and "b" "d"] "c"]) false))
))

(deftest compute-simple
  (testing "Simple boolean evaluation "
    (is (= (compute world bf1) :false))
    (is (= (compute world bf2) :true))
    (is (= (compute world bf3) :true))
    (is (= (compute world bf4) :false))
    (is (= (compute world bf5) :true))
    (is (= (compute world bf6) "d5"))
    (is (= (compute world bf7) :true))
    (is (= (compute world bf8) :false))
    (is (= (compute world bf9) :true))
))

(deftest compute-simple2
  (testing "Simple boolean evaluation - part II"
    (is  (= (compute {"a1" :true "b1" :false "c1" :false "d1" :true}  
                     [:and "a1" "b1" "c1" "d1"] 
                     ) :false))
    (is (= (compute   {"a1" :true "b1" :false "c1" :false "d1" :true}
                      [:or "a1" "b1" "c1" "d1"]
                     ) :true))
    (is (= (compute   {"a1" :true "b1" :false "c1" :false "d1" :true}
                      [:or "b1" "c1" "d1"]
                  ) :true))
    (is (= (compute   {"a1" :true "b1" :false "c1" :false "d1" :true}
                      [:and "a1" [:or "b1" "c1" "d1"]] 
                  ) :true))
    (is (= (compute {"a1" :true "b1" :false "c1" :false "d1" :true}
                    [:or "a1" [:and "b1" "c1" "d1"]] 
                ) :true))
    (is (= (compute {"a1" :true "b1" :false "c1" :false "d1" :false}
                    [:and "a1" [:or "b1" "c1" "d1"]] 
                ) :false))
    (is (= (compute  {"a1" :false "b1" :false "c1" :false "d1" :false}
                     [:or "b1" "c1" ] 
                 ) :false))

))


(deftest compute-incomplete-evaluation
  (testing "Incomplete evaluation  - part I"
    (is  (= (compute {"a1" :true }  
                     bf3 
                     )  [:not "c3"])) 
    ;; bf6 [:and   [:or "a1" "b2" "c3"] "d5"])
    (is  (= (compute {"a1" :true }  
                     bf6
                     )  "d5")) 
    (is  (= (compute {"a1" :false }  
                     bf6
                     )  [:and   [:or "b2" "c3"] "d5"])) 
    ;; (def bf8 [:or  [:and "a1" "b2" "c3"] "d4"])
     (is  (= (compute {"a1" :true }  
                     bf8
                     )   [:or  "d4" [:and "b2" "c3"] ])) 
     ;; (def bf9 [:and "a1" "e1"])
     (is  (= (compute {"a1" :true }  
                     bf9
                     )   "e1")) 

))



(deftest support-test
  (testing "Simple support tests"
    (is (= (support bf1) ["a1" "b2"]))
    (is (= (support bf2) ["c3"]))
    (is (= (support bf3) ["a1" "c3"]))
    (is (= (support bf4) ["b2"]))
    (is (= (support bf5) []))
    (is (= (support bf6) ["a1" "b2" "c3" "d5"]))
))
