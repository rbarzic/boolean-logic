(ns boolean-logic.cnf
 (:require [clojure.set]
           [boolean-logic.core :refer :all]
           [clojure.core.match :as cm])
  (:require [clojure.pprint :as pp] )
  (:require [clojure.math.combinatorics :as combo])
  (:gen-class))


;; Conversion to CNF
;; see http://www.cs.jhu.edu/~jason/tutorials/convert-to-CNF.html


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


;; If φ has the form P ^ Q, then:
;;    CONVERT(P) must have the form P1 ^ P2 ^ ... ^ Pm, and
;;    CONVERT(Q) must have the form Q1 ^ Q2 ^ ... ^ Qn,
;;    where all the Pi and Qi are disjunctions of literals.
;;    So return P1 ^ P2 ^ ... ^ Pm ^ Q1 ^ Q2 ^ ... ^ Qn.
;;
(defmethod bf2cnf :and *bf2cnf-and*
  [bf]
   (let [r (distinct (map #(bf2cnf  %) (rest bf)))]
    (defn move-and-up [e]
      (if (vector? e)
        (if (= (first e) :and)
          (rest e) ;; we return the data of the ":and" vector
          (vector e) ;; not :and, probably :or, we wrap it into a vector to not merge it in the mapcat operation
          )
        (vector e)))
    (do (println "and-func")
        (vec (concat [:and] (mapcat  move-and-up r))))
     )
  )

;; If φ has the form P v Q, then:
;;    CONVERT(P) must have the form P1 ^ P2 ^ ... ^ Pm, and
;;    CONVERT(Q) must have the form Q1 ^ Q2 ^ ... ^ Qn,
;;    where all the Pi and Qi are dijunctions of literals.
;;    So we need a CNF formula equivalent to
;;       (P1 ^ P2 ^ ... ^ Pm) v (Q1 ^ Q2 ^ ... ^ Qn).
;;    So return (P1 v Q1) ^ (P1 v Q2) ^ ... ^ (P1 v Qn)
;;            ^ (P2 v Q1) ^ (P2 v Q2) ^ ... ^ (P2 v Qn)
;;              ...
;;            ^ (Pm v Q1) ^ (Pm v Q2) ^ ... ^ (Pm v Qn)
;;
(defmethod bf2cnf :or *bf2cnf-or*
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

   (if (all-literals? bf)
     bf ;; return vector directly
     (vec (concat [:and] ;; else apply transformation
                (map
                   #( vec (cons  :or % ))
                   (reduce mul-or (map to_vec r))))))))



;; If φ has the form ~(...), then:
;;   If φ has the form ~A for some variable A, then return φ.
;;   If φ has the form ~(~P), then return CONVERT(P).           // double negation
;;   If φ has the form ~(P ^ Q), then return CONVERT(~P v ~Q).  // de Morgan's Law
;;   If φ has the form ~(P v Q), then return CONVERT(~P ^ ~Q).  // de Morgan's Law
(defmethod bf2cnf :not *bf2cnf-not*
  [bf]
  (let [nexp  (nth bf 1 )]
    (if (vector? nexp)
      (let [x (first nexp)]
        (case x
          ;; ~(~P) -> P
          :not (do println(bf2cnf (second nexp )))
          ;; ~(P and Q) -> (~P or ~Q)
          :and (do (println "not-and")(bf2cnf  (vec (concat  [:or] (vec (map #(conj [:not] %) (rest nexp)))))))
          ;; ~(P or Q) -> (~P and ~Q)
          :or (do (println "not-or")(bf2cnf  (vec (concat  [:and] (vec (map #(conj [:not] %) (rest nexp)))))))))
      ;; not a vector
      bf
      )))




(defmethod bf2cnf :var
  [bf]
    bf)




(defn get_variable_index
  [v sup ]
  (cm/match [v]
            [[:not  not_v]] (- (inc (.indexOf sup not_v)))
            [b] (inc (.indexOf sup b))           
            ))


(defn cnf2sat4j-array 
  "Return an array of array of indexes to be able to connect to sat4j API"
  [cnf]
  (let [sup (support cnf)]
    (defn variable_indexes [vs]
      (cm/match [vs]
                [[:or & r]] (vec (map #(get_variable_index % sup) r ))
                [v] (get_variable_index v sup)
      ))
    (cm/match [cnf]
              [[:and & r ]] (vec (map variable_indexes r))
              [[:or & r ]]  (vector (vec (map #(get_variable_index % sup) r)))
              :else "g"
              )))


