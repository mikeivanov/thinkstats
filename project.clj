(defproject thinkstats "1.0.0-SNAPSHOT"
  :description "My Think Stats book worksheet"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [incanter "1.3.0-SNAPSHOT" :exclusions [swank-clojure]]]
  :extra-classpath-dirs ["resources"]
  :plugins [[lein-swank "1.4.3"]])
