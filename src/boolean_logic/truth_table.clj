(ns boolean-logic.truth-table
 (:require [clojure.set]
           [boolean-logic.core :refer :all])
  (:require [clojure.pprint :as pp] )
  (:require [boolean-logic.misc :as misc] )
  (:require [clojure.math.combinatorics :as combo])
  (:gen-class))

(defn truth-table 
  "Compute the truth table of the logic function bf"
  [bf]
  (let [sup (support bf)
        len (count sup)
        inputs (combo/selections [:true :false] len)
        worlds  (map #(zipmap sup %) inputs)]
    (map #( vector % (compute % bf)) worlds)
    ))



(def tt {:a 1 :b 2 :c 5})
(seq tt)
(map #(nth  % 1) (seq tt))

(defn satisfiable? 
  "Check if bf is satisfiable using an exhaustive truth table approach"
  [bf]
  (let [tt (truth-table bf)
        vals (misc/select-values tt)]
    (not (not-any? #(= :true %) vals))
  ))



(satisfiable? [:and "a" "b"])
(satisfiable? [:or  "a" "b"])
(satisfiable? [:and "a" [:not "a"]])
(satisfiable? :false)
(satisfiable? :true)
(satisfiable? "a")
