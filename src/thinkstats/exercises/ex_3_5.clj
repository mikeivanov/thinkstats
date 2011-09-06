(ns thinkstats.exercises.ex-3-5
  (:use incanter.core
        [thinkstats.relay :only (speeds)]
        [thinkstats.stats :only (cdf items)])
  (:require [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-3-5

;; 3.5 ----------------------------------------------------------------------
;; Generate a plot that shows the CDF of running speeds.

(defn ex-3-5 []
  (let [data (speeds)
        cdf  (cdf data)
        ds   (dataset ["speed" "cdf"] (items cdf))]
    (with-data ds
      (view (charts/xy-plot :speed :cdf)))))

;; ===========================================================================

(defn -main []
  (ex-3-5))

