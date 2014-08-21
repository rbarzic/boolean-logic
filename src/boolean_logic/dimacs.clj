(ns boolean-logic.dimacs
  (:require [clojure.pprint :as pp] )  
  (:gen-class))



(def not-nil? (complement nil?))

(defn- remove-comment [l]
  (re-matches #"^c.*" l))


(defn- extract-cnf-info [matcher result]
  "Extract informations from the matcher and insert them into the result
  map - Matcher is defined in reduce-fn function"
  (let [rf (re-find matcher)
        nbvar (read-string (rf 1))
        nbclauses (read-string (rf  2))]
    (assoc result :nbvar nbvar :nbclauses nbclauses)
   
    ))


(defn- remove-tailling-zero- [v]
  "Clauses can end up with a zero - which is optional"
  (if (= 0 (peek v))
    (pop v)
    v))


(defn- extract-clause [line result]
  (let [v (vec (re-seq #"-?\d+" line))
        vi  (vec  (map read-string v)) ;; conversion to integer
        m (:clauses result)]
    (assoc result :clauses (conj m  (remove-tailling-zero- vi))))
)




(defn- reduce-fn [result line]
  "Function to be applied on each line of the dimacs file. Will call
  various sub-function depending on the type of line"
  (let [m-cnf (re-matcher #"^p\s+cnf\s+(\d+)\s+(\d+)" line)
        m-comment (re-matches #"c.*" line)
        m-clause (re-matches  #"[-0-9 ]+" line)]
    (cond
     (re-matches #"c.*" line) result ;; comment ->  skip
     (re-matches  #"[-0-9 ]+" line) (extract-clause line result)
     (not-nil? m-cnf) (extract-cnf-info m-cnf result) 
     :else result
     )

    ))


(defn parse-dimacs [seq-of-str]
  "Take an seq of strings in Dimacs format, return a map. Example : 
  {:nbclauses 2, 
   :nbvar 6, 
    :clauses [
        [1 -2  3] 
        [4 5 6]]}"
  (reduce reduce-fn {:clauses []} seq-of-str))

