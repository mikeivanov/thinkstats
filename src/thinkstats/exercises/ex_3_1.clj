(ns thinkstats.exercises.ex-3-1
  (:use clojure.contrib.generic.math-functions
        [thinkstats.stats  :only (maphash mean normalize pmf-mean)]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-1

;; 3.1 ----------------------------------------------------------------------
;; Build a PMF of these [students per class] data and compute the mean as
;; perceived by the Dean. 

(def *class-size-dist* {[ 5  9] 8
                        [10 14]	8
                        [15 19]	14
                        [20 24]	4
                        [25 29]	6
                        [30 34]	12
                        [35 39]	8
                        [40 44]	3
                        [45 49]	2})

(defn bias [f t]
  (maphash (fn [[x p]] [x (f p x)]) t))

(defn ex-3-1 []
  (let [freq     (maphash (fn [[s n]] [(mean s) n]) *class-size-dist*)
        dean     (normalize freq)
        student  (normalize (bias * freq))
        unbiased (normalize (bias / student))
        means    (map pmf-mean [dean student unbiased])]
    (->> means
         (apply format (str "Dean's mean = %f\n"
                            "Student's mean = %f\n"
                            "Unbiased mean = %f\n"))
         (println))))

;; ===========================================================================

(defn -main []
  (ex-3-1))

