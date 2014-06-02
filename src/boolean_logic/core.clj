(ns boolean-logic.core
 (:require [clojure.set] )
  (:require [clojure.pprint :as pp] )
  (:require [clojure.math.combinatorics :as combo])
  (:gen-class))




(def boolean_ops #{:and :or :not})
(def boolean_ops_tf #{:and :or :not :true :false})

;; from stack overflow - contains? can't be used with vector
(defn in?
  "true if seq contains elm"
  [seq elm]
  (some #(= elm %) seq))


(defmulti compute
  "Evaluate a boolean function bf according to a existing mapping (a map symbol->value)"
  (fn [mapping bf] (if (vector? bf) (let [x (first bf)]
                     ;; We check first element of vector
                     ;; if it does not match a known operator
                     ;; we assume a boolean variable
                     (if (contains? boolean_ops x) x :var))
                     ;; else not a vector, must be a variable
                     :var
                     )))

(defmethod compute :and
  [mapping bf]
  (let [r (distinct (map #(compute mapping %) (rest bf)))]
    (if (in? r :false) :false ;; contains one false -> return false
        (if (= r  '(:true))
          :true ;; contains only one :true key -> return true
          (let [rr (disj (set  r) :true)] ;; continue without :true
            (if (= (count rr) 1)
              (first rr) ;; one variable ? -> return it
              (conj [:and] r)))))))

(defmethod compute :or
  [mapping bf]
  (let [r (distinct (map #(compute mapping %) (rest bf)))]
    (if (in? r :true)
       :true ;; contains one true -> return true
       (if (= r  '(:false))
          :false ;; contains only one :false key -> return false
          (let [rr (disj (set  r) :false)] ;; continue without :false
            (if (empty? rr)
              :false
              (if (= (count rr) 1)
                (first rr)
                (conj [:or] r))))))))



;;(defmethod compute :or
;;  [mapping bf]
;;  (let [r (distinct (map #(compute mapping %) (rest bf)))]
;;    (if (in? r :true) :true
;;        (conj [:or] r))))


(defmethod compute :not
  [mapping bf]
  (let [nexp  (compute mapping (nth bf 1) )]
    (if (= nexp :true) :false
        (if (= nexp :false) :true
            [:not bf]))))

(defmethod compute :var
  [mapping bf]
    (get mapping bf bf))



(defn support
  "Return the support of a boolean function as a sequence"
  [bf]
  (if (vector? bf)
    (sort (clojure.set/difference (set (flatten bf)) boolean_ops_tf))
    (sort (clojure.set/difference (set (vector bf))  boolean_ops_tf))
  ))




(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
