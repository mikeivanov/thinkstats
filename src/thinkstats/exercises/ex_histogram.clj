(ns thinkstats.exercises.ex-histogram
  (:use clojure.contrib.generic.math-functions
        [thinkstats.survey :only (dataset firstborn? lengths)])
  (:require [incanter.core   :as ic]
            [incanter.charts :as charts])
  (:import  (org.jfree.chart.renderer.xy ClusteredXYBarRenderer)))

;; A histogram chart example
;; lein run -m thinkstats.exercises.ex-histogram

(defn hist-1 []
  (let [data (lengths (dataset :alive))
        hist (charts/histogram data)]
    (ic/view hist)))

(defn in-range? [rec]
  (let [x (:prglength rec)]
    (and (< 25 x) (<= x 45))))

(defn hist-2 []
  (let [records    (filter in-range? (dataset :alive))
        firstborns (group-by firstborn? records)
        fb-len     (lengths (firstborns true))
        ob-len     (lengths (firstborns false))
        hist       (doto (charts/histogram fb-len :legend true)
                     (charts/add-histogram ob-len))]
    (doto (.getPlot hist)
      (.setRenderer (ClusteredXYBarRenderer.)))
    (ic/view hist)))

(defn -main []
  (hist-1)
  (hist-2))



