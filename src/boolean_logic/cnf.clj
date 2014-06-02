(ns boolean-logic.cnf
 (:require [clojure.set]
           [boolean-logic.core :refer :all])
  (:require [clojure.pprint :as pp] )
  (:require [clojure.math.combinatorics :as combo])
  (:gen-class))


(defmulti bf2cnf
  "Convert a  boolean function bf to cnf format (  ( a | b | c) & ( d | e | f)  & ...)"
  (fn [bf] (if (vector? bf) (let [x (first bf)]
                     ;; We check first element of vector
                     ;; if it does not match a known operator
                     ;; we assume a boolean variable
                     (if (contains? boolean_ops x) x :var))
                     ;; else not a vector, must be a variable
                     :var
                     )))

(defmethod bf2cnf :and
  [bf]
   (let [r (distinct (map #(bf2cnf  %) (rest bf)))]
    (defn move-and-up [e]
    (if (vector? e)
      (if (= (first e) :and)
        (rest e) ;; we return the data of the ":and" vector
        (vector e) ;; not :and, probably :or, we wrap it into a vector to not merge it in the mapcat operation
        )
      (vector e)))
     (vec (concat [:and] (mapcat  move-and-up r)))
     )
  )


(defmethod bf2cnf :or
  [bf]
   (let [r (vec (distinct (map #(bf2cnf  %) (rest bf))))]
    (defn  attach [a b]
      (if (vector? a)
      (conj a b)
      [a b]))

   (defn to_vec [x]
     (if (vector? x) (rest x) (vector x)))
   (defn mul-or
     ([x y]
      (for [a x b y] (attach a b)))
     ([x]  x))


   (vec (concat [:and]
                (map
                   #( vec (cons  :or % ))
                   (reduce mul-or (map to_vec r))))
  )
))




(defmethod bf2cnf :not
  [bf]
  (let [nexp  (nth bf 1 )]
    (if (vector? nexp)
      (let [x (first nexp)]
        (case x
          ;; ~(~P) -> P
          :not (bf2cnf (second nexp ))
          ;; ~(P and Q) -> (~P or ~Q)
          :and (bf2cnf  (concat  [:or] (map #(conj [:not %]) (rest nexp) )))
          ;; ~(P or Q) -> (~P and ~Q)
          :or (bf2cnf  (concat  [:and] (map #(conj [:not %]) (rest nexp) )))))
      ;; not a vector
      bf
      )))




(defmethod bf2cnf :var
  [bf]
    bf)
