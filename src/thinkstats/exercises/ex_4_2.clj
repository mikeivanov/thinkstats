(ns thinkstats.exercises.ex-4-2
  (:import org.jfree.chart.axis.LogAxis)
  (:require [thinkstats.stats :as stats]
            [thinkstats.presidents :as data]
            [incanter.core :as ic]
            [incanter.charts :as charts]
            [clj-time.core :as dt]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-4-2

;; Plot the CDF of the interarrival times and the CCDF on a log-y scale.

(defn pairwise [seq]
  (map vector seq (drop 1 seq)))

(defn intervals [ds]
  (->> ds
       (ic/$ :dob)
       (map dt/year)
       (sort)
       (reverse)
       (pairwise)
       (map (partial apply -))))

(defn plot-intervals [[x y] title]
  (charts/xy-plot x y
                  :legend false
                  :title title
                  :x-label "Years"
                  :y-label "Probability"))

(defn cdf-data [cdf]
  [(:values cdf) (:probabilities cdf)])

(defn cdf-complement [cdf]
  [(drop-last (:values cdf))
   (map (partial - 1) (drop-last (:probabilities cdf)))])

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
      (-> (cdf-data cdf)
          (plot-intervals "CDF")
          (ic/view))
      (-> (cdf-complement cdf)
          (plot-intervals "CCDF")
          (log-scale-y)
          (ic/view)))))

(defn -main []
  (ex-4-2)
  nil)
