(ns thinkstats.exercises.ex-2-5
  (:use clojure.contrib.generic.math-functions
        [thinkstats.survey :only (pregnancy-lengths)]
        [thinkstats.stats  :only (mean variance)]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-2-5

;; 2.5 ----------------------------------------------------------------------
;; Write functions called PmfMean and PmfVar that take a Pmf object and
;; compute the mean and variance.

(def maphash (comp (partial into {}) map))

(def sum (partial reduce +))

(defn normalize [hist]
  (let [total (sum (vals hist))]
    (maphash (fn [[x f]] [x (/ f total)]) hist)))

(defn pmf-mean [pmf]
  (sum (map (fn [[x p]] (* x p)) pmf)))

(defn pmf-variance [pmf & [mu]]
  (let [mu  (or mu (pmf-mean pmf))
        var (sum (map (fn [[x p]] (* p (sqr (- x mu)))) pmf))]
    [mu var]))

(defn ex-2-5 []
  (let [lengths (map float (pregnancy-lengths))
        [m1 v1] (variance lengths)
        [m2 v2] (pmf-variance (normalize (frequencies lengths)))]
    (println (format (str "m1=%f, m2=%f, diff=%f\n"
                          "v1=%f, v2=%f, diff=%f\n")
                     m1 m2 (- m1 m2)
                     v1 v2 (- v1 v2)))))

;; ===========================================================================

(defn -main []
  (ex-2-5))

