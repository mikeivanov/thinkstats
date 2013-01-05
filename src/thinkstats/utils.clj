(ns thinkstats.utils)

(defn cwd []
  (System/getProperty "user.dir"))

(defn read-lines [file]
  (line-seq (clojure.java.io/reader file)))

(defn parse-int [s]
  (Integer/parseInt (.trim s)))

