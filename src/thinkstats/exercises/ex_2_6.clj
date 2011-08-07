(ns thinkstats.exercises.ex-2-6
  (:use clojure.contrib.generic.math-functions
        [thinkstats.survey :only (pregnancy-lengths-split)]
        [thinkstats.stats  :only (normalize)])
  (:require [incanter.core   :as ic]
            [incanter.charts :as charts]))

;; Run as:
;;     lein run -m thinkstats.exercises.ex-2-6

;; 2.6 ----------------------------------------------------------------------
;; Modify descriptive.py to plot Pmfs using lines instead of bars.

(defn line-chart [f o title y-label]
  (-> (charts/xy-plot (keys f)
                      (vals f)
                      :series-label "Firstborns"
                      :legend true
                      :title title
                      :x-label "Length (weeks)"
                      :y-label y-label)
      (charts/add-lines (keys o)
                        (vals o)
                        :series-label "Others")
      (ic/view)))

(defn ex-2-6 []
  (let [[fl ol] (pregnancy-lengths-split)
        [fh oh] (map frequencies [fl ol])
        [fp op] (map normalize [fh oh])
        dw      (range 35.0 46.0)
        dp      (map #(* 100.0 (- (get fp % 0) (get op % 0))) dw)]
    (do
      (line-chart fh oh "Histogram" "Frequency")
      (line-chart fp op "PMF" "Probability")
      (-> (charts/xy-plot dw dp
                          :legend true
                          :title "Difference in PMFs"
                          :series-label "Difference"
                          :x-label "Length (weeks)"
                          :y-label "100 (first-other)")
          (ic/view)))))

;; ===========================================================================

(defn -main []
  (ex-2-6))

