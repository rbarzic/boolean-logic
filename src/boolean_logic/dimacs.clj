(ns boolean-logic.dimacs
  (:require [clojure.pprint :as pp] )  
  (:gen-class))



(def not-nil? (complement nil?))

(defn remove-comment [l]
  (re-matches #"^c.*" l))


(defn extract-cnf-info [matcher result]
  (let [rf (re-find matcher)
        nbvar (read-string (rf 1))
        nbclauses (read-string (rf  2))]
    (assoc result :nbvar nbvar :nbclauses nbclauses)
   
    ))


(defn remove-tailling-zero- [v]
  (if (= 0 (peek v))
    (pop v)
    v))


(defn extract-clause [line result]
  (let [v (vec (re-seq #"-?\d+" line))
        vi  (vec  (map read-string v)) ;; conversion to integer
        m (:clauses result)]
    (assoc result :clauses (conj m  (remove-tailling-zero- vi))))
)




(defn reduce-fn [result line]
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
  (reduce reduce-fn {:clauses []} seq-of-str))

