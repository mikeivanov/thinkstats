(ns thinkstats.exercises.ex-histogram
  (:use clojure.contrib.generic.math-functions
        [thinkstats.survey :only (pregnancy-lengths
                                  pregnancy-lengths-split)])
  (:require [incanter.core   :as ic]
            [incanter.charts :as charts])
  (:import  (org.jfree.chart.renderer.xy ClusteredXYBarRenderer)))

;; A histogram chart example
;; lein run -m thinkstats.exercises.ex-histogram

(defn hist-1 []
  (let [data (pregnancy-lengths)
        hist (charts/histogram data)]
    (ic/view hist)))

(defn hist-2 []
  (let [[firstborns others] (pregnancy-lengths-split)
        hist                (doto (charts/histogram firstborns :legend true)
                              (charts/add-histogram others))]
    (doto (.getPlot hist)
      (.setRenderer (ClusteredXYBarRenderer.)))
    (ic/view hist)))

(defn -main []
  (hist-1)
  (hist-2))



