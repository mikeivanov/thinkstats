(defproject thinkstats "1.0.0-SNAPSHOT"
  :description "My Think Stats book worksheet"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [incanter "1.5.0-SNAPSHOT" :exclusions [swank-clojure]]
                 [org.clojure/math.numeric-tower "0.0.2"]]
  :extra-classpath-dirs ["resources"])
