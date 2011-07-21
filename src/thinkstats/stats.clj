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

(def maphash (comp (partial into {}) map))

(def sum (partial reduce +))

(defn normalize [hist]
  (let [total (sum (vals hist))]
    (maphash (fn [[x f]] [x (/ f total)]) hist)))

