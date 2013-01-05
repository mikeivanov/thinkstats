(ns thinkstats.exercises.ex-4-1
  (:import org.jfree.chart.axis.LogAxis)
  (:require [thinkstats.stats :as stats]
            [thinkstats.babyboom :as data]
            [incanter.core :as ic]
            [incanter.charts :as charts]
            [incanter.distributions :as dist]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-4-1

(defn expovariate [lambda]
  (let [exp (dist/exponential-distribution lambda)]
    (repeatedly #(.draw exp))))

(defn plot-intervals [[mx my] [ax ay] title]
  (-> (charts/xy-plot mx my
                      :series-label "Model"
                      :legend true
                      :title title
                      :x-label "Interval"
                      :y-label "Probability")
      (charts/add-lines ax ay
                        :series-label "Actual")))

(defn cdf-data [cdf]
  [(:values cdf) (:probabilities cdf)])
    
(def complement-to-1 (partial - 1))

(defn cdf-complement [cdf]
  [(drop-last (:values cdf))
   (map complement-to-1 (drop-last (:probabilities cdf)))])
        
(defn log-scale-y [chart]
  (let [plot (.getPlot chart)
        axis (LogAxis.)]
    (.setRangeAxis plot 0 axis))
  chart)

(defn plot-cdfs [model actual]
  (-> (plot-intervals (cdf-data model)
                      (cdf-data actual)
                      "CDF")
      (ic/view)))

(defn plot-ccdfs [model actual]
  (-> (plot-intervals (cdf-complement model)
                      (cdf-complement actual)
                      "CCDF")
      (log-scale-y)
      (ic/view)))

(defn ex-4-1 []
  ;; generate 44 values from an exponential distribution with mean 32.6.
  (let [model (->> (/ 1 32.6)
                   (expovariate)
                   (take 44)
                   (stats/cdf))
        ;; values from Figure 4.2
        actual (->> (data/dataset)
                    (data/intervals)
                    (stats/cdf))]
    (do
      (plot-cdfs model actual)
      ;; Plot the CCDF on a log-y scale
      (plot-ccdfs model actual))))

(defn -main []
  (ex-4-1)
  nil)
