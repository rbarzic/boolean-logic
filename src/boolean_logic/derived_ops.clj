(ns boolean-logic.derived_ops
  (:gen-class))




(def boolean_ops #{:and :or :not :impl :xor :eq})
(def boolean_ops_tf (into boolean_ops #{:true :false}))


(def bf1 [:impl "x" "y"]) ; x -> y  = [:or  [:not "x"] "y"]
(def bf1 [:xor  "x" "y"]) ; x xor y  = [:and  [:or "x" "y"] [:not [:and "x" "y"]]]

(defn simplify [bf]
  (if (vector? bf)
    (let [op (first bf) r (rest bf)]
      (cond
       (= op :impl) (let [x (simplify (nth bf 1)) y (simplify (nth bf 2)) ]
                      [:or [:not x] y])
       (= op :xor) (let [x (simplify (nth bf 1)) y (simplify (nth bf 2)) ]
                     [:and  [:or x y] [:not [:and x y]]])
       (= op :eq) (let [x (simplify (nth bf 1)) y (simplify (nth bf 2)) ]
                    [:not [:and  [:or x y] [:not [:and x y]]]])

       :else (into [op] (map simplify r))
        )
      )
    bf))

(simplify [:impl "x1" "y2"]);;=>[:or [:not "x1"] "y2"]
(simplify [:or "x1" "y2"]);;=>[:or "x1" "y2"]
(simplify [:or [:not "x1"] "y2"]);;=>[:or [:not "x1"] "y2"]
(simplify [:or [:not "x1"] [:not "y2"]]);;=>[:or [:not "x1"] [:not "y2"]]
(simplify [:xor "x1" "y2"]);;=>[:and [:or "x1" "y2"] [:not [:and "x1" "y2"]]]
(simplify [:eq "x1" "y2"]);;=>[:not [:and [:or "x1" "y2"] [:not [:and "x1" "y2"]]]]
