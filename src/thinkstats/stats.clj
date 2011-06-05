(ns thinkstats.stats
  (:use clojure.contrib.generic.math-functions))

(defn mean [t]
  (/ (reduce + t)
     (count t)))

(defn variance [t & [mu]]
  (let [mu   (or mu (mean t))
        dev2 (map #(sqr (- % mu)) t)
        var  (mean dev2)]
    [mu var]))

