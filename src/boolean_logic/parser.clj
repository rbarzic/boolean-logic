(ns boolean-logic.parser
  (:require [instaparse.core :as insta])
  (:gen-class))



(def parse-named-boolean-function
  (insta/parser
   "<function> = id <'='> or-exp
     <or-exp> = and-exp | or | xor | impl
     or = or-exp <'|'> and-exp
     xor = or-exp <'^'> and-exp
     impl = or-exp <'=>'> and-exp
     <and-exp> = term2 | and
     and = and-exp <'&'> term2
     <term2> = term | not
     not = <'!'> term
     <term> = id | <'('> or-exp <')'>
     <id> = #'[a-zA-Z0-9]+'"
   :auto-whitespace :standard))





(parse-named-boolean-function "g = !(a & b)");;=>("g" [:not [:and "a" "b"]])
(parse-named-boolean-function "g = !(a & b) ^ c ")
(parse-named-boolean-function "g = !(a & b) ^ (e & f) ")
(parse-named-boolean-function "h = !(a & b) =>  (e & f) ");;=>("h" [:impl [:not [:and "a" "b"]] [:and "e" "f"]])
