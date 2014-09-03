[![Build Status](https://travis-ci.org/rbarzic/boolean-logic.svg?branch=master)](https://travis-ci.org/rbarzic/boolean-logic)

[![Clojars Project](http://clojars.org/boolean-logic/latest-version.svg)](http://clojars.org/boolean-logic)

# boolean-logic


A clojure library to play with boolean logic (under construction !)

## Usage/Installation


### Leiningen

Add [boolean-logic _put_version_as_shown_above_here_ ] to your project.clj :dependencies vector.

Example of a project.clj :

```clojure
(defproject test-boolean-logic "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"] [boolean-logic "0.1.0-SNAPSHOT"]]
  :main ^:skip-aot test-boolean-logic.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [
             [com.jakemccrary/lein-test-refresh "0.5.1"]
             [cider/cider-nrepl "0.7.0"]]}})
```
## Options


## Examples


### evaluation

```clojure
(ns test-boolean-logic.core
                     (:require 
                      [boolean-logic.core :as bl]
                      [boolean-logic.cnf :as cnf]
                      [boolean-logic.dimacs :as dimacs]
                      [boolean-logic.sat4j :as sat4j])
                     (:gen-class))


(def bf1 [:and "a" [:not "b"]])) ; a boolean function  (a & !b)

(bl/support bf1)  ; => ("a" "b")

(bl/compute {"a" :true "b" :false} bf1); => true

(bl/compute {"a" :true } bf1); => [:not "b"]



```

### Conversion to cnf (Conjunctive normal form)

```clojure

(def bf2 [:and   [:or "a" "b" "c"] "d"]) ; (a | b | c) & d

(def bf3 [:or  [:and "a" "b" "c"] "d"]) ; (a & b & c) | d


(cnf/bf2cnf bf2) ; => [:and [:or "a" "b" "c"] "d"] (bf2 was already in cnf format)


(cnf/bf2cnf bf3) ; => [:and [:or "a" "d"] [:or "b" "d"] [:or "c" "d"]]



```


### Dimacs reader


```clojure
;;; TBD

```



### Using the sat4j solver

```clojure

;;; TBD

```



### Bugs

Probably some....


## License

Copyright © 2014 Ronan Barzic

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
