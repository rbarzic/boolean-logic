(ns boolean-logic.parser-test
  (:require [clojure.test :refer :all]
            [boolean-logic.parser :refer :all] :reload-all))

(deftest parser-test
  (testing "Simple parser tests"
    (is (= (parse-named-boolean-function  "g = !(a & b)")
           '("g" [:not [:and "a" "b"]])))
    (is (= (parse-named-boolean-function  "h = !(a & b) =>  (e & f) ")
           '("h" [:impl [:not [:and "a" "b"]] [:and "e" "f"]])))
    ))
