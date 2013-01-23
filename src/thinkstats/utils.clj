(ns thinkstats.utils)

(defn cwd []
  (System/getProperty "user.dir"))

(defn read-lines [file]
  (line-seq (clojure.java.io/reader file)))

(defn parse-int [s]
  (Integer/parseInt (.trim s)))

(defn pairwise [seq]
  ;map vector seq (drop 1 seq)))
  (partition 2 1 seq))

