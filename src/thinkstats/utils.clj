(ns thinkstats.utils)

(defn read-lines [file]
  (line-seq (clojure.java.io/reader file)))

(defn parse-int [s]
  (Integer/parseInt (.trim s)))
