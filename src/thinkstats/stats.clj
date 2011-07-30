(ns thinkstats.stats
  (:use clojure.contrib.generic.math-functions))

(def sum (partial reduce +))

(defn mean [t]
  (/ (sum t)
     (count t)))

(defn variance [t & [mu]]
  (let [mu   (or mu (mean t))
        dev2 (map #(sqr (- % mu)) t)
        var  (mean dev2)]
    [mu var]))

(def maphash (comp (partial into {}) map))

(defn normalize [hist]
  (let [total (double (sum (vals hist)))]
    (maphash (fn [[x f]] [x (/ f total)])
             hist)))

(def pmf (comp normalize frequencies))

(defn pmf-mean [pmf]
  (->> pmf
       (map (fn [[x p]] (* x p)))
       (sum)))

(defn pmf-variance [pmf & [mu]]
  (let [mu  (or mu (pmf-mean pmf))
        var (->> pmf
                 (map (fn [[x p]] (* p (sqr (- x mu)))))
                 (sum))]
    [mu var]))

