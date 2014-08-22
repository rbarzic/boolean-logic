(ns boolean-logic.sat4j
  (:gen-class)
  (:require 
           [boolean-logic.core :refer :all]
           [boolean-logic.cnf :refer :all])
  (:import org.sat4j.minisat.SolverFactory)
  (:import org.sat4j.tools.ModelIterator)
  (:import [org.sat4j.core Vec  VecInt] ))


(defn setup-solver [nbvars nbclauses]
  (let [solver (SolverFactory/newDefault)]
    (doto solver
      (.setTimeout 3600 )
      (.newVar nbvars)
      (.setExpectedNumberOfClauses nbclauses))
    solver))


(defn build-problem [f]
  (let [clauses (:clauses f)
        nbclauses (:nbclauses f)
        nbvar (:nbvar f)
        solver (setup-solver nbvar nbclauses)]
    (doseq [c clauses]
      (.addClause solver     
                  (new VecInt (int-array c))))
    solver))





(defn solve-problem [f]
  (let [problem (build-problem f)]
    (if (.isSatisfiable problem)
      (vec (.model problem))
      nil
      )))


;; to try to get several results 
;; does not work  (yet...)
(defn solve-problem2 [f]
  (let [problem (build-problem f)]
    (if (.isSatisfiable problem)
      (conj (vec (.model problem)) (vec (.model problem)))
      nil
      )))






(defn setup-solver-iter [nbvars nbclauses]
  (let [solver (ModelIterator. (SolverFactory/newDefault))] ;; (ModelIterator. args) == (new ModelIterator args)
    (doto solver
      (.setTimeout 3600 )
      (.newVar nbvars)
      (.setExpectedNumberOfClauses nbclauses))
    solver))


(defn build-problem-iter [f]
  (let [clauses (:clauses f)
        nbclauses (:nbclauses f)
        nbvar (:nbvar f)
        solver (setup-solver-iter nbvar nbclauses)]
    (doseq [c clauses]
      (.addClause solver     
                  (new VecInt (int-array c))))
    solver))







(defn get-all-solutions [f]
  (let [problem (build-problem-iter f)
        result []]
    (loop [r result]
      (if (.isSatisfiable problem)
        (recur (conj r (vec  (.model problem))) )
        r
        )
      )))


(defn solution2mapping
  "Return a map variable->value for a solution from sat4j SAT solver 
  support is expected to be a vector"
  [ support solution]
  (letfn [(abs [n] (max n (- n))) ;; no abs function by default in clojure :-(
          (val01 [v] (if (pos? v) 1 0)) 
          (reduce-fn [result idx]
            (into result {(support (dec (abs idx))) 
                          (val01 idx)}))]
    (reduce reduce-fn {} solution)
    ))



(defn cnf-get-all-solutions-as-mapping
  [cnf]
  (let [sup (support cnf)
        bf-sat4j (cnf2sat4j cnf)
        solutions (get-all-solutions bf-sat4j)]
    (map #(solution2mapping (vec  sup) %) solutions)
    ))


(defn bf-get-all-solutions-as-mapping
  [bf]
  (let [sup (support bf)
        cnf (bf2cnf bf)
        bf-sat4j (cnf2sat4j cnf)
        solutions (get-all-solutions bf-sat4j)]
    (map #(solution2mapping (vec  sup) %) solutions)
    ))




