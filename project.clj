(defproject boolean-logic "0.1.0-SNAPSHOT"
  :description "Experimenting with boolean logic and clojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/math.combinatorics "0.0.7"]
                 [org.clojure/core.match "0.2.1"]
                 [org.clojure/tools.trace "0.7.8"]
                 [org.ow2.sat4j/org.ow2.sat4j.sat "2.3.5"]
                 [org.ow2.sat4j/org.ow2.sat4j.core "2.3.5"]
                 [instaparse "1.3.2"]]
  :main ^:skip-aot boolean-logic.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             })
