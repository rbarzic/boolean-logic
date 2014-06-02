(ns boolean-logic.truth-table
 (:require [clojure.set]
           [boolean-logic.core :refer :all])
  (:require [clojure.pprint :as pp] )
  (:require [clojure.math.combinatorics :as combo])
  (:gen-class))

(defn truth-table [bf]
  "Compute the truth table of the logic function bf"
  (let [sup (support bf)
        len (count sup)
        inputs (combo/selections [:true :false] len)
        worlds  (map #(zipmap sup %) inputs)]
    (map #( vector % (compute % bf)) worlds)
    ))
