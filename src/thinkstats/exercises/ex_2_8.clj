(ns thinkstats.exercises.ex-2-8
  (:use clojure.contrib.generic.math-functions
        [thinkstats.survey :only (pregnancy-lengths-split)]
        [thinkstats.stats  :only (normalize pmf maphash)])
  (:require [incanter.core   :as ic]
            [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-2-8

;; 2.8 ----------------------------------------------------------------------
;; Write a function that computes the probability that a baby will be born
;; during Week X, given that it was not born prior to Week X.

(defn cond-prob [pmf x]
  (let [cond-pmf (->> pmf
                      (filter (fn [[t p]] (<= x t)))
                      normalize)]
    (get cond-pmf x 0.0)))

;; Plot this value as a function of x for first babies and others.

(defn cond-prob-chart [fc oc]
  (-> (charts/xy-plot (keys fc)
                      (vals fc)
                      :series-label "Firstborns"
                      :legend true
                      :title "Conditional probability"
                      :x-label "Weeks"
                      :y-label "Probability")
      (charts/add-lines (keys oc)
                        (vals oc)
                        :series-label "Others")
      (ic/view)))

(defn range-cond-prob [range fp]
  (maphash (fn [t] [t (cond-prob fp t)])
           range))
  
(defn ex-2-8 []
  (let [[f o]   (pregnancy-lengths-split)
        [fp op] (map pmf [f o])
        weeks   (map double (range 25 46))
        fc      (range-cond-prob weeks fp)
        oc      (range-cond-prob weeks op)]
    (cond-prob-chart fc oc)))

;; ===========================================================================

(defn -main []
  (ex-2-8))

