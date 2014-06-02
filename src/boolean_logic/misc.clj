;; Miscellaneous/useful  functions

(ns boolean-logic.misc
  (:gen-class))

(defn select-values
  "Return values of the map as a seq"
  [m]
  (map #(nth  % 1) (seq m)))
