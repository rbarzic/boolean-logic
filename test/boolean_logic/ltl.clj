(ns boolean-logic.ltl
  (:gen-class))

;; LTL operator
;;

;; From : A roadmap for Formal Property Verification
;; Pallab Dasgupta
;; Springer

;; X : next-time operator
;; X: The next-time operator The property, Xφ, is true at a state of the un-
;; derlying state machine if φ is true in the next cycle, where φ may be
;; another temporal property or a Boolean property over the state bits. Xφ
;; is sometimes read as “next φ”, and the operator X is called the next-time
;; operator.
;; -> keyword :X
;; example [:X f]


;; F :  The future operator (or eventually operator) The property, F φ, is true at a state if φ is true some-
;; time (at some state) in the future.
;; -> keyword :F
;; example [:F f]

;; G  : The global operator (or always operator) The property, Gφ, is true at a state if φ is true always
;; in the future.
;; -> keyword :G

;; U : The until operator The property, φ U ψ is true at a state if ψ is true at
;; some future state, t, and φ is true at all states leading up to t.
;; -> keyword :U
;; example [:F f g]


;; The operators X and U are the only fundamental temporal operators – F and
;; G can be derived from combinations of U and Boolean operators.
