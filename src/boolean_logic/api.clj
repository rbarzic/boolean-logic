(ns boolean-logic.api
  (:require [boolean-logic.core :as bf]))




(defprotocol BOOLLOGIC
  (evaluate [bf mapping])
  (support [bf world])
  (sat? [bf])
  (satisfiable  [bf])
)


(defrecord BooleanFunction [name value])

(defrecord LTLFunction [name value])



(extend-type BooleanFunction
  BOOLLOGIC
  (evaluate [bf mapping]
    (bf/compute mapping (:value bf)) ))

(def bf1 (BooleanFunction. "f" [:or "a" "b"]))

(defn blFromMap
  "Create a BooleanFunction record from a map"
  [name val_as_a_map])


(defn blFromString
  "Create a BooleanFunction record from a string using an instaparse parser"
  [name val_as_a_map])

(defn blFromMap
  "Create a LTL Function record from a map"
  [name val_as_a_map])


(defn ltlFromString
  "Create a LTL record from a string using an instaparse parser"
  [name val_as_a_map])



(evaluate bf1 {"a" :false})
