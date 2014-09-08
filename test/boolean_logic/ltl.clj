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


;; F g == true U g
;; G f = not (true U (not f))


;; Always operator: A property, always f , is true on a run iff the property f
;; holds on all states of the run. This is the same as saying that ¬f never
;; holds on the run. In other words we may write:
;; always f = ¬eventually ¬f
;; eventually f = ¬always ¬f
;; The first equation allows us to express the always operator using the even-
;; tually operator, and in turn, in terms of the until operator.



;; Rewriting rules
;; f U g = g ∨ (f ∧ X(f U g))
;; F g = g ∨ XF g
;; Gf = f ∧ XGf


;; Also

;; always f = ¬eventually ¬f  -> G f= !F (!f)
;; eventually f = ¬always ¬f   -> Ff = ! G !f
;;

;; SAT example p93
;; P1 : G[ r 1 ⇒ Xg 1 ∧ XXg 1 ]

;; As before, we will look for a run satisfying ¬P 1 in the implementation. Let
;; φ = ¬P 1, that is:
; φ = F [ r1 ∧ ( ¬Xg1 ∨ ¬XXg1 ) ]

; demonstration
; φ = !P1 = !G[ r 1 ⇒ Xg 1 ∧ XXg 1 ]
;   = !G[ !r1 or (Xg1 & XXg1)]
;   = !G[ !r1 or (Xg1 & XXg1)]
;   = F[ !(!r1 or (Xg1 & XXg1))]
;   = F[ r1 and (!Xg1 or!XXg1 )]
