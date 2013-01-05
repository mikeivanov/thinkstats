(ns thinkstats.exercises.ex-4-2
  (:use thinkstats.utils)
  (:import org.jfree.chart.axis.LogAxis)
  (:require [thinkstats.stats :as stats]
            [thinkstats.presidents :as data]
            [incanter.core :as ic]
            [incanter.charts :as charts]
            [clj-time.core :as dt]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-4-2

;; Plot the CDF of the interarrival times and the CCDF on a log-y scale.

(defn intervals [ds]
  (->> ds
       (ic/$ :dob)
       (map dt/year)
       (sort)
       (reverse)
       (pairwise)
       (map (partial apply -))))

(defn plot-intervals [title [x y]]
  (charts/xy-plot x y
                  :legend false
                  :title title
                  :x-label "Years"
                  :y-label "Probability"))

(defn log-scale-y [chart]
  (let [plot (.getPlot chart)
        axis (LogAxis.)]
    (.setRangeAxis plot 0 axis))
  chart)

(defn ex-4-2 []
  (let [cdf (->> (data/dataset)
                 (intervals)
                 (stats/cdf))]
    (do
      (->> cdf
           (stats/components)
           (plot-intervals "CDF")
           (ic/view))
      (->> cdf
           (stats/complementary)
           (stats/components)
           (map drop-last)
           (plot-intervals "CCDF")
           (log-scale-y)
           (ic/view)))))

(defn -main []
  (ex-4-2)
  nil)
