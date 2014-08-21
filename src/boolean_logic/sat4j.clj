(ns boolean-logic.sat4j
  (:gen-class)
  (:import org.sat4j.minisat.SolverFactory)
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


;; infinite loop ?
(defn get-all-solutions [f]
  (let [problem (build-problem f)
        result []]
    (if (.isSatisfiable problem)
      (loop [r result
             s (.model problem)]
        (if (nil? s)
          result
          (recur (conj r s) (.model problem)))
        )
      []
      )))
